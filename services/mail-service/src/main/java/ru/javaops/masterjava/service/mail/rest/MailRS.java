package ru.javaops.masterjava.service.mail.rest;


import com.google.common.collect.ImmutableList;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.validator.constraints.NotBlank;
import ru.javaops.masterjava.service.mail.GroupResult;
import ru.javaops.masterjava.service.mail.MailServiceExecutor;
import ru.javaops.masterjava.service.mail.MailWSClient;
import ru.javaops.masterjava.service.mail.util.Attachments;
import ru.javaops.masterjava.web.WebStateException;

import javax.servlet.http.Part;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

@Path("/")
public class MailRS {
    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "Test";
    }

    @POST
    @Path("/send")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public GroupResult send(  @FormDataParam("users") String users,
                            @FormDataParam("subject") String subject,
                             @FormDataParam("body") String body,
                            @FormDataParam("attach") InputStream attachStream,
                              @FormDataParam("attach") FormDataContentDisposition fileDispos)  throws WebStateException {


            return MailServiceExecutor.sendBulk(MailWSClient.split(users), subject, body,
                    attachStream == null ? null :
                            ImmutableList.of(Attachments.getAttachment("fileTest", attachStream))) ;// Collections.emptyList());
       
    }
}