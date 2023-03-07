package commons;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Objects;

@Entity
public class List {
    @Id
    private int id;
    private String name;
    private int color;
    private java.util.List<Card> cardList;
//    private List<Tag> tags;


    public List(int id, String name, int color, java.util.List<Card> cardList) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.cardList = new ArrayList<Card>();
    }

    protected List() {}
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(int color) {
        this.color = color;
    }



    @Id
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        List list = (List) o;
        return id == list.id && color == list.color && Objects.equals(name, list.name) && Objects.equals(cardList, list.cardList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color, cardList);
    }
}
