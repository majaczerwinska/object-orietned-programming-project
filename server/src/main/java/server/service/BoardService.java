package server.service;

import commons.Board;

//import commons.Card;
import commons.CardList;
import commons.Palette;
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
     * @return the deleted board instance
     */
    public Board delete(Board board){
        if(repo.existsById(board.getId())){
            repo.delete(board);
            return board;
        }
        return null;
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
            if (b.getBoardkey()!=null && b.getBoardkey().equals(key)) {
                return b;
            }
        }
        return null;
    }

    /**
     * set board information
     * @param board with updated information
     */

    public void setBoardInfo(Board board){
        Board b = repo.getById(board.getId());
        b.setName(board.getName());
        b.setPassword(board.getPassword());
        b.setbColor(board.getbColor());
        b.setfColor(board.getfColor());
        b.setListb(board.getListb());
        b.setListt(board.getListt());
        repo.save(b);
    }

//    /**
//     * find a board by name in the database
//     * @param name of the board to be searched
//     * @return Board elem or null
//     */
    /*public Board findByName(String name) {
        System.out.println("Find by name called with name = " + name);
        List<Board> l = repo.findAll();
        for (Board b : l) {
            if (b.getName().equals(name)) {
                return b;
            }
        }
        return null;
    }*/

    /**
     * tries to get palettes from a board with given id
     * @param boardId
     * @return - list of palettes from that board
     */
    public List<Palette> getPalettesFromBoard(int boardId){
        Board b = repo.getById(boardId);
        return b.getPalettes();
    }

}
