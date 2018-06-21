package com.terapico.shuxiang.wxalayout;

import com.terapico.caf.viewcomponent.PageViewComponent;
import com.terapico.shuxiang.ShuxiangUserContext;

public class ${pageSpec.className}Render extends ${pageSpec.className}BaseRender {

    @Override
    public PageViewComponent doRendering(${userContext} userContext, ${pageSpec.viewModelName} viewModel)
            throws Exception {
        PageViewComponent page = super.doRendering(userContext, viewModel);
        page.fixLayoutTypeNames();
        return page;
    }

}
