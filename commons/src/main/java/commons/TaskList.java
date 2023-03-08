package commons;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class TaskList {

    private int id;
    private String name;
    private int color;


   // private List<Card> cardList;
//    private List<Tag> tags;


    /**
     *
     * Constructor
     * @param name - the name of the list
     */
    public TaskList( String name) {
        this.id = id;
        this.name = name;

    }

    /**
     * Default constructor
     */
    public TaskList() {}

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

//    /**
//     * gets the list of cards
//     * @return the list
//     */
//    public List<Card> getCardList() {
//        return cardList;
//    }
//
//    /**
//     * Changes the list of cards
//     * @param cardList the new list
//     */
//    public void setCardList(List<Card> cardList) {
//        this.cardList = cardList;
//    }

    /**
     *retrieves the id
     * @return - the id
     */
    @Id
    @GeneratedValue
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
