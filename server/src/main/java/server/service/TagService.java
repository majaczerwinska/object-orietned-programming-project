package server.service;
import commons.Board;
import commons.Card;
import commons.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.database.BoardRepository;
import server.database.CardRepository;
import server.database.TagRepository;


import java.util.HashSet;
import java.util.List;


@Service
public class TagService {
    private final TagRepository repo;
    private final BoardRepository br;
    private final CardRepository cr;

    /**
     * Constructor
     * @param repo the tag repository used
     * @param br the board repository used
     * @param cr the card repository used
     */
    @Autowired
    public TagService(@Qualifier("tag") TagRepository repo, @Qualifier("board") BoardRepository br,
                      @Qualifier("card") CardRepository cr){
        this.repo =repo;
        this.br = br;
        this.cr = cr;
    }

    /**
     * Gets all tags from a board
     * @param boardId the id of the board we need to get the tags from
     * @return list of tags
     */
    public List<Tag> getTagsFromBoard(int boardId) {
        Board board = br.getById(boardId);
        return board.getTags();
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
        tag.getCards().forEach(card -> card.getTags().remove(tag));
        tag.setCards(new HashSet<>());
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

    /**
     * Adds a tag to the card
     * @param boardId the board to which the card and the tag belong
     * @param cardId the card to which the tag is added
     * @param tagId the tag that is assigned to a card
     * @return the tag that was matched to a card
     */
    public Tag addTagToCard(int boardId, int cardId, int tagId){
        Tag tag = repo.getById(tagId);
        if(!br.existsById(boardId) || !cr.existsById(cardId) ) return null;
        Card card = cr.getById(cardId);
        card.getTags().add(tag);
        cr.save(card);
        tag.getCards().add(card);
        repo.save(tag);
        return tag;
    }

    /**
     * Edits a tag in the database
     * @param tag the id of the tag to be edited
     * @param newTag the new tag with the right attributes
     * @return the edited tag
     */
    @Transactional
    public Tag editTag(Tag tag, Tag newTag) {
            tag.setTitle(newTag.getTitle());
            tag.setColor(newTag.getColor());
            return tag;
    }

    /**
     * removes the tag from the card
     * @param cardId the card
     * @param tagId the tag
     * @return return the card
     */
    public Tag removeTagFromCard(int cardId, int tagId){
        if(!cr.existsById(cardId)) return null;
        Tag tag = repo.getById(tagId);
        Card card = cr.getById(cardId);
        card.getTags().remove(tag);
        cr.save(card);
        tag.getCards().remove(card);
        repo.save(tag);
        return tag;
    }


}
