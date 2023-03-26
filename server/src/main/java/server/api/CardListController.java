package server.api;

import commons.Card;
import commons.CardList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.service.CardListService;

import java.util.List;

@RestController
@RequestMapping("/api/lists")
public class CardListController {

    private CardListService als;

    /**
     *Constructor
     * @param l - the service on use
     */
    @Autowired
    public CardListController(CardListService l) {
        this.als = l;
    }

    /**
     * Renames the tasklist
     * @param id the id of the list
     * @param name the new name of the list
     * @return a string showing the id of the renamed list and the new name
     */
    @PutMapping("/{id}")
    public ResponseEntity<CardList> renameList(@PathVariable("id") int id,@RequestBody String name){
        if(!als.existsById(id)) return ResponseEntity.badRequest().build();
        als.updateTaskListName(als.getById(id), name);
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

}
