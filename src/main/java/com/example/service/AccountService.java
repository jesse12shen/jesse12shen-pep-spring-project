package com.example.service;

import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.lang.StackWalker.Option;
import java.util.Optional;

import com.example.entity.Account;
import com.example.repository.AccountRepository; 

// import org.springframework.context.annotation.Bean;
// @Bean // https://stackoverflow.com/questions/21520316/the-annotation-bean-is-disallowed-for-this-location-error you only need beans when returning in a method
@Service
public class AccountService {
    // @Autowired
    private AccountRepository accountR; 
    // I guess we can get away with using a field injection?
    @Autowired
    public AccountService(AccountRepository repo){
        accountR = repo;
    }
        /**
     *
     * @param account an account object.
     * @return The persisted account if the persistence is successful.
     */
    public Account RegisterNew(Account account) {
        String pass = account.getPassword();
        String user = account.getUsername();
        if (pass.length() < 4 || user.length() == 0) {
            return null;  //Optional doesn't seem helpful in this case
        } // password and username requirements
        if (accountR.findByUsername(user) != null){
            return new Account("","");
        }
        return this.accountR.save(account); //insertAccount should return null if username is not unique
    }

    public Optional<Account> login(Account info){
        Account credentials = this.accountR.findByUsername(info.getUsername());
        if (credentials == null || !info.getPassword().equals(credentials.getPassword())) //we'd probably want to distinguish the errors, throw an exception? Return a dummy account with specifics?
        {
            // System.out.println("login was" + account.getPassword() + "but actually " + credentials.getPassword());
            
            // System.out.println(account.getUsername() == credentials.getUsername());
            return Optional.ofNullable(null);
        }
        return Optional.of(credentials);
    }
}
