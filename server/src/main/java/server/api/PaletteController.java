package server.api;

import commons.Palette;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import server.service.PaletteService;

@RestController
@RequestMapping("api/palettes")
public class PaletteController {

    private PaletteService ps;
    private SimpMessagingTemplate msgs;

    /**
     *
     * @param ps
     * @param msgs the messaging template for messages
     */
    @Autowired
    public PaletteController(PaletteService ps, SimpMessagingTemplate msgs){
        this.ps = ps;
        this.msgs = msgs;
    }

    /**
     *
     * @param boardId
     * @param palette
     * @return - list of palettes
     */
    @PostMapping("/{boardId}")
    public ResponseEntity<Palette> addPaletteToBoard(@PathVariable("boardId") int boardId,
                                                     @RequestBody Palette palette){
        System.out.println(palette);
        Palette saved = ps.save(palette, boardId);
        if(saved == null) return ResponseEntity.badRequest().build();
        System.out.println(palette.getName());
        msgs.convertAndSend("/topic/palletes/"+boardId, "Pallete added on board#" + boardId);
        return ResponseEntity.ok(saved);
    }

    /**
     * deletes the palette from the board
     * @param id
     * @return - response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Palette> deletePalette(@PathVariable int id) {
        if(!ps.existsById(id)) return ResponseEntity.badRequest().build();
        Palette p = ps.delete(id);
        msgs.convertAndSend("/topic/palletes/"+id, "Pallete deleted on board#" + id);
        return ResponseEntity.ok(p);
    }

    /**
     * edits palette (sets default)
     * @param id
     * @param palette
     * @return - response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<Palette> editPalette(@PathVariable("id") int id, @RequestBody Palette palette){
        if(!ps.existsById(id)) return ResponseEntity.badRequest().build();
        Palette updated = ps.updatePalette(ps.getById(id), palette);
        return ResponseEntity.ok().build();
    }
}
