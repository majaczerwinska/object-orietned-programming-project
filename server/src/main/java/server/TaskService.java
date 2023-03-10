package server;

import commons.Card;
import commons.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.database.CardRepository;
import server.database.TaskRepository;


@Service
public final class TaskService {
    private TaskRepository repo;
    @Autowired
    private CardService acs;

    /**
     *Constructor
     * @param repo - the repository in use
     */
    @Autowired
    public TaskService(@Qualifier("task") TaskRepository repo) {
        this.repo = repo;
    }



    /**
     *Saves the task in the database
     * @param task - the task to be saved
     * @return - the saved task
     */
    public Task save(Task task){
        repo.save(task);
        return task;
    }

    /**
     * deletes a list from the database
     * @param task - the list to be deleted
     * @return - the deleted list
     */
    public Task delete(Task task){
        repo.delete(repo.getById(task.getId()));
        return task;
    }

    /**
     * retrieves the list by id
     * @param id - the id of the list
     * @return - the task
     */
    public Task getById(int id){
        Task task = repo.getById(id);
        return task;
    }

    /**
     * Checks if the list exists
     * @param id - the id of the list
     * @return true iff it exists
     */
    public boolean existsById(int id){
        return repo.existsById(id);
    }

    /**
     * Updates the name of a task
     * @param task - the updated task
     * @param newName - the new name for the task
     */
    public void updateTask(Task task, String newName) {
        task.setName(newName);
        repo.save(task);
    }

    /**
     * Adds the task to the card
     * @param cardId - id of the card
     * @param task - the task
     */
    public void addToCard(int cardId, Task task){
        Card card = acs.getById(cardId);
        card.getTasks().add(task);
        acs.save(card);
    }
}
