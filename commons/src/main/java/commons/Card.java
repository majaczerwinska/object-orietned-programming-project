package commons;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "cards")
@JsonIdentityInfo(
        scope = Card.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Card {
    @Id
    @Column(name = "card_id")
    @SequenceGenerator(name = "card_sequence", sequenceName = "card_sequence")
    @GeneratedValue(generator = "card_sequence", strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(name = "card_name")
    private String title;
    @Column(name = "card_description")
    private String description;
    @Column(name = "card_color")
    private int color;
    @Column(name = "card_position")
    private int position;
    @ManyToMany
    @JoinTable(
            name = "cards_tags",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @OneToMany(targetEntity = Task.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "card_id")
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
        this.tags = new HashSet<>();
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
     * Gets the position
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the position
     * @param position the position
     */
    public void setPosition(int position) {
        this.position = position;
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
     * Gets the tags of the card
     * @return a list of tags
     */
    public Set<Tag> getTags() {
        return tags;
    }

    /**
     * Sets the list of tags for the card
     * @param tags the tags belonging to the card
     */
    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Checks if card has description
     * @return true if it has description, otherwise false
     */
    public boolean hasDescription(){
        return !(description==null || description=="");
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
        return "Card #" + id + "\n title: " + title + "\n description: " + description + "\n color: "
                + color ;
    }


    /**
     * compare content of two cards, ignoring id. used in frontend for occasions where the card is
     * supposed to be the same but because its a different instance its a different id.
     * for the client side .equals effectively works as == because for the id to be the same
     * the two cards need to be from the same instance
     * this method bypasses that allowing for comparisons with effectively identical cards that just happened to have
     * veen initialised in different places.
     * @param o card object to compare to
     * @return true/false
     */
    public boolean softEquals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return Objects.equals(title, card.title)
                && Objects.equals(description, card.description)
                && Objects.equals(color, card.color);
    }
}
