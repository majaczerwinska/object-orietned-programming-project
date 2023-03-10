package server.api;

import commons.Card;
import commons.CardList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.CardListService;

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
     * @param newName the new name of the list
     * @return a string showing the id of the renamed list and the new name
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<CardList> renameList(@PathVariable("id") int id, String newName){
        if(!als.existsById(id)) return ResponseEntity.badRequest().build();
        als.updateTaskListName(als.getById(id), newName);
        return ResponseEntity.ok(als.getById(id));
    }


    /**
     *Adds a list ot the database
     * @param list - the list to be added
     * @return - a response entity
     */
    @PostMapping(path = { "", "/" })

    public ResponseEntity<CardList> addList(@RequestBody CardList list) {
        if(list.getName()==null) return ResponseEntity.badRequest().build();
        CardList saved = als.save(list);
        return ResponseEntity.ok(saved);
    }

    @PostMapping(path = {"/{boardId}"})
    public ResponseEntity<CardList> addListToBoard(@PathVariable("boardId") int listId, @RequestBody CardList cards) {
        // if(!cls.existsById(listId)) return ResponseEntity.badRequest().build();
        if(cards.getName()==null) return ResponseEntity.badRequest().build();
        CardList saved = als.save(cards);
        als.addToBoard(listId, cards);
        return ResponseEntity.ok(saved);
    }




    /**
     *deletes a list from the database
     * @param id - the id of the list
     * @return - a string showing the id of the deleted list
     */
    @DeleteMapping("/{id}")
    public String deleteList(@PathVariable("id") int id) {
        als.delete(als.getById(id));
        return "Deleted list #" + id;
    }


}
