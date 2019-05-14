package ru.javaops.masterjava.xml;

import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.schema.Group;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.schema.Payload;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.Schemas;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;
import ru.javaops.masterjava.xml.util.XsltProcessor;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainXml {
    private static final JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);

    static {
        JAXB_PARSER.setSchema(Schemas.ofClasspath("payload.xsd"));
    }
    public static void main(String[] args) throws IOException, JAXBException, XMLStreamException, TransformerException {
        //parsingJAXB("Topjava");
       // parsingStax("Topjava");
        convertXMLtoHTML("Topjava");
        
    }

    public static void convertXMLtoHTML( String project) throws IOException, TransformerException {
        try (InputStream xslInputStream = Resources.getResource("groups.xsl").openStream();
             InputStream xmlInputStream = Resources.getResource("payload.xml").openStream()) {

            XsltProcessor processor = new XsltProcessor(xslInputStream);
            processor.setParamToTransformer("proj_id",project);
            System.out.println(processor.transform(xmlInputStream));
        }
    }



    public static void parsingJAXB(String project) throws IOException, JAXBException{
        Payload payload = JAXB_PARSER.unmarshal(
                Resources.getResource("payload.xml").openStream());
        List<Group> groupList = payload.getProjects().getProject().stream()
                .filter(a->a.getName().equals(project))
                .findFirst().get().getGroups().getGroup();
        groupList.forEach(a->System.out.println(a.getValue()));

    }

    public static void parsingStax(String project) throws IOException, XMLStreamException {
        try (StaxStreamProcessor processor =
                     new StaxStreamProcessor(Resources.getResource("payload.xml").openStream())) {
            XMLStreamReader reader = processor.getReader();
            boolean flag=false;
            while (reader.hasNext()) {
                int event = reader.next();
              //  XMLEvent xmlEvent = reader.ev.nextEvent();
                if (event == XMLEvent.START_ELEMENT) {
                  //  if ("Project".equals(reader.getLocalName())&&reader.getElementText().equals(project)) {
                        if ("Project".equals(reader.getLocalName()) ) {
                            System.out.println(reader.getElementText());
//                        flag=true;
                    }
//                        if ( flag==true&&"Group".equals(reader.getLocalName())) {
//                            System.out.println(reader.getElementText());
//                        }
//                        if (flag==true&&)
                    }

//                if(xmlEvent.isEndElement()){
//                    EndElement endElement = xmlEvent.asEndElement();
//                    if(endElement.getName().getLocalPart().equals("Employee")){
//                        empList.add(emp);
//                    }
//                }
            }
        }
    }

   /*public static void main(String[] args) {
        String fileName = "/Users/pankaj/employee.xml";
        List<Employee> empList = parseXML(fileName);
        for(Employee emp : empList){
            System.out.println(emp.toString());
        }
    }

    private static List<Employee> parseXML(String fileName) {
        List<Employee> empList = new ArrayList<>();
        Employee emp = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
            while(xmlEventReader.hasNext()){
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
               if (xmlEvent.isStartElement()){
                   StartElement startElement = xmlEvent.asStartElement();
                   if(startElement.getName().getLocalPart().equals("Employee")){
                       emp = new Employee();
                       //Get the 'id' attribute from Employee element
                       Attribute idAttr = startElement.getAttributeByName(new QName("id"));
                       if(idAttr != null){
                       emp.setId(Integer.parseInt(idAttr.getValue()));
                       }
                   }
                   //set the other varibles from xml elements
                   else if(startElement.getName().getLocalPart().equals("age")){
                       xmlEvent = xmlEventReader.nextEvent();
                       emp.setAge(Integer.parseInt(xmlEvent.asCharacters().getData()));
                   }else if(startElement.getName().getLocalPart().equals("name")){
                       xmlEvent = xmlEventReader.nextEvent();
                       emp.setName(xmlEvent.asCharacters().getData());
                   }else if(startElement.getName().getLocalPart().equals("gender")){
                       xmlEvent = xmlEventReader.nextEvent();
                       emp.setGender(xmlEvent.asCharacters().getData());
                   }else if(startElement.getName().getLocalPart().equals("role")){
                       xmlEvent = xmlEventReader.nextEvent();
                       emp.setRole(xmlEvent.asCharacters().getData());
                   }
               }
               //if Employee end element is reached, add employee object to list
               if(xmlEvent.isEndElement()){
                   EndElement endElement = xmlEvent.asEndElement();
                   if(endElement.getName().getLocalPart().equals("Employee")){
                       empList.add(emp);
                   }
               }
            }

        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return empList;
    }

}*/

}
