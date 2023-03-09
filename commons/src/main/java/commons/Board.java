package commons;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Board {

    private int id;
    private int color;
    private String name;
    private String boardkey;



    /**
     * Constructor
     * @param name the name of the board
     */
    public Board( String name){
        this.name = name;
        color = 0xffffff;
//        this.lists = new ArrayList<>();
    }

    /**
     * Default constructor
     */
    public Board(){}

    /**
     * Gets the name of the board
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the name of the board
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

//    /**
//     * Gets the list of lists
//     * @return the list
//     */
//    public List<TaskList> getLists() {
//        return lists;
//    }
//
//    /**
//     * Changes the list of lists
//     * @param lists the new list
//     */
//    public void setLists(List<TaskList> lists) {
//        this.lists = lists;
//    }

    /**
     * Retrieves the key of the board
     * @return the key
     */
    public String getBoardkey() {
        return boardkey;
    }

    /**
     * Retrieves the id of the board
     * @return the id
     */
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the board
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the key of the board
     * @param key the new key
     */
    public void setBoardkey(String key) {
        this.boardkey = key;
    }

    /**
     * Retrieves the color of the board
     * @return the color
     */
    public int getColor() {
        return color;
    }

    /**
     * Changes the color of the board
     * @param colour the new color
     */
    public void setColor(int colour) {
        this.color = colour;
    }

    /**
     * Checks if two boards are equal
     * @param o the other Object
     * @return true iff they are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (id != board.id) return false;
        return Objects.equals(name, board.name);
    }

    /**
     * Hashes the board
     * @return the hashcode of the board
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    /**
     * Creates a string representation of the board
     * @return string of board attributes
     */
    @Override
    public String toString() {
        return "Board#+" + id + "\n" + name + "\n" + boardkey + "\n" + color;
    }
}