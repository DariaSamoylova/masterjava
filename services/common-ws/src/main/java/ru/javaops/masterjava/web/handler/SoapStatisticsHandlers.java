package ru.javaops.masterjava.web.handler;

import com.sun.xml.ws.api.handler.MessageHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import ru.javaops.masterjava.web.Statistics;

import javax.xml.ws.handler.MessageContext;
import java.util.Date;

import static ru.javaops.masterjava.web.handler.SoapLoggingHandlers.HANDLER.getMessageText;

@Slf4j
public    class SoapStatisticsHandlers   extends SoapBaseHandler {

    private Date startTime, endTime;
    @Override
    public boolean handleMessage(MessageHandlerContext mhc) {
        startTime = new Date();
        try {
        Statistics.count(mhc.getPort().getName().getLocalPart(),startTime.getTime(), Statistics.RESULT.SUCCESS);
    } catch (Exception x) {
        Statistics.count(mhc.getPort().getName().getLocalPart(),startTime.getTime(), Statistics.RESULT.FAIL);
        x.printStackTrace();
    }
//          if (isOutbound(mhc)) {
//            startTime = new Date();
//            mhc.put("StartTime "+mhc.getPort().getName().getLocalPart(), startTime);
//            //+ handler name
//        } else {
//            //out.println("\ndirection = inbound ");
//            try {
//                startTime = (Date) mhc.get("StartTime "+mhc.getPort().getName().getLocalPart());
//                endTime = new Date();
//                long elapsedTime = endTime.getTime() - startTime.getTime();
//
//                mhc.put("ELAPSED_TIME", elapsedTime);
//                mhc.setScope("ELAPSED_TIME", MessageContext.Scope.APPLICATION);
//
////                out.println("Elapsed time in handler " + HandlerName + " is "
////                        + elapsedTime);
//                Statistics.count(mhc.getPort().getName().getLocalPart(),startTime.getTime(), Statistics.RESULT.SUCCESS);
//            } catch (Exception x) {
//                Statistics.count(mhc.getPort().getName().getLocalPart(),startTime.getTime(), Statistics.RESULT.FAIL);
//                x.printStackTrace();
//            }
//        }



         return true;
    }

    @Override
    public boolean handleFault(MessageHandlerContext mhc) {
       log.error("Fault SOAP message:\n" + getMessageText(mhc.getMessage().copy()));
        Statistics.count(mhc.getPort().getName().getLocalPart(),(new Date()).getTime(), Statistics.RESULT.FAIL);

        return true;
    }

//    abstract protected boolean isRequest(boolean isOutbound);
//
//    public static class ClientHandler extends SoapStatisticsHandlers {
//
//        @Override
//        protected boolean isRequest(boolean isOutbound) {
//            return isOutbound;
//        }
//    }
//
//    public static class ServerHandler extends SoapStatisticsHandlers {
//
//
//        @Override
//        protected boolean isRequest(boolean isOutbound) {
//            return !isOutbound;
//        }
//    }
}
