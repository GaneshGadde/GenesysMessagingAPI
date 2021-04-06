package com.genesys.messagingservice.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.genesys.messagingservice.entity.Message;

public class MessageRequest {
    @NotNull
    private Long receiverId;

    @NotBlank
    private String body;

    public Message toMessage(Long senderId, String senderUsername) {
        Message message = new Message();
        message.setSenderId(senderId);
        message.setSenderUsername(senderUsername);
        message.setReceiverId(this.receiverId);
        message.setBody(this.body);
        return message;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "MessageRequest{" +
                "receiverId=" + receiverId +
                ", body='" + body + '\'' +
                '}';
    }
}
