package uispec.parser.specelement;

public class DataSourceUiSpec extends BaseUiSpecElement {
    protected String variableName;
    protected String dataSourceExpression;

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getDataSourceExpression() {
        return dataSourceExpression;
    }

    public void setDataSourceExpression(String dataSourceExpression) {
        this.dataSourceExpression = dataSourceExpression;
    }

    public void handlePropertyAssignment(String propertyName, Object propertyValue) {
        this.setVariableName(propertyName);
        this.setDataSourceExpression(String.valueOf(propertyValue));
        this.setIgnoreWhenGeneration(true);
        this.setShouldBeRender(false);
    }

}
