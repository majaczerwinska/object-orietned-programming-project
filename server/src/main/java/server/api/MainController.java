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


    @PostMapping("/admin")
    public ResponseEntity<String> checkAdminPassword(@RequestBody String password) {
        if (password.equals(adminPassword)) {
            return ResponseEntity.ok("Password is correct");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");

        }
    }
}
