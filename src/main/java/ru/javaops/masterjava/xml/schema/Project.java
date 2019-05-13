package ru.javaops.masterjava.xml.schema;


import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "name",
        "description"  ,
        "groups"
})
@XmlRootElement(name = "Project", namespace = "http://javaops.ru")
public class Project {
    @XmlElement(namespace = "http://javaops.ru",name = "name", required = true)
    protected String name;
    @XmlElement(namespace = "http://javaops.ru",name = "description",required = true)
    protected String description;
                          /*   @XmlElement(namespace = "http://javaops.ru", required = true)
    protected String fullName;*/
    @XmlElement(name = "Groups", namespace = "http://javaops.ru", required = true)
    protected Project.Groups groups;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Groups getGroups() {
        return groups;
    }

    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "group"
    })
    public static class Groups {

        @XmlElement(name = "Group", namespace = "http://javaops.ru")
        protected List<Group> group;

        public List<Group> getGroup() {
            if (group == null) {
                group = new ArrayList<Group>();
            }
            return this.group;
        }
    }

}
