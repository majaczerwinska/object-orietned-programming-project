package server;

import commons.Board;
import commons.Card;
import commons.CardList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.database.TaskListRepository;


@Service
public final class CardListService {
    private TaskListRepository repo;
    @Autowired
    private BoardService bs;

    /**
     *Constructor
     * @param repo - the repository in use
     */
    @Autowired
    public CardListService(@Qualifier("list") TaskListRepository repo) {
        this.repo = repo;
    }


    /**
     *Saves the list in the database
     * @param list - the list to be saved
     * @return - the saved list
     */
    public CardList save(CardList list){
        repo.save(list);
        return list;
    }

    /**
     * deletes a list from the database
     * @param list - the list to be deleted
     * @return - the deleted list
     */
    public CardList delete(CardList list){
        repo.delete(repo.getById(list.getId()));
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

    public void addToBoard(int boardId, CardList cards){
        Board board = bs.getById(boardId);
        board.getLists().add(cards);
        bs.save(board);

    }
}
