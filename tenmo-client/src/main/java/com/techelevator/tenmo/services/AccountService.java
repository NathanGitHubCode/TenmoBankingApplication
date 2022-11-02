package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.util.BasicLogger;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.stream.BaseStream;

public class AccountService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    public Account getBalance(){
        Account account = null;
        try {
            account = restTemplate.getForObject(API_BASE_URL + "account", Account.class);
        }
        catch (RestClientResponseException e){
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        }
        catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }

}
