package client.utils;

import commons.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ServerUtilsTest extends ServerUtils{
    Set<Card> cards = new HashSet<>();



    public Card addCard(Card card, int listID) {
        cards.add(card);
        return card;
    }
}