package server.api;

import commons.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.service.TaskService;

import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private TaskService ts;

    /**
     *Constructor
     * @param ts - the task service on use
     */
    @Autowired
    public TaskController(TaskService ts) {
        this.ts = ts;
    }

    /**
     * Changes the task
     * @param id -  the id of the task
     * @param task - the new task for the task
     * @return  a response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> editTask(@PathVariable("id") int id, @RequestBody Task task){
        if(!ts.existsById(id) || task.getName().isEmpty()) return ResponseEntity.badRequest().build();
        Task updated = ts.updateTask(ts.getById(id), task);
        return ResponseEntity.ok().build();
    }


    /**
     *Adds a task to the card (and the database)
     * @param cardId - the id of the card
     * @param task - the task to be added
     * @return a response entity
     */
    @PostMapping( "/{cardId}" )
    public ResponseEntity<Task> addTaskToCard(@PathVariable("cardId") int cardId, @RequestBody Task task) {
        if(task.getName()==null) return ResponseEntity.badRequest().build();
        Task saved = ts.save(task, cardId);
        if(saved==null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(saved);
    }

    /**
     *Deletes the task from the database
     * @param id - the id of the task
     * @param cardId - the id of the card
     * @return - a string showing the id of the deleted list
     */
    @DeleteMapping("/{cardId}/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable("cardId") int cardId, @PathVariable("id") int id) {
        if(!ts.existsById(id)) return ResponseEntity.badRequest().build();
        Task t = ts.delete(ts.getById(id), cardId);
        if(t==null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves a specific task from the database
     * @param id the id of the task
     * @return a response entity with the task
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask( @PathVariable("id") int id){
        if(!ts.existsById(id)) return ResponseEntity.badRequest().build();
        Optional<Task> t = ts.findById(id);
        if(t.isEmpty())return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(t.get());

    }



}
