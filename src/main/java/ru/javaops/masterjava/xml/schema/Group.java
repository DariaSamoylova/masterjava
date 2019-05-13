package ru.javaops.masterjava.xml.schema;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "Group", namespace = "http://javaops.ru")
public class Group {
    @XmlValue
    protected String value;

    @XmlAttribute(name = "type", required = true)
    protected GroupType groupType;

    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
