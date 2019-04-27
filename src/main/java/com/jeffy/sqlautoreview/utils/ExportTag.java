package com.jeffy.sqlautoreview.utils;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ExportTag extends TagSupport{
    
    private String action;//请求到后台Url

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

 /**
 * 导出根据搜索框条件导出所有数据
 */
    @Override
    public int doStartTag()
        throws JspException {
        

        try {
             this.pageContext.getOut().write("<a href='javascript:exp.exportExcel(\""+action+"\")' class='easyui-linkbutton' id='exportBtn' iconCls='iconExport' plain='true'>导出</a>");     
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return super.doStartTag();
    }

    @Override
    public int doEndTag()
        throws JspException {
        return super.doEndTag();
    }
    
    
    
}
