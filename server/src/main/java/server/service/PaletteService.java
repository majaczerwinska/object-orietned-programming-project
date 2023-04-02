package server.service;

import commons.Board;
import commons.Palette;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.database.BoardRepository;
import server.database.PaletteRepository;

import java.util.ArrayList;
import java.util.List;

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
     * @return - saved palette, null if it wasn't successful
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

    /**
     *
     * @param id
     * @return - true if exists
     */
    public boolean existsById(int id){
        return repo.existsById(id);
    }

    /**
     *
     * @param id
     * @return - the palette
     */
    public Palette getById(int id){
        return repo.getById(id);
    }

    /**
     *
     * @param paletteId
     * @param boardId
     * @return - deleted palette
     */
    public Palette delete(int paletteId){
        Palette p = (Palette) Hibernate.unproxy(repo.getById(paletteId));
        repo.delete(p);
        return p;
    }

    /**
     *
     * @param palette
     * @param newPalette
     * @return - updates palette (default)
     */
    public Palette updatePalette(Palette palette, Palette newPalette) {
        palette.setName(newPalette.getName());
        palette.setIsdefault(newPalette.isIsdefault());
        repo.save(palette);
        return palette;
    }


}
