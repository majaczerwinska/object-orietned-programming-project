package server.api;

import commons.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService ts;

    /**
     *Constructor
     * @param ts - the service on use
     */

    public TaskController(TaskService ts) {
        this.ts = ts;
    }

    /**
     * Changes the task
     * @param id -  the id of the task
     * @param newName - the new name for the task
     * @return  a response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> editTask(@PathVariable("id") int id, String newName){
        if(!ts.existsById(id)) return ResponseEntity.badRequest().build();
        ts.updateTask(ts.getById(id), newName);
        return ResponseEntity.ok(ts.getById(id));
    }


    /**
     *Adds a task to the card (and the database)
     * @param cardId - the id of the card
     * @param task - the task to be added
     * @return a response entity
     */
    @PostMapping(path = { "", "/{cardId}" })
    public ResponseEntity<Task> addTaskToCard(@PathVariable("cardId") int cardId, @RequestBody Task task) {
        if(task.getName()==null) return ResponseEntity.badRequest().build();
        Task saved = ts.save(task);
        ts.addToCard(cardId, task);
        return ResponseEntity.ok(saved);
    }

    /**
     *Deletes the task from the database
     * @param id - the id of the task
     * @return - a string showing the id of the deleted list
     */
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable("id") int id) {
        ts.delete(ts.getById(id));
        return "Deleted list #" + id;
    }
}
