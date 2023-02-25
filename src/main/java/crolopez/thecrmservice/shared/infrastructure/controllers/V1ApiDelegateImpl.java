package crolopez.thecrmservice.shared.infrastructure.controllers;

import crolopez.thecrmservice.customer.application.services.CustomerService;
import crolopez.thecrmservice.login.infrastructure.services.LoginService;
import crolopez.thecrmservice.shared.entities.CustomerDto;
import crolopez.thecrmservice.shared.entities.UserDto;
import crolopez.thecrmservice.shared.infrastructure.api.V1ApiDelegate;
import crolopez.thecrmservice.user.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
public class V1ApiDelegateImpl implements V1ApiDelegate {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Override
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        return ResponseEntity.ok().body(customerService.getCustomers());
    }

    @Override
    public ResponseEntity<CustomerDto> createCustomer(CustomerDto customerDto) {
        return ResponseEntity.ok().body(customerService.createCustomer(customerDto));
    }

    @Override
    public ResponseEntity<CustomerDto> getCustomer(String id) {
        return ResponseEntity.ok().body(customerService.getCustomer(id));
    }

    @Override
    public ResponseEntity<CustomerDto> deleteCustomer(String id) {
        return ResponseEntity.ok().body(customerService.deleteCustomer(id));
    }

    @Override
    public ResponseEntity<CustomerDto> updateCustomer(String id, CustomerDto customerDto) {
        return ResponseEntity.ok().body(customerService.updateCustomer(id, customerDto));
    }

    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @Override
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        return ResponseEntity.ok().body(userService.createUser(userDto));
    }

    @Override
    public ResponseEntity<UserDto> getUser(String id) {
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @Override
    public ResponseEntity<UserDto> deleteUser(String id) {
        return ResponseEntity.ok().body(userService.deleteUser(id));
    }

    @Override
    public ResponseEntity<UserDto> updateUser(String id, UserDto userDto) {
        return ResponseEntity.ok().body(userService.updateUser(id, userDto));
    }

    @Override
    public ResponseEntity<String> githubAuthorize(String code,
                                                  String state,
                                                  String responseType,
                                                  String clientId,
                                                  String scope,
                                                  String redirectUri)  {
        String accessToken = loginService.getAccessToken(code, state) ;

        if (accessToken != null) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Authorization", "Bearer " + accessToken);
            return new ResponseEntity<>("Authorized", responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<String> githubLogin(String scope) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(loginService.getLoginUrl(scope)));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

}
