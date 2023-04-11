package server.api;

import commons.Board;


//import commons.Card;
import commons.CardList;
import commons.Palette;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import server.service.BoardService;

import java.util.List;


@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private BoardService abs;
    private SimpMessagingTemplate msgs;


    /**
     *Constructor
     * @param abs - the service we use
     * @param msgs the messaging template for messages
     */
    @Autowired
    public BoardController(BoardService abs, SimpMessagingTemplate msgs) {
        this.abs = abs;
        this.msgs = msgs;
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
        msgs.convertAndSend("/topic/admin/", "Board added");
        return ResponseEntity.ok(saved);
    }

    /**
     *Adds the public board to the database if it doesn't already exists
     * @return - a response entity
     */
    @GetMapping("/publicBoard")
    public ResponseEntity<Board> addPublicBoard() {
        if (!abs.existsById(1)) {
            abs.addPublicBoard();
        }
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
        if(id < 0 || !abs.existsById(id)) return ResponseEntity.badRequest().build();
        Board resp = (Board) Hibernate.unproxy(abs.getById(id));
        return ResponseEntity.ok().body(resp);
    }


    /**
     * Gets all lists form a specific board
     * @param boardId the id of the board we need to get the lists from
     * @return a response entity with the list of cardlists from the board
     */
    @GetMapping("/lists/{boardId}")
    public ResponseEntity<List<CardList>> getCardListsFromBoard(@PathVariable("boardId") int boardId) {
        if (boardId < 0 || !abs.existsById(boardId)) {
            return ResponseEntity.badRequest().build();}
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
            return ResponseEntity.badRequest().build();}
        board.setId(id);
        abs.setBoardInfo(board);
        msgs.convertAndSend("/topic/boards/"+id, "Edited board#" + id + " refreshnamecolor");
        msgs.convertAndSend("/topic/admin/", "Edited board#" + id);
        return ResponseEntity.ok().build();
    }

    /**
     * hopefully retrieves palettes from a board whose id is prvided
     * @param boardId
     * @return - list of palettes
     */
    @GetMapping("/palettes/{boardId}")
    public ResponseEntity<List<Palette>> getPalettesFromBoard(@PathVariable("boardId") int boardId) {
        if (boardId < 0 || !abs.existsById(boardId)) {
            return ResponseEntity.badRequest().build();}
        return ResponseEntity.ok(abs.getPalettesFromBoard(boardId));
    }

    /**
     * delete mapping for boards
     * @param id the board's id
     * @return the deleted board instance
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Board> deleteBoard(@PathVariable("id") int id) {
        if (id < 0 || !abs.existsById(id)) {
            return ResponseEntity.badRequest().build();}
        abs.delete(abs.getById(id));
        msgs.convertAndSend("/topic/boards/delete/"+id, "Board#" + id + " deleted");
        msgs.convertAndSend("/topic/admin/", "Deleted board#" + id);
        return ResponseEntity.ok().build();
    }


}
