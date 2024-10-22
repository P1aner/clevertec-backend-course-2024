package ru.clevertec.starter.model;


import lombok.Data;

@Data
public class SessionRequest {
    private String login;
    private Session session;

    public SessionRequest(String login) {
        this.login = login;
    }
}
