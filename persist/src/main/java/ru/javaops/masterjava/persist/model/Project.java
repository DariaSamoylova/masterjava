package ru.javaops.masterjava.persist.model;

import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Project extends  BaseEntity{
    private @NonNull String description;
    //private @NonNull List<Group> group;
    private @NonNull String name;

//    public Project(Integer id, String description, List<Group> group, String name) {
//        this(description, group, name);
//        this.id=id;
//    }

    public Project(Integer id, String description,   String name) {
        this(description,  name);
        this.id=id;
    }
}
