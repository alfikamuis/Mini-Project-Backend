package com.alfika.backendecommerce.model.notification;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Getter
@Setter
@ToString
public class Message {

    @Nullable
    private String sender;
    private String messages;

    public Message(@Nullable String sender, String messages) {
        this.sender = sender;
        this.messages = messages;
    }
}
