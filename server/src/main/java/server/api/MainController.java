package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class MainController {

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
}
