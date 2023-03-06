package server.api;

import commons.Card;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import server.AddCardService;
import server.VisitCounterService;
import server.database.CardRepository;

@Controller
@RequestMapping("/")
public class CardController {
    private AddCardService acs;
    private CardRepository cards;

    public CardController(AddCardService c, CardRepository cards) {
        this.acs = c;
        this.cards = cards;
    }

    @GetMapping("/cards")
    @ResponseBody
    public String visit() {
        return "cards";
    }
    @GetMapping("/card/{title}")
    @ResponseBody
    public String addCard(@PathVariable("title") String title) {
        int nextid = acs.getNextID();
        Card newCard = new Card(nextid, title);
        cards.save(newCard);
        return "Created card " + title;
    }

    @GetMapping("/card/{id}/delete")
    @ResponseBody
    public String deleteCard(@PathVariable("id") int id) {
        cards.delete(cards.getById(id));
        return "Deleted card #" + id;
    }
}
