package commons;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "BOARDS")
@JsonIdentityInfo(
        scope =Board.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Board {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "card_sequence", sequenceName = "card_sequence")
    @GeneratedValue(generator = "card_sequence", strategy = GenerationType.SEQUENCE)
    public int id;
    @Column(name = "b_color")
    private int bColor;
    @Column(name = "f_color")
    private int fColor;
    @Column(name = "name")
    private String name;
    @Column(name = "boardkey")
    private String boardkey;
    @Column(name = "password")
    private String password;

    @OneToMany(targetEntity = CardList.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "board_id")
    private List<CardList> lists;

    @OneToMany(targetEntity = Tag.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "board_id")
    private List<Tag> tags;

    @OneToMany(targetEntity = Palette.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "board_id")
    private List<Palette> palettes;



    /**
     * Constructor
     * @param name the name of the board
     */
    public Board(String name){
        this.name = name;
        this.bColor = 0xffffff;
        this.fColor = 0x000000;
        this.lists = new ArrayList<>();
        this.boardkey = "";
        this.tags = new ArrayList<>();
        this.password = "";
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
     * password getter method
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * password setter method
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
 
    /**
     * Changes the name of the board
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of lists
     * @return the list
     */
    public List<CardList> getLists() {
        return lists;
    }

    /**
     * Changes the list of lists
     * @param lists the new list
     */
    public void setLists(List<CardList> lists) {
        this.lists = lists;
    }

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
     * Retrieves the background color of the board
     * @return the color
     */
    public int getbColor() {
        return this.bColor;
    }

    /**
     * Changes the background color of the board
     * @param colour the new color
     */
    public void setbColor(int colour) {
        this.bColor = colour;
    }

    /**
     * Retrieves the font color
     * @return - the font color
     */
    public int getfColor(){return this.fColor;}

    /**
     * Changes the font color
     * @param color - a new color
     */
    public void setfColor(int color){this.fColor = color;}

    /**
     * Gets the tags assigned to a board
     * @return all tags in the board
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Sets the tags of the board
     * @param tags the tags of the board
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
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
        return "Board #" + id + "\n name: " + name + "\n key: " + boardkey;
    }

    /**
     * Retrieves palettes from the board
     * @return list of palettes
     */
    public List<Palette> getPalettes() {
        return palettes;
    }

    /**
     * Sets the list of palettes
     * @param palettes - a list of palettes
     */
    public void setPalettes(List<Palette> palettes) {
        this.palettes = palettes;
    }
}
