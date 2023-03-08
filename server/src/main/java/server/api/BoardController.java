package server.api;

import commons.Board;

import commons.Card;
import commons.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.BoardService;

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
     * @return - a string showing the id of the deleted board
     */
    @DeleteMapping("/{id}")
    public String deleteBoard(@PathVariable("id") int id) {
        abs.delete(abs.getById(id));
        return "Deleted board #" + id;
    }
}
