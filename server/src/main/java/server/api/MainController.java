package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class MainController {

    public MainController() {}

    @GetMapping("/")
    public ResponseEntity<String> rootGet() {
        return ResponseEntity.ok("welcome to talio!");
    }
}
