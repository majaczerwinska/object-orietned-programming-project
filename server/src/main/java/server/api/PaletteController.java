package server.api;

import commons.Palette;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.service.PaletteService;

@RestController
@RequestMapping("api/palettes")
public class PaletteController {

    private PaletteService ps;

    /**
     *
     * @param ps
     */
    @Autowired
    public PaletteController(PaletteService ps){
        this.ps = ps;
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
        return ResponseEntity.ok(saved);
    }

}
