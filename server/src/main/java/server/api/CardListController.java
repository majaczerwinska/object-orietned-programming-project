package server.api;

import commons.Card;
import commons.CardList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import server.service.CardListService;

import java.util.List;

@RestController
@RequestMapping("/api/lists")
public class CardListController {

    private CardListService als;
    private SimpMessagingTemplate msgs;

    /**
     *Constructor
     * @param l - the service on use
     * @param msgs the messaging template for messages
     */
    @Autowired
    public CardListController(CardListService l, SimpMessagingTemplate msgs) {
        this.als = l;
        this.msgs = msgs;
    }

    /**
     * Renames the tasklist
     * @param id the id of the list
     * @param name the new name of the list
     * @param boardId the id of the board at which the cardList is located
     * @return a string showing the id of the renamed list and the new name
     */
    @PutMapping("/{boardId}/{id}")
    public ResponseEntity<CardList> renameList(@PathVariable("boardId") int boardId,
                                               @PathVariable("id") int id,@RequestBody String name){
        if(!als.existsById(id)) return ResponseEntity.badRequest().build();
        als.updateTaskListName(als.getById(id), name);
        msgs.convertAndSend("/topic/boards/"+boardId, "CardList edited on board#" + boardId);
        return ResponseEntity.ok().build();
    }

    /**
     * updates list's color
     * @param id - list id
     * @param list - list to be changed
     * @return - response entity
     */
    @PutMapping("/color/{id}")
    public ResponseEntity<CardList> recolorList(@PathVariable("id") int id,@RequestBody CardList list){
        if(!als.existsById(id)) return ResponseEntity.badRequest().build();
        als.updateTaskListColour(list);
        return ResponseEntity.ok().build();
    }


    /**
     *Adds a list ot the database
     * @param list - the list to be added
     * @param boardId - the board to which we add the list
     * @return - a response entity
     */
    @PostMapping("/{boardId}")

    public ResponseEntity<CardList> addList(@PathVariable("boardId") int boardId, @RequestBody CardList list) {
        if(list.getName()==null) return ResponseEntity.badRequest().build();
        CardList saved = als.save(list, boardId);
        msgs.convertAndSend("/topic/boards/"+boardId, "CardList added on board#" + boardId);
        if(saved == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(saved);
    }

    /**
     *deletes a list from the database
     * @param id - the id of the list
     * @param boardId - the id of the board
     * @return - a response entity
     */
    @DeleteMapping("/{boardId}/{id}")
    public ResponseEntity<CardList> deleteList(@PathVariable("boardId") int boardId, @PathVariable("id") int id) {
        if(!als.existsById(id)) return ResponseEntity.badRequest().build();
        CardList list = als.delete(als.getById(id), boardId);
        msgs.convertAndSend("/topic/boards/"+boardId, "CardList deleted on board#" + boardId);
        if(list==null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

    /**
     * Gets all cards form a specific list
     * @param listId the id of the list we need to get the cards from
     * @return a response entity with the list of cards from the list
     */
    @GetMapping("/{listId}")
    public ResponseEntity<List<Card>> getCardsFromList(@PathVariable("listId") int listId) {
        if (listId < 0 || !als.existsById(listId)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(als.getCardsFromList(listId));
    }

    /**
     * get a lists size
     * @param listId the list id
     * @return int size
     */
    @GetMapping("/{listId}/size")
    public ResponseEntity<Integer> getListSize(@PathVariable("listId") int listId) {
        if (listId < 0 || !als.existsById(listId)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(als.getListSize(listId));
    }

    /**
     * set a lists lastposition index
     * @param listId list id
     * @param size new index
     * @return new size
     */
    @PutMapping("/{listId}/size/{size}")
    public ResponseEntity<Integer> setListSize(@PathVariable("listId") int listId, @PathVariable("size") int size) {
        if (listId < 0 || !als.existsById(listId)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(als.setListSize(listId, size));
    }
}
