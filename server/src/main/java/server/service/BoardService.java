package server.service;

import commons.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.database.BoardRepository;

import javax.transaction.Transactional;
import java.util.List;


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
        //return repo.getById(id);
        System.out.println("board get by id called");
        System.out.println("all boards: "+repo.findAll());
        if (repo.findById(id).isEmpty()) {
            return null;
        }
        return repo.findById(id).get();
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


}
