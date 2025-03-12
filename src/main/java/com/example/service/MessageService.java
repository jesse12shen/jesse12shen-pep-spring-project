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
        Optional<Message> result_o = this.messageR.findById(id);
        return result_o.orElse(null);
    }

    public Integer dlt(int id){
        Optional<Message> body = this.messageR.findById(id);
        if (body.isPresent()){
            this.messageR.deleteById(id);
            // return this.messageR.deleteById_i(id);
            return 1;
        } else {
            return null;
        }
        // return body.get(); 
    }
    public Optional<Message> upd(int id, String text){
        if (!messageR.existsById(id)){
            return Optional.empty();
        }
        Message msg_oi = messageR.getById(id);
        // boolean doesMsgExist = msg_oi != null;
        int msg_length = text.length();
        System.out.println("text is " + text);
        // if (msg_length > 255 || msg_length == 0 || !doesMsgExist) {
        if (msg_length > 255 || msg_length < 1){
            System.out.println("empty condition met" + Optional.empty());
            return Optional.empty();
        } // message requirements
        msg_oi.setMessageText(text);
        messageR.save(msg_oi);
        return Optional.of(msg_oi);
    }
    public List<Message> rtvAllByAcc(Integer a_id){
        boolean doesUserExist = accountR.existsById(a_id);
        List<Message> message_l = new ArrayList<Message>(); // List because that's what findByPostedBy() uses
        if (!doesUserExist){
            return message_l;
        }
        // Account account_oi = accountR.getById(a_id);
        // System.out.println("ready to call repo");
        message_l = messageR.findMessagesByPostedBy(a_id);
        return message_l;

    }
}
