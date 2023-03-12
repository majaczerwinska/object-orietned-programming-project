package commons;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CardList {
    @Id
    @SequenceGenerator(name = "card_sequence", sequenceName = "card_sequence")
    @GeneratedValue(generator = "card_sequence", strategy = GenerationType.SEQUENCE)
    private int id;
    private String name;
    private int color;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Card> cards;



    /**
     *
     * Constructor
     * @param name - the name of the list
     */
    public CardList(String name) {
        this.name = name;
        this.color = 0xffffff;
        this.cards = new ArrayList<>();
    }

    /**
     * Default constructor
     */
    public CardList() {}

    /**
     *Changes the id
     * @param id - the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *Changes the name
     * @param name - the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *Changes the color
     * @param color - the color
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * gets the list of cards
     * @return the list
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Changes the list of cards
     * @param cards -  the new list
     */
    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    /**
     *retrieves the id
     * @return - the id
     */
//    @Id
//    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     *Retrieves the name
     * @return - the name
     */
    public String getName() {
        return name;
    }

    /**
     *Retrieves the color
     * @return - the color
     */
    public int getColor() {
        return color;
    }


  }
