package commons;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @Column(name = "tag_id")
    @SequenceGenerator(name = "card_sequence", sequenceName = "card_sequence")
    @GeneratedValue(generator = "card_sequence", strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(name = "tag_name")
    private String title;
    @Column(name = "tag_color")
    private int color;
    @ManyToMany(mappedBy = "tags")
    private List<Card> cards;



    /**
     * Constructor method
     * 
     * @param title - the title of the tag
     */
    public Tag(String title) {
        this.title = title;
        this.color = 0xffffff;
        this.cards = new ArrayList<>();
    }


    /**
     * Constructor with title and description
     * @param title the title of the tag
     * @param color the color of the tag
     */
    public Tag(String title, int color) {
        this.title = title;
        this.color = color;
        this.cards = new ArrayList<>();
    }



    /**
     * Default constructor method
     */
    public Tag() {
    }

    /**
     * Gets the cards that are assigned to this tag
     * @return a list of cards
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Sets the list of cards to this tag
     * @param cards the list which match this tag
     */
    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * ID setter method
     * 
     * @param id - the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Title setter method
     * 
     * @param title - the new title of the tag
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * Color setter method
     * 
     * @param color - the new color
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * ID getter method
     * 
     * @return - the id
     */
    public int getId() {
        return id;
    }

    /**
     * Title getter method
     * 
     * @return - the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Color getter method
     * 
     * @return - the color
     */
    public int getColor() {
        return color;
    }


    /**
     * Equals method
     * 
     * @param other - the other Object/Tag that they are comparing to
     * @return - if they are the same or not
     */
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other instanceof Tag) {
            Tag that = (Tag) other;
            return id == that.id && this.title.equals(that.title);
        }
        return false;
    }

    /**
     * Tag hashmethod
     * 
     * @return - the hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    /**
     * ToString method of the tag
     * 
     * @return string of Tag attributes
     */
    @Override
    public String toString() {
        return "Tag #" + id + "\n title: " + title + "\n color: " + color;
    }
}
