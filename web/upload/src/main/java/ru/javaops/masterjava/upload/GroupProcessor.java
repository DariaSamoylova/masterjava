package ru.javaops.masterjava.upload;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.GroupDao;
import ru.javaops.masterjava.persist.model.Group;
import ru.javaops.masterjava.persist.model.Project;
import ru.javaops.masterjava.persist.model.type.GroupType;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.Map;

@Slf4j
public class GroupProcessor {


    private final GroupDao groupDao = DBIProvider.getDao(GroupDao.class);
    private static final JaxbParser jaxbParser = new JaxbParser(ObjectFactory.class);

    public Map<String, Group> process(StaxStreamProcessor processor, Map<String, Project> projectMap) throws XMLStreamException, JAXBException {
        val unmarshaller = jaxbParser.createUnmarshaller();
        while (processor.startElement("Project", "Projects")) {
            ru.javaops.masterjava.xml.schema.Project projectXml = unmarshaller.unmarshal(processor.getReader(), ru.javaops.masterjava.xml.schema.Project.class);
            if (projectMap.get(projectXml.getName()) == null) {
                log.error("no such project");
            } else {
                for (ru.javaops.masterjava.xml.schema.Project.Group g : projectXml.getGroup()) {
                    groupDao.insert(
                            new Group(g.getName(), GroupType.valueOf(g.getType().value()), projectMap.get(projectXml.getName()).getId()));

                }
            }

        }
        return groupDao.getAsMap();
    }
}
