package commons;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "lists")
@JsonIdentityInfo(
        scope = CardList.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class CardList {
    @Id
    @Column(name = "list_id")
    @SequenceGenerator(name = "card_sequence", sequenceName = "card_sequence")
    @GeneratedValue(generator = "card_sequence", strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(name = "list_name")
    private String name;
    @Column(name = "list_b_color")
    private int bColor;
    @Column(name = "list_f_color")
    private int fColor;

    @Column(name = "list_height")
    public int lastPosition;

    @OneToMany( targetEntity = Card.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "list_id")
    private List<Card> cards;



    /**
     *
     * Constructor
     * @param name - the name of the list
     */
    public CardList(String name) {
        this.name = name;
        this.bColor = 11776947;
        this.fColor = 0;
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
     * Changes the background color
     * @param color - the color
     */
    public void setbColor(int color) {
        this.bColor = color;
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
     * Retrieves the background color
     * @return - the color
     */
    public int getbColor() {
        return this.bColor;
    }

    /**
     * Retrieves the font color
     * @return - the color
     */
    public int getfColor(){return this.fColor;}

    /**
     * Sets the font color
     * @param color - the color
     */
    public void setfColor(int color){this.fColor = color;}

    /**
     * Creates a string for the object
     * @return a string representation
     */
    public String toString(){
        String res = "List #" + id + "\n name: " +name + "\n color: " + getbColor() + "\n";
        if (this.cards.size() < 1) {
            res += "this list has no cards";
        } else {
            res += "Cards:\n";
            for (Card c : cards) {
                res += c.toString();
            }
        }
        return res;
    }



  }
