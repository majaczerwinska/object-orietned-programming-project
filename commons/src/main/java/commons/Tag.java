package commons;

import javax.persistence.*;
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
    private String description;
    private int color;


    /**
     * Constructor method
     * 
     * @param title - the title of the tag
     */
    public Tag(String title) {
        this.title = title;
        this.description = "";
        this.color = 0xffffff;

    }



    /**
     * Default constructor method
     */
    public Tag() {
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
     * Description setter method
     * 
     * @param description - the new description of the tag
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Description getter method
     * 
     * @return - the description
     */
    public String getDescription() {
        return description;
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
        return "Tag #+" + id + "\n" + title + "\n" + description + "\n" + color;
    }
}
