package server.api;

import commons.Board;


//import commons.Card;
import commons.CardList;
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
    public Board getBoardByKey(@PathVariable("key") String key){
        System.out.println("Received get request at /api/boards/key/"+key);
        Board res = abs.findByKey(key);
        System.out.println("result of find by key = "+res);
        return res;
    }

    /**
     * gets a board from database with provided id
     * @param id - id of the board
     * @return - the board
     */
    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoard(@PathVariable("id") int id){
        System.out.println(id);
        if(id < 0 || !abs.existsById(id)) return ResponseEntity.badRequest().body(null);

        return ResponseEntity.ok(abs.findById(id).get());
    }

    /**
     * Gets all lists form a specific board
     * @param boardId the id of the board we need to get the lists from
     * @return a response entity with the list of cardlists from the board
     */
    @GetMapping("/lists/{boardId}")
    public ResponseEntity<List<CardList>> getCardListsFromBoard(@PathVariable("boardId") int boardId) {
        if (boardId < 0 || !abs.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(abs.getCardListsFromBoard(boardId));
    }

    /**
     * edits board name and color
     * @param id board's id
     * @param board board with updated information
     * @return a response entity with the card object
     */

    @PutMapping("/{id}")
    public ResponseEntity<Board> editBoard(@PathVariable("id") int id, @RequestBody Board board) {
        if(!abs.existsById(id) || board.getName() == null){
            return ResponseEntity.badRequest().build();
        }
        board.setId(id);
        abs.setBoardInfo(board);
        return ResponseEntity.ok().build();
    }

//    /**
//     * board getter using the name
//     * @param name name of the board
//     * @return Board instance or null 
//     */
    /*@GetMapping("/name/{name}")
    public Board getBoardByName(@PathVariable("name") String name) {
        System.out.println("Received get request at /api/boards/name/"+name);
        Board res = abs.findByName(name);
        return res;
    }*/
}
