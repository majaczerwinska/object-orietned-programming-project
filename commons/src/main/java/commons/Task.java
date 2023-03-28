package commons;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
@JsonIdentityInfo(
        scope = Task.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Task {

    @Id
    @Column(name = "task_id")
    @SequenceGenerator(name = "list_sequence", sequenceName = "list_sequence")
    @GeneratedValue(generator = "list_sequence", strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(name = "task_name")
    private String name;
    @Column(name = "task_checked")
    private boolean checked;


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
        checked = false;
    }

    /**
     * Gets the state of the task
     * @return true iff it is checked
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * Sets the state of the task
     * @param checked the state
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
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

    /**
     * Creates a string of the object
     * @return a text representation
     */
    public  String toString(){
        return "Task #" +id +", name: " + name + ", checked: " + checked;
    }

}
