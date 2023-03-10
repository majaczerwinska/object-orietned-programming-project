package commons;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Card {
    @Id
    @SequenceGenerator(name = "card_sequence", sequenceName = "card_sequence")
    @GeneratedValue(generator = "card_sequence", strategy = GenerationType.SEQUENCE)
    private int id;
    private String title;
    private String description;
    private int color;

    @OneToMany
    private List<Task> tasks;


    /**
     *Constructor
     * @param title - the name of the card
     */
    public Card(String title) {
        this.title = title;
        this.description = "";
        this.color = 0xffffff;
        this.tasks = new ArrayList<>();
    }

    /**
     * Default constructor
     */
    public Card() {}

    /**
     *Sets the id of the card
     * @param id - the id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     *Sets the title of the card
     * @param title - the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     *Sets the description of the card
     * @param description - the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     *Sets the color of the card
     * @param color - the new color
     */
    public void setColor(int color) {
        this.color = color;
    }


    /**
     *Retrieves the id of the card
     * @return - the id
     */
//    @Id
//    @GeneratedValue
    public int getId() {
        return id;
    }


    /**
     *Retrieves the title of the card
     * @return - the title
     */
    public String getTitle() {
        return title;
    }


    /**
     *Retrieves the color of the card
     * @return - the color
     */
    public int getColor() {
        return color;
    }


    /**
     *Retrieves the description of the card
     * @return - the description
     */
    public String getDescription() {
        return description;
    }


    /**
     * Retrieves the card the task is in
     * @return - the TaskList
     */
    public List<Task> getTasks() {return tasks;}

    /**
     * Sets the list that belongs to a card
     * @param tasks - the list
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }


    /**
     *checks if two cards are equal
     * @param o - the other object
     * @return - true iff they are
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return id == card.id && Objects.equals(title, card.title);
    }


    /**
     *hashes the card
     * @return - the hashcode of the card
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }


    /**
     * Creates a string representation of the card
     * @return string of card attributes
     */
    @Override
    public String toString() {
        return "Card #+" + id + "\n" + title + "\n" + description + "\n" + color;
    }
}
