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
     * @param boardId - the board to which the tag is added
     * @return - a response entity
     */
    @PostMapping(path = { "/{boardId}", "/{boardId}" })
    public ResponseEntity<Tag> addTag(@PathVariable("boardId") int boardId, @RequestBody Tag tag) {
        if(tag.getTitle()==null) return ResponseEntity.badRequest().build();
        Tag saved = ser.save(tag,boardId);
        if(saved==null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(saved);
    }

    /**
     *deletes a tag from the database
     * @param id - the id of the tag to be deleted
     * @param boardId - the id of the board from which the tag is deleted
     * @return - a string showing the id of the deleted tag
     */
    @DeleteMapping("/{boardId}/{id}")
    public ResponseEntity<Tag> deleteTag(@PathVariable("boardId") int boardId, @PathVariable("id") int id) {
        if(!ser.existsById(id)) return ResponseEntity.badRequest().build();
        Tag tag = ser.delete(ser.getById(id), boardId);
        if(tag==null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

    /**
     * Adds a tag to the card
     * @param boardId the board to which the card and the tag belong
     * @param cardId the card to which the tag is added
     * @param tagId the tag that is assigned to a card
     * @return the tag that was matched to a card
     */
    @PutMapping( "/{boardId}/{cardId}/{tagId}")
    public ResponseEntity<Tag> addTagToCard(@PathVariable("boardId") int boardId,
                                            @PathVariable("cardId") int cardId,
                                            @PathVariable("tagId") int tagId) {
        if(!ser.existsById(tagId)) return ResponseEntity.badRequest().build();
        Tag saved = ser.addTagToCard(boardId, cardId, tagId);
        if(saved==null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }



}
