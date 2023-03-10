package server;
import commons.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.database.TagRepository;

@Service
public class TagService {
    private final TagRepository repo;

    /**
     * Constructor
     * @param repo the repository used
     */
    @Autowired
    public TagService(@Qualifier("tag") TagRepository repo){
        this.repo =repo;
    }

    /**
     * save a tag to the database
     * @param tag tag to save
     * @return the tag
     */
    public Tag save(Tag tag){
        repo.save(tag);
        return tag;
    }

    /**
     * delete a tag from the database
     * @param tag to delete
     * @return the tag
     */
    public Tag delete(Tag tag){
        repo.delete(repo.getById(tag.getId()));
        return tag;
    }

    /**
     * return tag instance given its id
     * @param id tag id
     * @return tag object
     */
    public Tag getById(int id){
        return repo.getById(id);
    }

    /**
     * return true if a tag with the given id exists, false otherwise
     * @param id tag id
     * @return boolean
     */
    public boolean existsById(int id){
        return repo.existsById(id);
    }

}
