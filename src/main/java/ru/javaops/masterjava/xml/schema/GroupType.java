package ru.javaops.masterjava.xml.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "typeGroup", namespace = "http://javaops.ru")
@XmlEnum
public enum GroupType {


    @XmlEnumValue("REGISTERING")
            REGISTERING("REGISTERING"),
    @XmlEnumValue("CURRENT")
    CURRENT("CURRENT"),
    @XmlEnumValue("FINISHED")
    FINISHED("FINISHED");
    private final String value;

    GroupType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GroupType fromValue(String v) {
        for (GroupType c: GroupType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
