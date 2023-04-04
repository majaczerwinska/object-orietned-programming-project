package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class MainController {

    @Autowired
    private String adminPassword;

    @Autowired
    private String authToken;

    /**
     * empty constructor
     */
    public MainController() {}

    /**
     * Root URL
     * @return simple response to verify the server connection
     */
    @GetMapping("/")
    public ResponseEntity<String> rootGet() {
        return ResponseEntity.ok("welcome to talio!");
    }


    /**
     * Authentication endpoint for the admin panel.
     * user sends a password and it is checked against the server's admin
     * password, as defined in the bean.
     * //todo hash method in commons, receive hashed password
     * @param password password string from the request body
     * @return the authentication token for the sql endpoint,
     * wrapped in a response entity of either 200 (successful)
     * or 401 (unauthorized)
     */
    @PostMapping("/admin")
    public ResponseEntity<String> checkAdminPassword(@RequestBody String password) {
        if (password.equals(adminPassword)) {
            return ResponseEntity.ok(authToken);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");

        }
    }
}
