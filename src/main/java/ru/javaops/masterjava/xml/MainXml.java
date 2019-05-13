package ru.javaops.masterjava.xml;

import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.schema.Group;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.schema.Payload;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.Schemas;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public class MainXml {
    private static final JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);

    static {
        JAXB_PARSER.setSchema(Schemas.ofClasspath("payload.xsd"));
    }
    public static void main(String[] args) throws IOException, JAXBException {
        Payload payload = JAXB_PARSER.unmarshal(
                Resources.getResource("payload.xml").openStream());
        List<Group> groupList = payload.getProjects().getProject().stream()
                .filter(a->a.getName().equals("Topjava"))
                .findFirst().get().getGroups().getGroup();

        
    }
}
