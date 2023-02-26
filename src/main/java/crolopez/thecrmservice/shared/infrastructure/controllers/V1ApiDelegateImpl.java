package crolopez.thecrmservice.shared.infrastructure.controllers;

import crolopez.thecrmservice.customer.application.services.CustomerService;
import crolopez.thecrmservice.login.application.services.LoginService;
import crolopez.thecrmservice.shared.domain.entities.dto.CustomerDto;
import crolopez.thecrmservice.shared.domain.entities.dto.GenericResponseDto;
import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;
import crolopez.thecrmservice.shared.infrastructure.api.V1ApiDelegate;
import crolopez.thecrmservice.shared.infrastructure.auth.AuthenticatedUserCache;
import crolopez.thecrmservice.shared.infrastructure.controllers.utils.RequestManager;
import crolopez.thecrmservice.user.application.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Log4j2
public class V1ApiDelegateImpl implements V1ApiDelegate {

    private final Pattern BEARER_PATTERN = Pattern.compile("Bearer\\s+(.*)$");

    @Autowired
    private AuthenticatedUserCache cache;

    @Autowired
    private RequestManager requestManager;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Override
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        List<CustomerDto> customers = customerService.getCustomers();
        log("Customers obtained.");
        return ResponseEntity.ok().body(customers);
    }

    @Override
    public ResponseEntity<CustomerDto> createCustomer(CustomerDto customerDto) {
        String loggedUserId = getLoggedUserId();
        CustomerDto customerResponse = customerService.createCustomer(customerDto, loggedUserId);
        log("Customer {} has been created.", customerDto.getId());
        return ResponseEntity.ok().body(customerResponse);
    }

    @Override
    public ResponseEntity<CustomerDto> getCustomer(String id) {
        CustomerDto customerResponse = customerService.getCustomer(id);
        log("Customer {} obtained.", id);
        return ResponseEntity.ok().body(customerResponse);
    }

    @Override
    public ResponseEntity<CustomerDto> deleteCustomer(String id) {
        CustomerDto customerResponse = customerService.deleteCustomer(id);
        log("Removed {} customer.", id);
        return ResponseEntity.ok().body(customerResponse);
    }

    @Override
    public ResponseEntity<CustomerDto> updateCustomer(String id, CustomerDto customerDto) {
        String loggedUserId = getLoggedUserId();
        CustomerDto customerResponse = customerService.updateCustomer(id, customerDto, loggedUserId);
        log("Customer {} has been updated.", id);
        return ResponseEntity.ok().body(customerResponse);
    }

    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getUsers();
        log("Users obtained.");
        return ResponseEntity.ok().body(users);
    }

    @Override
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        UserDto userResponse = userService.createUser(userDto);
        log("User {} has been created.", userDto.getId());
        return ResponseEntity.ok().body(userResponse);
    }

    @Override
    public ResponseEntity<UserDto> getUser(String id) {
        UserDto userResponse = userService.getUser(id);
        log("User {} obtained.", id);
        return ResponseEntity.ok().body(userResponse);
    }

    @Override
    public ResponseEntity<UserDto> deleteUser(String id) {
        UserDto userResponse = userService.deleteUser(id);
        log("Removed {} user.", id);
        return ResponseEntity.ok().body(userResponse);
    }

    @Override
    public ResponseEntity<UserDto> updateUser(String id, UserDto userDto) {
        UserDto userResponse = userService.updateUser(id, userDto);
        log("User {} has been updated.", id);
        return ResponseEntity.ok().body(userResponse);
    }

    @Override
    public ResponseEntity<GenericResponseDto> githubAuthorize(String code,
                                                              String state,
                                                              String responseType,
                                                              String clientId,
                                                              String scope,
                                                              String redirectUri)  {
        String accessToken = loginService.getAccessToken(code, state) ;

        if (accessToken != null) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Authorization", "Bearer " + accessToken);
            return new ResponseEntity<>(new GenericResponseDto().message("Authorized"), responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenericResponseDto().message("Unauthorized"), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<String> githubLogin() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(loginService.getLoginUrl()));
        log.info("Authentication link created.");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    private String getLoggedUserId() {
        HttpServletRequest request = requestManager.getCurrentRequest();
        String authenticationHeader = request.getHeader("authorization");
        String token = getTokenFromHeader(authenticationHeader);
        UserDto user = cache.getAuthenticatedUser(token);
        return user.getId();
    }

    private String getTokenFromHeader(String header) {
        Matcher matcher = BEARER_PATTERN.matcher(header);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return "UNEXPECTED_ERROR";
    }

    private void log(String message, String... params) {
        final String LOGGED_USER_PREFIX = "[{}] ";
        log.info(LOGGED_USER_PREFIX + message, getLoggedUserId(), params);
    }

}
