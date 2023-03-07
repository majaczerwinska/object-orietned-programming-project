package commons;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Objects;

@Entity
public class TaskList {
    @Id
    private int id;
    private String name;
    private int color;
    private java.util.List<Card> cardList;
//    private List<Tag> tags;


    /**
     *
     * @param id -
     * @param name -
     * @param color -
     * @param cardList -
     */
    public TaskList(int id, String name, int color, java.util.List<Card> cardList) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.cardList = new ArrayList<Card>();
    }

    protected TaskList() {}

    /**
     *
     * @param id -
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @param name -
     */
    public void setName(String name) {
        this.name = name;
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
    public String getName() {
        return name;
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
     * @param o -
     * @return -
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskList list = (TaskList) o;
        return id == list.id && color == list.color &&
                Objects.equals(name, list.name) &&
                Objects.equals(cardList, list.cardList);
    }


    /**
     *
     * @return -
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, color, cardList);
    }
}
