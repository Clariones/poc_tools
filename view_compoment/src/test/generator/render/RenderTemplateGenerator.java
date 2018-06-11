package test.generator.render;

import java.io.File;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import poc.utils.DebugUtil;
import poc.utils.TextUtil;
import sample.shuxiang.customer.MapUtil;
import uispec.parser.datasource.DataSourceInfo;
import uispec.parser.specelement.BaseUiSpecElement;
import uispec.parser.specelement.DataSourceUiSpec;
import uispec.parser.specelement.PageUiSpec;
import uispec.parser.specparser.DataSourceUtil;

public class RenderTemplateGenerator {
    protected String packageBaseName;
    protected File outputBaseFolder;
    protected List<PageUiSpec> pages;
    protected File templateBaseFolder;
    protected String templateFileName = "renderTemplate.java.ftl";

    public File getTemplateBaseFolder() {
	return templateBaseFolder;
    }

    public void setTemplateBaseFolder(File templateBaseFolder) {
	this.templateBaseFolder = templateBaseFolder;
    }

    public String getTemplateFileName() {
	return templateFileName;
    }

    public void setTemplateFileName(String templateFileName) {
	this.templateFileName = templateFileName;
    }

    public String getPackageBaseName() {
	return packageBaseName;
    }

    public void setPackageBaseName(String packageBaseName) {
	this.packageBaseName = packageBaseName;
    }

    public File getOutputBaseFolder() {
	return outputBaseFolder;
    }

    public void setOutputBaseFolder(File outputBaseFolder) {
	this.outputBaseFolder = outputBaseFolder;
    }

    public List<PageUiSpec> getPages() {
	return pages;
    }

    public void setPages(List<PageUiSpec> pages) {
	this.pages = pages;
    }

    public Map<String, Object> preprocess() throws Exception {
	Map<String, Object> result = MapUtil.newMap(MapUtil.$("packageBase", this.getPackageBaseName()),
		MapUtil.$("userContext", "ShuxiangUserContext"));
	List<Map<String, Object>> jobs = new ArrayList<Map<String, Object>>();

	for (PageUiSpec page : pages) {
	    parsePageDataSource(page);
	    jobs.add(createPageJob(page));
	}
	result.put("pages", jobs);
	return result;
    }

    // 分析页面内的“数据”
    private void parsePageDataSource(PageUiSpec page) throws Exception {
	Map<String, DataSourceInfo> globalVarTable = new HashMap<String, DataSourceInfo>();
	// 先分析page level的变量，所有变量最终都以此为起点
	for(BaseUiSpecElement child : page.getChildren()) {
	    if (child instanceof DataSourceUiSpec) {
		try {
		    DataSourceUtil.parseGlobalExpression(globalVarTable, null, ((DataSourceUiSpec)child).getVariableName(), ((DataSourceUiSpec)child).getDataSourceExpression());
		} catch (Exception e) {
		    e.printStackTrace();
		    throw new Exception("Error when parse data-source at line " + child.getElementDeclaredLineNumber(), e);
		}
	    }
	}
	page.setVariableTable(globalVarTable);
	
	// 然后开始分析各级组件内包含的变量
	for(BaseUiSpecElement child : page.getChildren()) {
	    parseDataSource(globalVarTable, null, child);
	}
    }

    /**
     * 分析一个UI Spec单元的数据源
     * @param globalVarTable
     * @param closestParentDataInfo
     * @param curUiSpec
     * @throws Exception 
     */
    private void parseDataSource(Map<String, DataSourceInfo> globalVarTable, DataSourceInfo closestParentDataInfo, BaseUiSpecElement curUiSpec) throws Exception {
	// 先看本组件是否有需要分析的变量
	List<String> exprList = curUiSpec.getDataSourceExpressionList();
	if (exprList != null && !exprList.isEmpty()) {
	    try {
		List<DataSourceInfo> dsInfoList = parseDataSourceOfUiSpec(globalVarTable, closestParentDataInfo, exprList);
		curUiSpec.setDataSourceExpressionResultList(dsInfoList);
	    } catch (Exception e) {
		e.printStackTrace();
		throw new Exception("Error when parse data-source at line " + curUiSpec.getElementDeclaredLineNumber(), e);
	    }
	}
	
	if (curUiSpec.getChildren() == null) {
	    return;
	}
	DataSourceInfo curDataSrc = curUiSpec.getBindedDataSourceInfo();
	if (curDataSrc != null) {
	    closestParentDataInfo = curDataSrc;
	}
	for(BaseUiSpecElement child : curUiSpec.getChildren()) {
	    parseDataSource(globalVarTable, closestParentDataInfo, child);
	}
    }

    private List<DataSourceInfo> parseDataSourceOfUiSpec(Map<String, DataSourceInfo> globalVarTable,
	    DataSourceInfo closestParentDataInfo, List<String> exprList) throws Exception {
	List<DataSourceInfo> localVarTable = new ArrayList<DataSourceInfo>();
	for(String dsExpr : exprList) {
	    DataSourceInfo dsInfo = DataSourceUtil.parseExpression(globalVarTable, closestParentDataInfo, localVarTable, dsExpr);
	    if (dsInfo == null) {
		throw new Exception("Cannot parse data-source " + dsExpr);
	    }
	    localVarTable.add(dsInfo);
	}
	return localVarTable;
    }

    private Map<String, Object> createPageJob(PageUiSpec page) {
	RenderTemplatePreprocessContext jobContext = new RenderTemplatePreprocessContext();
	List<BaseUiSpecElement> memberElements = new ArrayList<BaseUiSpecElement>();
	
	// page 没有child我就直接抛空指针
	List<BaseUiSpecElement> children = page.getChildren();
	for (BaseUiSpecElement child : children) {
	    createUiSpecElementTask(jobContext, memberElements, page, child);
	}
	return MapUtil.newMap(MapUtil.$("page", page), MapUtil.$("members", memberElements),
		MapUtil.$("className", TextUtil.hyphenCaseToBigCamelName(page.getName())),
		MapUtil.$("viewModelName", TextUtil.hyphenCaseToBigCamelName(page.getName()) + "ViewModel"));
    }

    private void createUiSpecElementTask(RenderTemplatePreprocessContext jobContext,
	    List<BaseUiSpecElement> memberElements, PageUiSpec page, BaseUiSpecElement element) {
	DebugUtil.dumpObjectToJson("当前处理的的元素：", element);
	if (element instanceof DataSourceUiSpec) {
	    return;
	}
	String renderMethodName = RUtils.calcElementRenderName(jobContext, element);
	element.setJobInfo(MapUtil.newMap(MapUtil.$("methodName", renderMethodName), MapUtil.$("isDynamic", false)));

	memberElements.add(element);

	// process children element
	if (element.getChildren() == null || element.getChildren().isEmpty()) {
	    return;
	}
	for (BaseUiSpecElement child : element.getChildren()) {
	    createUiSpecElementTask(jobContext, memberElements, page, child);
	}
    }

    public void produce(Map<String, Object> data, Writer out) throws Exception {
	Configuration config = new Configuration(Configuration.VERSION_2_3_28);
	config.setDirectoryForTemplateLoading(getTemplateBaseFolder());
	config.setDefaultEncoding("UTF-8");
	Template template = config.getTemplate(getTemplateFileName());

	List<Object> pages = (List<Object>) data.get("pages");
	for (Object page : pages) {
	    data.put("pageSpec", page);
	    template.process(data, out);
	}
    }

}
