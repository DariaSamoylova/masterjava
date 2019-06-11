package ru.javaops.masterjava.service.mail;

        import com.google.common.collect.ImmutableList;
        import com.google.common.collect.ImmutableSet;

public class MailWSClientMain {
    public static void main(String[] args) {
         MailWSClient.sendToGroup(
                ImmutableSet.of(new Addressee("To <masterjava@javaops.ru>")),
                ImmutableSet.of(new Addressee("Copy <masterjava@javaops.ru>")), "Subject", "Body");
    }
}
      /*
                ImmutableList.of(new Addressee("To <fireillusion@yandex.ru>")),
                ImmutableList.of(new Addressee("Copy <fireillusion@yandex.ru>")), "Subject", "Body");
*/