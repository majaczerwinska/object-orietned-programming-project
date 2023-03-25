package server.service;

import commons.Board;

import commons.CardList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.database.BoardRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional

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
     * Gets all lists from a board
     * @param boardId the id of the board we need to get the lists from
     * @return list of cardlists
     */
    public List<CardList> getCardListsFromBoard(int boardId){
        Board board = repo.getById(boardId);
        return board.getLists();
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
     */
    public void delete(Board board){
        if(repo.existsById(board.getId())){
            repo.delete(board);
        }
    }

    /**
     * return board instance given its id
     *
     * @param id board id
     * @return board object
     */
    public Board getById(int id){
        return repo.getById(id);
    }

    /**
     * return board instance given its id
     *
     * @param id board id
     * @return board object
     */
    public Optional<Board> findById(int id){
        return repo.findById(id);
    }

    /**
     * checks if the board exists
     * @param id board id
     * @return board object
     */
    public boolean existsById(int id){
       return repo.existsById(id);
    }

    /**
     * returns all boards from the database
     * @return - the boards
     */
    public List<Board> findAll(){
        return repo.findAll();
    }


    /**
     * searches for a board by key
     * @param key board key
     * @return the board element, if not found null
     */
    public Board findByKey(String key) {
        System.out.println("Board service findByKey called for key = "+key);
        List<Board> l = repo.findAll();
        System.out.println("Repo answered findAll with "+l);
        for (Board b : l) {
            if (b.getBoardkey().equals(key)) {
                return b;
            }
        }
        return null;
    }
}
