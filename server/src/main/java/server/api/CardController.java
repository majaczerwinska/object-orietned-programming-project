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
     *Constructor
     * @param c the service we use
     */
    @Autowired
    public CardController(CardService c) {
        this.acs = c;
    }



    /**
     *Adds a card to the database
     * @param card - the card to be added
     * @return - a response entity
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Card> addCard(@RequestBody Card card) {
        if(card.getTitle()==null) return ResponseEntity.badRequest().build();
        Card saved = acs.save(card);
        return ResponseEntity.ok(saved);
    }

    /**
     *deletes a card from the database
     * @param id - the id of the card to be deleted
     * @return - a string showing the id of the deleted card
     */
    @DeleteMapping("/card/{id}/delete")
    public String deleteCard(@PathVariable("id") int id) {
        acs.delete(acs.getById(id));
        return "Deleted card #" + id;
    }

    /**
     * edits card's title, description, color
     * @param id card's id
     * @param card card with updated information
     * @return a response entity with the card object
     */

    @PutMapping("/{id}")
    public ResponseEntity<Card> editCard(@PathVariable("id") int id, @RequestBody Card card) {
        if(!acs.existsById(id) || card.getTitle() == null){
            return ResponseEntity.badRequest().build();
        }
        card.setId(id);
        acs.setCardInfo(card);
        return ResponseEntity.ok(card);
    }
}
