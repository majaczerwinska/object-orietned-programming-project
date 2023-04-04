package server.api;

import commons.Board;
import commons.Card;
import commons.CardList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import server.database.BoardRepositoryTest;
import server.database.CardListRepositoryTest;
import server.database.CardRepositoryTest;
import server.service.CardListService;
import server.service.CardService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//This annotation loads the WebsocketConfigTest to instantiate websocket server for testing
public class MainControllerTest {
    private MainController main;


    @BeforeEach
    public void setup() {
        main = new MainController();
    }

    @Test
    public void addPaletteToBoardTest(){
        ResponseEntity<String> response = ResponseEntity.ok("welcome to talio!");
        assertEquals(main.rootGet(), response);
    }

}
