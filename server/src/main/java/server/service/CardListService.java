package server.service;


import commons.Board;
import commons.CardList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.database.BoardRepository;
import server.database.CardListRepository;




@Service

public final class CardListService {
    private CardListRepository repo;

    private BoardRepository br;

    /**
     *Constructor
     * @param repo - the list repository in use
     * @param br - the board repository in use
     */
    @Autowired
    public CardListService(@Qualifier("list") CardListRepository repo,@Qualifier("board") BoardRepository br) {
        this.repo = repo;
        this.br = br;
    }


    /**
     *Saves the list in the database
     * @param list - the list to be saved
     * @param boardId - the board to which we save the list
     * @return - the saved list
     */
    public CardList save(CardList list, int boardId){
        if(!br.existsById(boardId)) return null;
        repo.save(list);
        Board board = br.getById(boardId);
        board.getLists().add(list);
        br.save(board);
        return list;

    }

    /**
     * deletes a list from the database
     * @param list - the list to be deleted
     * @param boardId - the board from which we delete the list
     * @return - the deleted list
     */
    public CardList delete(CardList list, int boardId){
        if(!br.existsById(boardId)) return null;
        repo.delete(repo.getById(list.getId()));
        Board board = br.getById(boardId);
        board.getLists().remove(list);
        br.save(board);
        return list;
    }

    /**
     * retrieves the list by id
     * @param id - the id of the list
     * @return - the list
     */
    public CardList getById(int id){
        CardList list = repo.getById(id);
        return list;
    }

    /**
     * Checks if the list exists
     * @param id the id of the list
     * @return true iff it exists
     */
    public boolean existsById(int id){
        return repo.existsById(id);
    }

    /**
     * Updates the name of a list in the database
     * @param list the updated list
     * @param newName the new name for the list
     */
    public void updateTaskListName(CardList list, String newName) {
        list.setName(newName);
        repo.save(list);
    }


}
