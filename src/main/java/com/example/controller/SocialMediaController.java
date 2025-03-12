package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // wasn't included by default
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// import java.util.Optional;
import java.util.*;

import javax.websocket.server.PathParam;

import com.example.entity.*;
import com.example.service.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You are required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    // normally, having both services instantiated would be fine... when would you want an app at ~50% functionality per session?
    @Autowired
    AccountService accountS;
    @Autowired
    MessageService messageS;
    
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account new_u){ // definitely want ResponseEntity to work with Account class and output status codes
        Account result = accountS.RegisterNew(new_u);
        // switch (result) { //switch statements only work on int
        //     case null:
        // }
        if(result == null){
            return ResponseEntity.status(400).body(null);
        }   else  if (result.getUsername().length() == 0) {
            return ResponseEntity.status(409).body(null);
        }  else {
            return ResponseEntity.status(200).body(result);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account info){
        Optional<Account> result = accountS.login(info);
        // Boolean isNull = result.isEmpty();
        if (result.isEmpty()){
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.status(200)
        .body(result.get());
    }
    
    @PostMapping("/messages")
    public ResponseEntity<Message> send(@RequestBody Message msg) {
        Message result = messageS.send(msg);
        if (result == null){
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> rtvAllMsg(){
        return ResponseEntity.ok(messageS.rtvAll());
    }
    
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> rtvMsg(@PathVariable int messageId){
        return ResponseEntity.ok(messageS.rtv(messageId));
    }
    
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> dltMsg(@PathVariable int messageId){
        // using an Optional may not be a good idea then
        return ResponseEntity.ok(messageS.dlt(messageId));
    }
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updMsg(@PathVariable int messageId, @RequestBody Message u_message) {
        // Note how you can get away with just having one arg for Message constructor
        Optional<Message> result_o = messageS.upd(messageId, u_message.getMessageText());
        if (result_o.isPresent()){
            return ResponseEntity.ok(1);
        }
        return ResponseEntity.status(400).body(0);
    }
}
