package server;

import commons.Board;
import commons.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.database.BoardRepository;
import server.database.CardRepository;

@Service
public class BoardService {
    private BoardRepository repo;
    /**
     * Constructor
     * @param repo board repository
     */
    @Autowired
    public BoardService(@Qualifier("board") BoardRepository repo) {
        this.repo = repo;
    }



    /**
     * save a board to the database
     * @param board to save
     * @return the board
     */
    public Board save(Board board){
        repo.save(board);
        return board;
    }

    /**
     * delete a board from the database
     * @param board to delete
     * @return the board
     */
    public Board delete(Board board){
        repo.delete(repo.getById(board.getId()));
        return board;
    }

    /**
     * return board instance given its id
     * @param id board id
     * @return board object
     */
    public Board getById(int id){
        Board board = repo.getById(id);
        return board;
    }

}
