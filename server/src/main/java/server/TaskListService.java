package server;

import commons.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.database.TaskListRepository;

@Service
public final class TaskListService {
    private TaskListRepository repo;

    /**
     *Constructor
     * @param repo - the repository in use
     */
    @Autowired
    public TaskListService(@Qualifier("list") TaskListRepository repo) {
        this.repo = repo;
    }


    /**
     *Saves the list in the database
     * @param list - the list to be saved
     * @return - the saved list
     */
    public TaskList save(TaskList list){
        repo.save(list);
        return list;
    }

    /**
     * deletes a list from the database
     * @param list - the list to be deleted
     * @return - the deleted list
     */
    public TaskList delete(TaskList list){
        repo.delete(repo.getById(list.getId()));
        return list;
    }

    /**
     * retrieves the list by id
     * @param id - the id of the list
     * @return - the list
     */
    public TaskList getById(int id){
        TaskList list = repo.getById(id);
        return list;
    }
}
