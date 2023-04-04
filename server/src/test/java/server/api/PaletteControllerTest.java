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

import java.util.ArrayList;
import java.util.List;

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



    @BeforeEach
    public void setup() {
        repo = new PaletteRepositoryTest();
        br = new BoardRepositoryTest();
        pas = new PaletteService(repo, br);
        pat = new PaletteController(pas);
    }

    @Test
    public void addPaletteToBoardTest(){
        Board board = new Board("b");
        br.save(board);
        Palette palette = new Palette("p", 4, 4);
        List<Palette> palettes = new ArrayList<>();
        palettes.add(palette);
        pat.addPaletteToBoard(board.getId(), palette);
        assertEquals(br.getById(board.getId()).getPalettes(), palettes);

    }

}
