package server.api;

import commons.Board;
import commons.Palette;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import server.database.*;
import server.service.CardListService;
import server.service.CardService;
import server.service.PaletteService;

import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

//This annotation loads the WebsocketConfigTest to instantiate websocket server for testing
public class PaletteControllerTest {

    private BoardRepositoryTest br;
    private CardListController con;
    private CardListService ser;
    private CardRepositoryTest cr;
    private CardService cardService;

    private PaletteService pas;

    private PaletteRepositoryTest repo;

    private PaletteController pat;

    @Autowired
    private SimpMessagingTemplate msgs;



    @BeforeEach
    public void setup() {

        repo = new PaletteRepositoryTest();
        br = new BoardRepositoryTest();
        pas = new PaletteService(repo, br);
        pat = new PaletteController(pas, msgs);
    }

//    @Test
//    public void addPaletteToBoardTest(){
//        Board board = new Board("test");
//
//        Palette palette = new Palette("p", 4, 4);
//        List<Palette> palettes = new ArrayList<>();
//        palettes.add(palette);
//        board.setPalettes(palettes);
//        br.save(board);
//        assertEquals(pat.addPaletteToBoard(board.getId(), palette),ResponseEntity.ok(palette));
//
//    }

    @Test
    public void addPaletteToBoardNullTest(){
        Board board = new Board("test");

        Palette palette = new Palette("p", 4, 4);
        List<Palette> palettes = new ArrayList<>();
        palettes.add(palette);
        board.setPalettes(palettes);
        br.save(board);
        assertEquals(pat.addPaletteToBoard(50, palette),ResponseEntity.badRequest().build());

    }



    @Test
    public void deletePaletteEmptyTest(){
        Board board = new Board("test");

        Palette palette = new Palette("p", 4, 4);
        br.save(board);

        assertEquals(pat.deletePalette(palette.getId()), ResponseEntity.badRequest().build());

    }

    @Test
    public void deletePaletteTest(){
        Board board = new Board("test");

        Palette palette = new Palette("p", 4, 4);
        br.save(board);
        pas.delete(palette.getId());

        assertFalse(pas.existsById(palette.getId()));

    }




//    @Test
//    public void deletePaletteTest(){
//        Board board = new Board("test");
//
//        Palette palette = new Palette("p", 4, 4);
//        br.save(board);
//        pas.save(palette, board.getId());
//
//        assertEquals(Objects.requireNonNull(pat.deletePalette(palette.getId())), ResponseEntity.ok(palette));
//    }

    @Test
    public void editPaletteTest(){
        Board board = new Board("test");

        Palette palette = new Palette("p", 4, 4);
        Palette palette1 = new Palette("a", 1, 2);
        br.save(board);
        pas.save(palette, board.getId());

        assertEquals(pat.editPalette(palette.getId(), palette1),ResponseEntity.ok().build());

       // assertEquals(Objects.requireNonNull(pat.deletePalette(palette.getId()).getBody()).getName(), "a");
    }



}
