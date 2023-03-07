package commons;

import javax.persistence.Entity;
import javax.persistence.Id;
//import java.util.ArrayList;
//import java.util.List;
import java.util.Objects;

@Entity
public class Card {
    @Id
    private int id;
    private String title;
    private String description;
    private int color;
//    private List<Card> subtasks;
//    private List<Tag> tags;

    /**
     *
     * @param id -
     * @param title -
     */
    public Card(int id, String title) {
        this.id = id;
        this.title = title;
        this.description = "";
        this.color = 0xffffff;
//        this.subtasks = new ArrayList<>();
    }

    protected Card() {}

    /**
     *
     * @param id -
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     *
     * @param title -
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     *
     * @param description -
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     *
     * @param color -
     */
    public void setColor(int color) {
        this.color = color;
    }


    /**
     *
     * @return -
     */
    @Id
    public int getId() {
        return id;
    }


    /**
     *
     * @return -
     */
    public String getTitle() {
        return title;
    }


    /**
     *
     * @return -
     */
    public int getColor() {
        return color;
    }


    /**
     *
     * @return -
     */
    public String getDescription() {
        return description;
    }


    /**
     *
     * @param o -
     * @return -
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return id == card.id && Objects.equals(title, card.title);
    }


    /**
     *
     * @return -
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }


    /**
     *
     * @return string of card attributes
     */
    @Override
    public String toString() {
        return "Card #+"+id+"\n"+title+"\n"+description+"\n"+color;
//        return "Card{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", description='" + description + '\'' +
//                ", color=" + color +
//                '}';
    }
}
