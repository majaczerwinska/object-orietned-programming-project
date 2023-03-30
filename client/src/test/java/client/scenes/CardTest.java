package client.scenes;

import client.utils.ServerUtils;
import client.utils.ServerUtilsTest;
import commons.Card;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CardTest {
    private ServerUtilsTest server;
    @BeforeEach
    public void setup() {
        server = new ServerUtilsTest();

    }

    @Test
    public void AddCardTest(){
        Card c = new Card("fhg");


    }

}
