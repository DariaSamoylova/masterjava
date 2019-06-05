package ru.javaops.masterjava.service.mail;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import ru.javaops.masterjava.service.mail.dao.EmailDao;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class MailSender {
    private final static  EmailDao emailDao = DBIProvider.getDao(EmailDao.class);


    static void sendMail(List<Addressee> to, List<Addressee> cc, String subject, String body)  {
        Config configEmail = ConfigFactory.parseResources("mail.conf").resolve().getConfig("mail");
        String host =     configEmail.getString("mail.host");
        Integer port =     configEmail.getInt("mail.port");
        String username =     configEmail.getString("mail.username");
        String password =     configEmail.getString("mail.password");
        Boolean useSSL =     configEmail.getBoolean("mail.useSSL");

        to.addAll(cc);
         for(Addressee addressee:to){
             Email email = new SimpleEmail();

             email.setHostName(host);
             email.setSmtpPort(port);
             email.setAuthenticator(new DefaultAuthenticator(username,password ));
             email.setSSLOnConnect(useSSL);
             ru.javaops.masterjava.service.mail.model.Email sendedEmail;
             try {
                 email.setFrom(username);

                 email.setSubject(subject);
                 email.setMsg(body);
                 email.addTo(addressee.getEmail());
              //   email.addTo( to.stream().map(e->e.getEmail()).collect(Collectors.toList()).toArray(new String[0]));
                // email.addTo( cc.stream().map(e->e.getEmail()).collect(Collectors.toList()).toArray(new String[0]));
                 email.send();
                 sendedEmail = new ru.javaops.masterjava.service.mail.model.Email(
                                 addressee.getEmail(),
                                 LocalDateTime.now(), subject, body, true) ;

             } catch (EmailException e) {
                 sendedEmail = new ru.javaops.masterjava.service.mail.model.Email(
                                 addressee.getEmail(),
                                 LocalDateTime.now(), subject, body, false) ;

                 e.printStackTrace();
             }
             emailDao.insert(sendedEmail);
         }



        log.info("Send mail to \'" + to + "\' cc \'" + cc + "\' subject \'" + subject + (log.isDebugEnabled() ? "\nbody=" + body : ""));
    }
}
