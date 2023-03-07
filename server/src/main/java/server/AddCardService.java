package server;

import commons.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.database.CardRepository;

@Service
public final class AddCardService {
    private CardRepository repo;
    private int currentID;

    /**
     * Constructor
     * @param repo card repository
     */
    @Autowired
    public AddCardService( @Qualifier("card") CardRepository repo) {
        this.currentID = 0;
        this.repo = repo;
    }

    /**
     * deprecated
     * @return id
     */
    public int getNextID() {
        this.currentID++;
        return this.currentID;
    }

    /**
     * save a card to the database
     * @param card card to save
     * @return the card
     */
    public Card save(Card card){
        repo.save(card);
        return card;
    }

    /**
     * delete a card from the database
     * @param card to delete
     * @return the card
     */
    public Card delete(Card card){
        repo.delete(repo.getById(card.getId()));
        return card;
    }

    /**
     * return card instance given its id
     * @param id card id
     * @return card object
     */
    public Card getById(int id){
        Card card = repo.getById(id);
        return card;
    }




}
