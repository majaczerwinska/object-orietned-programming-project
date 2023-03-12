package commons;

import javax.persistence.*;

@Entity
public class Task {

    @Id
    @SequenceGenerator(name = "list_sequence", sequenceName = "list_sequence")
    @GeneratedValue(generator = "list_sequence", strategy = GenerationType.SEQUENCE)
    private int id;
    private String name;


    /**
     * Default constructor
     */
    public Task() {}

    /**
     * Constructor
     * @param name - name of the task to be added to a card
     */
    public Task(String name) {
        this.name = name;
    }

    /**
     * Constructor for testing
     * @param id - id of a task
     * @param name - name of a task
     */
    public Task(int id, String name){
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the id of a task
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of a task
     * @param id - to be assigned id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of a task
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of a task
     * @param name - to be assigned name
     */
    public void setName(String name) {
        this.name = name;
    }
}
