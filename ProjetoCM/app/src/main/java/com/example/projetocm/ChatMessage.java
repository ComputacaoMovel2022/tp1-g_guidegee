package com.example.projetocm;

import java.util.Date;

public class ChatMessage {

    private String messageText;
    private String messageSenderKey;
    private long messageTime;

    public ChatMessage(String messageText, String messageSenderKey) {
        this.messageText = messageText;
        this.messageSenderKey = messageSenderKey;
        this.messageTime = new Date().getTime();
    }

    public ChatMessage() {

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageSenderKey() {
        return messageSenderKey;
    }

    public void setMessageSenderKey(String messageSenderKey) {
        this.messageSenderKey = messageSenderKey;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
