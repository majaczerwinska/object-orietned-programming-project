package server.api;

import commons.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.AddTaskListService;

@RestController
@RequestMapping("/api/lists")
public class TaskListController {
    private AddTaskListService als;

    /**
     *
     * @param l -
     */
    @Autowired
    public TaskListController(AddTaskListService l) {
        this.als = l;
    }

    /**
     *
     * @return -
     */
    @GetMapping("/")
    //@ResponseBody
    public String visit() {
        return "lists";
    }

    /**
     *
     * @param list -
     * @return -
     */
    @PostMapping(path = { "", "/" })

    public ResponseEntity<TaskList> addList(@RequestBody TaskList list) {
        if(list.getName()==null) return ResponseEntity.badRequest().build();
        TaskList saved = als.save(list);
        return ResponseEntity.ok(saved);
    }

    /**
     *
     * @param id -
     * @return -
     */
    @DeleteMapping("/list/{id}/delete")
    //@ResponseBody
    public String deleteList(@PathVariable("id") int id) {
        als.delete(als.getById(id));
        return "Deleted list #" + id;
    }
}
