package server.api;

import commons.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.CardService;
//import server.VisitCounterService;
//import server.database.CardRepository;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    private CardService acs;
    //private CardRepository cards;

    /**
     *
     * @param c -
     */
    @Autowired
    public CardController(CardService c) {
        this.acs = c;
        //this.cards = cards;
    }



    /**
     *
     * @param card -
     * @return -
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Card> addCard(@RequestBody Card card) {
        //int nextid = acs.getNextID();
       // Card newCard = new Card(nextid, title);
     //   cards.save(newCard);
        if(card.getTitle()==null) return ResponseEntity.badRequest().build();
        Card saved = acs.save(card);
        return ResponseEntity.ok(saved);
    }

    /**
     *
     * @param id -
     * @return -
     */
    @DeleteMapping("/card/{id}/delete")
    //@ResponseBody
    public String deleteCard(@PathVariable("id") int id) {
        acs.delete(acs.getById(id));
        return "Deleted card #" + id;
    }
}
