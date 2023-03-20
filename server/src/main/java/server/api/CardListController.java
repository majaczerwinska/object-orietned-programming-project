package server.api;

import commons.CardList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.service.CardListService;

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
    @PutMapping("/{id}")
    public ResponseEntity<CardList> renameList(@PathVariable("id") int id, String newName){
        if(!als.existsById(id)) return ResponseEntity.badRequest().build();
        als.updateTaskListName(als.getById(id), newName);
        return ResponseEntity.ok(als.getById(id));
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


}