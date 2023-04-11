package commons;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "palettes")
@JsonIdentityInfo(
        scope = Palette.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Palette {
    @Id
    @Column(name = "palette_id")
    @SequenceGenerator(name = "palette_sequence", sequenceName = "palette_sequence")
    @GeneratedValue(generator = "palette_sequence", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "palette_name")
    private String name;

    @Column(name = "bgd_color")
    private int bColor;

    @Column(name = "font_color")
    private int fColor;

    @Column(name = "isdefault")
    private boolean isdefault;

    /**
     * Default constructor
     */
    public Palette() {}

    /**
     *
      * @param name
     * @param bColor
     * @param fColor
     */
    public Palette(String name, int bColor, int fColor) {
        this.name = name;
        this.bColor = bColor;
        this.fColor = fColor;
        this.isdefault = false;
    }

    /**
     * Checks whether the color palette is set as a default one
     * @return true if it is default, false otherwise
     */
    public boolean isIsdefault(){
        return isdefault;
    }

    /**
     * Change whether a palette is default or not
     * @param isdefault
     */
    public void setIsdefault(boolean isdefault) {
        this.isdefault = isdefault;
    }

    /**
     * Returns the id of a palette
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of a palette
     * @param id - to be assigned id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of a palette
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of a palette
     * @param name - to be assigned name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the background color
     * @return the color
     */
    public int getbColor() {
        return bColor;
    }

    /**
     * Sets the background color
     * @param bColor - a new color
     */
    public void setbColor(int bColor) {
        this.bColor = bColor;
    }

    /**
     * Gets the font color
     * @return the color
     */
    public int getfColor() {
        return fColor;
    }

    /**
     * Sets the font color
     * @param fColor - a new color
     */
    public void setfColor(int fColor) {
        this.fColor = fColor;
    }

    /**
     * checks if equal
     * @param o
     * @return - true if yes
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Palette palette = (Palette) o;
        return id == palette.id && bColor == palette.bColor && fColor == palette.fColor
                && isdefault == palette.isdefault && name.equals(palette.name);
    }

    /**
     *
     * @return - hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, bColor, fColor, isdefault);
    }

    /**
     * to string
     * @return - string representation of palette
     */
    @Override
    public String toString() {
        return "Palette{" +
                "id=" + id +
                ", name= " + name + '\'' +
                ", bColor=" + bColor +
                ", fColor=" + fColor +
                ", isdefault=" + isdefault +
                '}';
    }
}
