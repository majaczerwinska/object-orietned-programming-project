package server.api;

import commons.Card;

import commons.Tag;
import commons.Task;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import server.service.CardService;

import java.util.List;
import java.util.Set;


//import java.util.Random;
//import server.VisitCounterService;
//import server.database.CardRepository;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private CardService acs;

    private SimpMessagingTemplate msgs;

    /**
     *Constructor
     * @param c the service we use
     * @param msgs the messaging template for messages
     */
    public CardController(CardService c, SimpMessagingTemplate msgs) {
        this.acs = c;
        this.msgs = msgs;
    }



    /**
     *Adds a card to the database
     * @param card - the card to be added
     * @param listId - the id of the list to which we add the card
     * @param boardId the id of the board at which the card is located
     * @return - a response entity
     */
    @PostMapping("/{boardId}/{listId}")
    public ResponseEntity<Card> addCard(@PathVariable("boardId") int boardId,
                                        @PathVariable("listId") int listId, @RequestBody Card card) {
        if(card.getTitle()==null) return ResponseEntity.badRequest().build();
        Card saved = acs.save(card, listId);
        msgs.convertAndSend("/topic/boards/"+ boardId, "Card added on board#" + boardId);
        if(saved==null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(saved);
    }

    /**
     *deletes a card from the database
     * @param id - the id of the card to be deleted
     * @param listId - the id of the list from which the card is deleted
     * @param boardId the id of the board at which the card is located
     * @return - a response entity
     */
    @DeleteMapping("/{boardId}/{listId}/{id}")
    public ResponseEntity<Card> deleteCard(@PathVariable("boardId") int boardId,
                                           @PathVariable("listId") int listId, @PathVariable("id") int id) {
        System.out.println("Received DELETE request for boardId="+boardId+ " listID="+listId+" card id="+id);
        if(!acs.existsById(id)) return ResponseEntity.badRequest().build();
        Card card = acs.delete(acs.getById(id), listId);
        msgs.convertAndSend("/topic/boards/"+boardId, "Card deleted on board#" + boardId);

        if(card==null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok().build();
    }

    /**
     * edits card's title, description, color
     * @param id card's id
     * @param card card with updated information
     * @param boardId the id of the board at which the card is located
     * @return a response entity with the card object
     */

//<<<<<<< HEAD
//    @PutMapping("/{id}")
//    public ResponseEntity<Card> editCard(@PathVariable("id") int id, @RequestBody Card card) {
//        System.out.println("editing card with id="+id+" to card="+card);
//=======
    @PutMapping("/edit/{boardId}/{id}")
    public ResponseEntity<Card> editCard(@PathVariable("boardId") int boardId,
                                         @PathVariable("id") int id, @RequestBody Card card) {
        if(!acs.existsById(id) || card.getTitle() == null){
            return ResponseEntity.badRequest().build();
        }

        card.setId(id);
        acs.setCardInfo(card);
        msgs.convertAndSend("/topic/boards/"+boardId , "Card#" +id+ " edited on board#" + boardId);

        return ResponseEntity.ok().build();
    }

    /**
     * Chanhges the list to which the card belongs to
     * @param id the id of the card
     * @param listid the id of the new list
     * @param card the card
     * @return the card
     */
    @PostMapping("/move/{id}/{listid}")
    public ResponseEntity<Card> changeListforCard(@PathVariable("id") int id, @PathVariable("listid") int listid,
                                                  @RequestBody Card card) {
        System.out.println("Changing list for card="+id+" to list="+listid);
//        card.setId(id);
        System.out.println(card);
        System.out.println(acs.getListForCard(card));

        if(!acs.existsById(id) || card.getTitle() == null || acs.getListForCard(card)==null){
            System.out.println("ChangeList for card id="+id+" failed!");
            System.out.println(!acs.existsById(id));
            System.out.println(card.getTitle() == null);
            System.out.println(acs.getListForCard(card)==null);
            return ResponseEntity.badRequest().build();
        } else {
            System.out.println("No issue with card existence");
        }

        Card newcard = acs.changeListOfCard(card, listid);

        return ResponseEntity.ok(newcard);
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

    /**
     * Gets the tags from a card
     * @param id the id of the card we need to get the tags from
     * @return a response entity with the ste of tags
     */
    @GetMapping("/{id}/tags")
    public ResponseEntity<Set<Tag>> getTags(@PathVariable("id") int id) {
        System.out.println("Received request at api/cards/"+id+"/tags");
        if(id < 0 || !acs.existsById(id)) return ResponseEntity.badRequest().body(null);
        Card c = acs.findById(id).get();
        Set<Tag> tags = c.getTags();
        return ResponseEntity.ok(tags);
    }
}
