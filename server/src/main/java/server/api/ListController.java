package server.api;

import commons.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.AddListService;

@RestController
@RequestMapping("/api/lists")
public class ListController {
    private AddListService als;
    @Autowired
    public ListController(AddListService l) {
        this.als = l;
    }

    @GetMapping("/")
    //@ResponseBody
    public String visit() {
        return "lists";
    }
    @PostMapping(path = { "", "/" })

    public ResponseEntity<List> addList(@RequestBody List list) {
        if(list.getName()==null) return ResponseEntity.badRequest().build();
        List saved = als.save(list);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/list/{id}/delete")
    //@ResponseBody
    public String deleteList(@PathVariable("id") int id) {
        als.delete(als.getById(id));
        return "Deleted list #" + id;
    }
}
