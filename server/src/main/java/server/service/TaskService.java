package server.service;

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

    private CardRepository cr;

    /**
     * Constructor
     * @param cr - the card repository in use
     * @param repo - the task repository in use
     */
    @Autowired
    public TaskService(@Qualifier("task") TaskRepository repo, @Qualifier("card") CardRepository cr) {
        this.repo = repo;
        this.cr = cr;
    }



    /**
     *Saves the task in the database
     * @param task - the task to be saved
     * @param cardId - the id of the card where the task will be saved
     * @return - the saved task
     */
    public Task save(Task task, int cardId){
        if(!cr.existsById(cardId)) return null;
        repo.save(task);
        Card card = cr.getById(cardId);
        card.getTasks().add(task);
        cr.save(card);

        return task;
    }

    /**
     * deletes a list from the database
     * @param task - the list to be deleted
     * @param cardId - the id of the card from which we delete the task
     * @return - the deleted list
     */
    public Task delete(Task task ,int cardId){
        Card card = cr.getById(cardId);
        card.getTasks().remove(task);
        cr.save(card);
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


}
