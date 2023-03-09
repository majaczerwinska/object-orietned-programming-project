package server;

//import commons.Card;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import server.database.CardRepository;

@Controller
@RequestMapping("/")
public class SomeController {
    private VisitCounterService vcs;
    private CardRepository users;

    /**
     *
     * @param v -
     * @param users -
     */
    public SomeController(VisitCounterService v, CardRepository users) {
        this.vcs = v;
        this.users = users;
    }

    /**
     *
     * @return -
     */
    @GetMapping("/visit")
    @ResponseBody
    public String visit() {
        this.vcs.increase();
        return "Visits: " + this.vcs.getValue();
    }

    /**
     *
     * @param id -
     * @return -
     */
    @GetMapping("/{id}")
    @ResponseBody
    public String index(@PathVariable("id") String id) {
//        this.vcs.increase();
//        Card u = new Card();
//        u.setName(id);
//        users.save(u);
        return "Hello " + id;
    }
}
