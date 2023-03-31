package commons;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

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
}
