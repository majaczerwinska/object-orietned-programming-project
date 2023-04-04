package server.api;

import commons.Card;

import commons.Tag;
import commons.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.service.CardService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;


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
     * Receives messages from /app/update/card/{boardId}
     * @param boardId the boardId the message is coming from
     */
    @MessageMapping("/update/card/{boardId}")
    public void messageClient(@DestinationVariable("boardId") int boardId){
        System.out.println("done editing card so refresh");
        msgs.convertAndSend("/topic/boards/"+boardId, "done editing card so refresh");
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
        msgs.convertAndSend("/topic/boards/"+ boardId, "Added card# "+card.getId()+" on board#" + boardId);
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
        if(!acs.existsById(id)) {

            return ResponseEntity.badRequest().build();
        }
        Card card = acs.delete(acs.getById(id), listId);

        if(card==null) return ResponseEntity.badRequest().build();
        msgs.convertAndSend("/topic/boards/"+boardId, "Card deleted on board#" + boardId);
        listeners.forEach((k, l) -> l.accept(card));

        return ResponseEntity.ok().build();
    }

    /**
     * edits card's title, description, color
     * @param id card's id
     * @param card card with updated information
     * @param boardId the id of the board at which the card is located
     * @param ignore indicates whether websockets should ignore this update
     * @return a response entity with the card object
     */
    @PutMapping("/edit/{boardId}/{id}/{ignore}")
    public ResponseEntity<Card> editCard(@PathVariable("boardId") int boardId,
                                         @PathVariable("id") int id,
                                         @PathVariable("ignore") boolean ignore,
                                         @RequestBody Card card) {
        if(!acs.existsById(id) || card.getTitle() == null){
            return ResponseEntity.badRequest().build();
        }

        card.setId(id);
        acs.setCardInfo(card);
        if(!ignore){
            msgs.convertAndSend("/topic/boards/"+boardId , "Card#" +id+ " edited on board#" + boardId);
        }

        return ResponseEntity.ok().build();
    }

    /**
     * Sets the position
     * @param cardId the id of the card
     * @param position the position
     * @param card the card
     * @return the card
     */
    @PostMapping("/position/{cardId}/{position}")
    public ResponseEntity<Card> setPosition(@PathVariable("cardId") int cardId,
                                         @PathVariable("position") int position, @RequestBody Card card) {
        if(!acs.existsById(cardId) || card.getTitle() == null){
            return ResponseEntity.badRequest().build();
        }
        card.setId(cardId);
        acs.setPosition(card,position);
       // acs.setCardInfo(card);
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
    //    System.out.println(card);
     //   System.out.println(acs.getListForCard(card));

        if(!acs.existsById(id) || card.getTitle() == null || acs.getListForCard(card)==null){
//            System.out.println("ChangeList for card id="+id+" failed!");
//            System.out.println(!acs.existsById(id));
//            System.out.println(card.getTitle() == null);
//            System.out.println(acs.getListForCard(card)==null);
            return ResponseEntity.badRequest().build();
        } else {
            System.out.println("No issue with card existence");
        }
        if(acs.getListForCard(card).getId() == listid)  return ResponseEntity.ok(card);

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

    private Map<Object, Consumer<Card>> listeners = new HashMap<>();

    /**
     * Gets the card from its id
     * @param id the id of the card we need to get
     * @return a response entity with the card
     */
    @GetMapping("/deleted/{id}")
    public DeferredResult<ResponseEntity<Card>> registerCardDeleted(@PathVariable("id") int id) {
        ResponseEntity<Object> noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        DeferredResult<ResponseEntity<Card>> res =
                new DeferredResult<>(5000L, noContent);

        Object key = new Object();
        listeners.put(key, c -> {
            if(c.getId() == id){
                res.setResult(ResponseEntity.ok().build());
            } else {
                res.setResult(ResponseEntity.badRequest().build());
            }
        });
        res.onCompletion(() -> listeners.remove(key));
        return res;
    }
}
