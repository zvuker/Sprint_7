package org.example.courier;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Courier {
    private String login;
    private String password;
    private String firstName;

    // Этот конструктор уже существует благодаря аннотации @Data, если нужен конструктор с двумя параметрами, его нужно добавить вручную:
    public Courier(String login, String password) {
        this.login = login;
        this.password = password;
    }

    // Ваш существующий конструктор для всех трех полей
    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
}