package ru.javaops.masterjava.service.mail.model;

import lombok.*;
import ru.javaops.masterjava.persist.model.BaseEntity;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Email extends BaseEntity {
    private @NonNull String email;
    private @NonNull LocalDateTime sendTime;
    private @NonNull String subject;
    private @NonNull String body;
    private @NonNull Boolean result;

    public Email(Integer id, String email, LocalDateTime sendTime, String subject, String body, Boolean result) {
        this(email, sendTime, subject, body, result);
        this.id=id;
    }
}
