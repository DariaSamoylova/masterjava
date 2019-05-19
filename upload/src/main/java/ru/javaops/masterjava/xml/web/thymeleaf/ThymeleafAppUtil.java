package ru.javaops.masterjava.xml.web.thymeleaf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;

public class ThymeleafAppUtil {
	private static TemplateEngine templateEngine;
    private static  ServletContextTemplateResolver templateResolver ;
    public static void setTemplateResolver(ServletContext ctx){
         templateResolver = new ServletContextTemplateResolver(ctx);
        templateResolver.setTemplateMode("XHTML");
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(3600000L);
    }
     static {

         templateEngine = new TemplateEngine();
         templateEngine.setTemplateResolver(templateResolver);
     }

     public static TemplateEngine getTemplateEngine() {
		return templateEngine;
     }
}
