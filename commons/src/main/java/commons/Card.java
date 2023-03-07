package commons;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Card {
    @Id
    private int id;
    private String title;
    private String description;
    private int color;
    private List<Card> subtasks;
//    private List<Tag> tags;

    public Card(int id, String title) {
        this.id = id;
        this.title = title;
        this.description = "";
        this.color = 0xffffff;
        this.subtasks = new ArrayList<>();
    }

    protected Card() {}
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Id
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return id == card.id && Objects.equals(title, card.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
