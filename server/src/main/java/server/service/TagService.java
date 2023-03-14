package server.service;
import commons.Board;
import commons.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.database.BoardRepository;
import server.database.TagRepository;

@Service
public class TagService {
    private final TagRepository repo;
    private final BoardRepository br;

    /**
     * Constructor
     * @param repo the tag repository used
     * @param br the board repository used
     */
    @Autowired
    public TagService(@Qualifier("tag") TagRepository repo, BoardRepository br){
        this.repo =repo;
        this.br = br;
    }

    /**
     * save a tag to the database
     * @param tag tag to save
     * @param boardId the board where the tag is saved
     * @return the tag
     */
    public Tag save(Tag tag, int boardId){
        if(!br.existsById(boardId)) return null;
        repo.save(tag);
        Board board = br.getById(boardId);
        board.getTags().add(tag);
        br.save(board);
        return tag;
    }

    /**
     * delete a tag from the database
     * @param tag to delete
     * @param boardId the board from which we delete the tag
     * @return the tag
     */
    public Tag delete(Tag tag, int boardId){
        if(!br.existsById(boardId)) return null;
        Board board = br.getById(boardId);
        board.getTags().remove(tag);
        br.save(board);
        repo.delete(tag);
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
