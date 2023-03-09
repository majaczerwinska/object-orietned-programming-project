package server.api;

import commons.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.TaskListService;

@RestController
@RequestMapping("/api/lists")
public class TaskListController {
    private TaskListService als;

    /**
     *Constructor
     * @param l - the service on use
     */
    @Autowired
    public TaskListController(TaskListService l) {
        this.als = l;
    }

    /**
     * Renames the tasklist
     * @param id the id of the list
     * @param newName the new name of the list
     * @return a string showing the id of the renamed list and the new name
     */
    @PutMapping("/edit/{id}")
    public String rename(@PathVariable("id") int id, String newName){
        als.updateTaskListName(id, newName);
        return "Updated tasklist " + id + "name to " + newName;
    }


    /**
     *Adds a list ot the database
     * @param list - the list to be added
     * @return - a response entity
     */
    @PostMapping(path = { "", "/" })

    public ResponseEntity<TaskList> addList(@RequestBody TaskList list) {
        if(list.getName()==null) return ResponseEntity.badRequest().build();
        TaskList saved = als.save(list);
        return ResponseEntity.ok(saved);
    }




    /**
     *deletes a list from the database
     * @param id - the id of the list
     * @return - a string showing the id of the deleted list
     */
    @DeleteMapping("/{id}")
    public String deleteList(@PathVariable("id") int id) {
        als.delete(als.getById(id));
        return "Deleted list #" + id;
    }
}
