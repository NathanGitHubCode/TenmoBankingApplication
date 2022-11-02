package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.stream.BaseStream;

public class AccountService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    public Account getBalance(int accountId){
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<Account> entity = new HttpEntity<>();

        Account account = null;
        try {
            account = restTemplate.getForObject(API_BASE_URL + "balance", Account.class);
        }
        catch (RestClientResponseException e){
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        }
        catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }
//    public Account
//    HttpEntity<UserCredentials> entity = createCredentialsEntity(credentials);
//    AuthenticatedUser user = null;
//        try {
//        ResponseEntity<AuthenticatedUser> response =
//                restTemplate.exchange(baseUrl + "login", HttpMethod.POST, entity, AuthenticatedUser.class);
//        user = response.getBody();
//    } catch (RestClientResponseException | ResourceAccessException e) {
//        BasicLogger.log(e.getMessage());
//    }

}
