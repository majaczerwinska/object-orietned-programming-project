package commons;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Id
    public String getName() {
        return name;
    }
}
