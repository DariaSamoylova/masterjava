package ru.javaops.masterjava.persist.model;

import lombok.*;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class City extends BaseEntity{
    private @NonNull String value;

    public City(Integer id, String value) {
        this(value);
        this.id=id;
    }
}
