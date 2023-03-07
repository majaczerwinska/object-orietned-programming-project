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
    @Autowired
    public TaskListController(AddTaskListService l) {
        this.als = l;
    }

    @GetMapping("/")
    //@ResponseBody
    public String visit() {
        return "lists";
    }
    @PostMapping(path = { "", "/" })

    public ResponseEntity<TaskList> addList(@RequestBody TaskList list) {
        if(list.getName()==null) return ResponseEntity.badRequest().build();
        TaskList saved = als.save(list);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/list/{id}/delete")
    //@ResponseBody
    public String deleteList(@PathVariable("id") int id) {
        als.delete(als.getById(id));
        return "Deleted list #" + id;
    }
}
