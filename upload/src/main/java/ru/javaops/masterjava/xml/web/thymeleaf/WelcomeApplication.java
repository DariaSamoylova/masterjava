package ru.javaops.masterjava.xml.web.thymeleaf;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.context.WebContext;

public class WelcomeApplication {
    public void process(HttpServletRequest request, HttpServletResponse response) 
   		 throws IOException {
	    WebContext ctx = new WebContext(request, response, request.getServletContext(),
	    		request.getLocale());
	    ctx.setVariable("currentDate", new Date());
        ctx.setVariable("name", new Date());
        ctx.setVariable("email", new Date());
        ctx.setVariable("flag", new Date());
		ThymeleafAppUtil.setTemplateResolver(request.getServletContext());
	    ThymeleafAppUtil.getTemplateEngine().process("welcome", ctx, response.getWriter());
    }
}
