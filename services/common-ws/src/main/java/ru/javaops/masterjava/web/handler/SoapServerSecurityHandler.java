package ru.javaops.masterjava.web.handler;

import com.sun.xml.ws.api.handler.MessageHandlerContext;
import lombok.extern.slf4j.Slf4j;
import ru.javaops.masterjava.web.AuthUtil;

import javax.xml.ws.handler.MessageContext;
import java.util.List;
import java.util.Map;

import static ru.javaops.masterjava.web.handler.SoapLoggingHandlers.HANDLER.getMessageText;

@Slf4j
public class SoapServerSecurityHandler  extends SoapBaseHandler{
    public static final String USER = "user";
    public static final String PASSWORD = "password";

    public static String AUTH_HEADER = AuthUtil.encodeBasicAuthHeader(USER, PASSWORD);


    @Override
    public boolean handleMessage(MessageHandlerContext context) {
        if (!isOutbound(context)) {

       // }  else {
            Map<String, List<String>> headers = (Map<String, List<String>>) context.get(MessageContext.HTTP_REQUEST_HEADERS);
            int code = AuthUtil.checkBasicAuth(headers, AUTH_HEADER);
            if (code != 0) {
                context.put(MessageContext.HTTP_RESPONSE_CODE, code);
                log.debug("Auth FAULT!");
                throw new SecurityException();
            }
            log.debug("Auth OK!");

        }
        /* if (direction) {
			out.println("direction = outbound");
		} else {
			out.println("direction = inbound");

			// ......
			try {
				// get SOAP envelope from SOAP message
				SOAPEnvelope se = smc.getMessage().getSOAPPart().getEnvelope();

				// get the headers from envelope
				SOAPHeader sh = se.getHeader();
				if (sh == null) {
					out.println("--- No headers found in the input SOAP request");
					exit = false;
				} else {
					// call method to process header
					exit = processSOAPHeader(sh);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				exit = false;
			}
         */
        return true;
    }

    @Override
    public boolean handleFault(MessageHandlerContext context) {
        log.error("Fault SOAP message:\n" + getMessageText(context.getMessage().copy()));

        return true;
    }
}
