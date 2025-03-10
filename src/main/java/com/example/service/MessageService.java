package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import com.example.entity.*;
import com.example.repository.*;


@Service
public class MessageService {
    private MessageRepository messageR;
    @Autowired // field injection is totally fine for these tests at least
    private AccountRepository accountR;
     @Autowired
    public MessageService(MessageRepository repo){
        messageR = repo;

    }
    public Message send(Message Message) { // creates a message
        int msg_length = Message.getMessageText().length();
        //  = new AccountRepository();
        boolean doesUserExist = accountR.existsById(Message.getPostedBy()); //findByID() seems to return an error when ID not found
        if (msg_length > 255 || msg_length == 0 || !doesUserExist) {
            return null;
        } // message requirements
        return this.messageR.save(Message); //insertMessage should return null if username is not unique
    }

    public List<Message> rtvAll(){
        return this.messageR.findAll();
    }

    public Message rtv(int id) {
        // System.out.println();
        return this.messageR.getById(id);
    }

}
