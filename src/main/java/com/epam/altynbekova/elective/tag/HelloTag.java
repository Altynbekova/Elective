package com.epam.altynbekova.elective.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class HelloTag extends TagSupport {
    private String login;

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public int doStartTag() throws JspException {

        if(!login.isEmpty()){
            try {
                pageContext.getOut().write(login);
            } catch (IOException e) {
                throw new JspException(e.getMessage(),e);
            }
        }
        return SKIP_BODY;
    }
}
