package server.api;

import commons.Board;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.service.BoardService;

import java.util.List;


@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private BoardService abs;


    /**
     *Constructor
     * @param abs - the service we use
     */
    @Autowired
    public BoardController(BoardService abs) {
        this.abs = abs;
    }



    /**
     *Adds a board to the database
     * @param board - the board to be added
     * @return - a response entity
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Board> addBoard(@RequestBody Board board) {
        if(board.getName()==null) return ResponseEntity.badRequest().build();
        Board saved = abs.save(board);
        return ResponseEntity.ok(saved);
    }

    /**
     *deletes a board from the database
     * @param id - the id of the board to be deleted
     * @return - a response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Board> deleteBoard(@PathVariable("id") int id) {
        if(!abs.existsById(id)) return ResponseEntity.badRequest().build();
        Board board = abs.getById(id);
        abs.delete(board);
        return ResponseEntity.ok().build();
    }

    /**
     * gets all boards from the database
     * @return - list of boards
     */
    @GetMapping("/")
    public List<Board> getBoards(){
        return abs.findAll();
    }

    /**
     * Get mapping to fetch a board by its key
     * @param key the board key to search
     * @return the board element if found, else null
     */
    @GetMapping("/key/{key}")
    public Board getBoardByKey(String key){
        return abs.findByKey(key);
    }

    /**
     * gets a board from database with provided id
     * @param id - id of the board
     * @return - the board
     */
    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoard(@PathVariable("id") int id){
        if(id < 0 || !abs.existsById(id)) return ResponseEntity.badRequest().body(null);

        return ResponseEntity.ok(abs.findById(id).get());
    }
}
