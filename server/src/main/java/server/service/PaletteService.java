package server.service;

import commons.Board;
import commons.Palette;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.database.BoardRepository;
import server.database.PaletteRepository;

@Service
public class PaletteService {

    private PaletteRepository repo;
    private BoardRepository br;

    /**
     * constructor
     * @param repo
     * @param br
     */
    @Autowired
    public PaletteService(@Qualifier("palette") PaletteRepository repo, @Qualifier("board") BoardRepository br) {
        this.repo = repo;
        this.br = br;
    }

    /**
     * constructor
     * @param palette
     * @param boardId
     * @return
     */
    public Palette save(Palette palette, int boardId){
        if(!br.existsById(boardId)) return null;
        repo.save(palette);
        System.out.println("saved " +palette.getId());
        Board b = br.getById(boardId);
        b.getPalettes().add(palette);
        br.save(b);

        return palette;
    }


}
