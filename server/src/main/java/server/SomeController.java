package server;

import commons.User;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import server.database.UserRepository;

@Controller
@RequestMapping("/")
public class SomeController {
    private VisitCounterService vcs;
    private UserRepository users;

    public SomeController(VisitCounterService v, UserRepository users) {
        this.vcs = v;
        this.users = users;
    }

    @GetMapping("/visit")
    @ResponseBody
    public String visit() {
        this.vcs.increase();
        return "Visits: " + this.vcs.getValue();
    }
    @GetMapping("/{id}")
    @ResponseBody
    public String index(@PathVariable("id") String id) {
        this.vcs.increase();
        User u = new User();
        u.setName(id);
        users.save(u);
        return "Hello " + id;
    }
}
