package ru.javaops.masterjava.persist.model;

import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Group extends BaseEntity{
    private @NonNull String name;
    private @NonNull Project project;
    private @NonNull GroupType groupType;
    public Group(Integer id, String name, Project project, GroupType groupType) {
        this(name, project,groupType);
        this.id=id;
    }
}
