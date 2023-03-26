package server.api;

import commons.Card;
import commons.Tag;
import commons.Task;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.service.CardService;

import java.util.List;

//import java.util.Random;
//import server.VisitCounterService;
//import server.database.CardRepository;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private CardService acs;


    /**
     *Constructor
     * @param c the service we use
     */
    public CardController(CardService c) {
        this.acs = c;
    }



    /**
     *Adds a card to the database
     * @param card - the card to be added
     * @param listId - the id of the list to which we add the card
     * @return - a response entity
     */
    @PostMapping("/{listId}")
    public ResponseEntity<Card> addCard(@PathVariable("listId") int listId, @RequestBody Card card) {
        if(card.getTitle()==null) return ResponseEntity.badRequest().build();
        Card saved = acs.save(card, listId);
        if(saved==null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(saved);
    }

    /**
     *deletes a card from the database
     * @param id - the id of the card to be deleted
     * @param listId - the id of the list from which the card is deleted
     * @return - a response entity
     */
    @DeleteMapping("/{listId}/{id}")
    public ResponseEntity<Card> deleteCard(@PathVariable("listId") int listId, @PathVariable("id") int id) {
        System.out.println("Received DELETE request for listID="+listId+" card id="+id);
        if(!acs.existsById(id)) return ResponseEntity.badRequest().build();
        Card card = acs.delete(acs.getById(id), listId);

        if(card==null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok().build();
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
        return ResponseEntity.ok().build();
    }

    /**
     * Gets the card from its id
     * @param id the id of the card we need to get
     * @return a response entity with the card
     */
    @GetMapping("/{id}")
    public ResponseEntity<Card> getCard(@PathVariable("id") int id) {
        if(id < 0 || !acs.existsById(id)) return ResponseEntity.badRequest().body(null);
        Card c =acs.findById(id).get();
        return ResponseEntity.ok(c);
    }
    /**
     * Gets the task from a card
     * @param id the id of the card we need to get tke tasks from
     * @return a response entity with the list of tasks
     */
    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<Task>> getTasks(@PathVariable("id") int id) {
        if(id < 0 || !acs.existsById(id)) return ResponseEntity.badRequest().body(null);
        Card c =acs.findById(id).get();
        List<Task> tasks = c.getTasks();
        return ResponseEntity.ok(tasks);
    }
}
