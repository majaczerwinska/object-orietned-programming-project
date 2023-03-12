package server.api;


import commons.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.service.TagService;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private TagService ser;

    /**
     * Constructor
     * @param ser the service used
     */
    @Autowired
    public TagController(TagService ser){
        this.ser = ser;
    }

    /**
     *Adds a tag to the database
     * @param tag - the tag to be added
     * @return - a response entity
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Tag> addTag(@RequestBody Tag tag) {
        if(tag.getTitle()==null) return ResponseEntity.badRequest().build();
        Tag saved = ser.save(tag);
        return ResponseEntity.ok(saved);
    }

    /**
     *deletes a tag from the database
     * @param id - the id of the tag to be deleted
     * @return - a string showing the id of the deleted tag
     */
    @DeleteMapping("/card/{id}/")
    public String deleteTag(@PathVariable("id") int id) {
        ser.delete(ser.getById(id));
        return "Deleted tag #" + id;
    }

}
