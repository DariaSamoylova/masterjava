package ru.javaops.masterjava.web;

import com.typesafe.config.Config;
import org.slf4j.event.Level;
import ru.javaops.masterjava.ExceptionType;
import ru.javaops.masterjava.config.Configs;
import ru.javaops.masterjava.web.handler.SoapLoggingHandlers;
import ru.javaops.masterjava.web.handler.SoapStatisticsHandlers;

import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.handler.Handler;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WsClient<T> {
    private static Config HOSTS;

    private final Class<T> serviceClass;
    private final Service service;
    private String endpointAddress;
    private String password;
    private String user;
    private static final SoapLoggingHandlers.ClientHandler LOGGING_HANDLER ;//= new SoapLoggingHandlers.ClientHandler(Level.DEBUG);
    private static final SoapStatisticsHandlers STATISTICS_HANDLER = new SoapStatisticsHandlers();





    static {
        HOSTS = Configs.getConfig("masterjava.config", "hosts");
        LOGGING_HANDLER= new SoapLoggingHandlers.ClientHandler(Level.valueOf(HOSTS.getString("debug.client")));
    }

    public WsClient(URL wsdlUrl, QName qname, Class<T> serviceClass) {
        this.serviceClass = serviceClass;
        this.service = Service.create(wsdlUrl, qname);
    }

    public void init(String host, String endpointAddress) {
        this.endpointAddress = HOSTS.getString(host) + endpointAddress;
        this.user = HOSTS.getString(user) ;
        this.password = HOSTS.getString(password) ;
    }

    //  Post is not thread-safe (http://stackoverflow.com/a/10601916/548473)
    public T getPort(WebServiceFeature... features) {
        T port = service.getPort(serviceClass, features);
        BindingProvider bp = (BindingProvider) port;
        Map<String, Object> requestContext = bp.getRequestContext();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
        requestContext.put(BindingProvider.USERNAME_PROPERTY, user);
        requestContext.put(BindingProvider.PASSWORD_PROPERTY, password);

        List<Handler> handlerChain = new ArrayList<Handler>();

        handlerChain.add(LOGGING_HANDLER);
        handlerChain.add(STATISTICS_HANDLER);


        WsClient.setHandler(port, handlerChain);
        return port;
    }

//    public static <T> void setAuth(T port, String user, String password) {
//        Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
//        requestContext.put(BindingProvider.USERNAME_PROPERTY, user);
//        requestContext.put(BindingProvider.PASSWORD_PROPERTY, password);
//    }

    public static <T> void setHandler(T port, List<Handler> handlers) {
        Binding binding = ((BindingProvider) port).getBinding();
        List<Handler> handlerList = binding.getHandlerChain();
        handlerList.addAll(handlers);
        binding.setHandlerChain(handlerList);
    }

    public static WebStateException getWebStateException(Throwable t, ExceptionType type) {
        return (t instanceof WebStateException) ? (WebStateException) t : new WebStateException(t, type);
    }
}
