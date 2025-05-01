package ru.sstu.socialnetworkbackend.services;

public interface EmailSender {
    void send(String to, String message);
}
