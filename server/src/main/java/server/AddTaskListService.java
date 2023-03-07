package server;

import commons.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.database.TaskListRepository;

@Service
public final class AddTaskListService {
    private TaskListRepository repo;
    private int currentID;

    /**
     *
     * @param repo -
     */
    @Autowired
    public AddTaskListService(@Qualifier("list") TaskListRepository repo) {
        this.currentID = 0;
        this.repo = repo;
    }

    /**
     *
     * @return -
     */
    public int getNextID() {
        this.currentID++;
        return this.currentID;
    }

    /**
     *
     * @param list -
     * @return -
     */
    public TaskList save(TaskList list){
        repo.save(list);
        return list;
    }

    /**
     *
     * @param list -
     * @return -
     */
    public TaskList delete(TaskList list){
        repo.delete(repo.getById(list.getId()));
        return list;
    }

    /**
     *
     * @param id -
     * @return -
     */
    public TaskList getById(int id){
        TaskList list = repo.getById(id);
        return list;
    }
}
