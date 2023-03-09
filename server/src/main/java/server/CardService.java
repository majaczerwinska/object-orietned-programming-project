package server;

import commons.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.database.CardRepository;

@Service
public final class CardService {
    private CardRepository repo;


    /**
     * Constructor
     * @param repo card repository
     */
    @Autowired
    public CardService(@Qualifier("card") CardRepository repo) {
        this.repo = repo;
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

    /**
     * return true if a card with the given id exists, false otherwise
     * @param id card id
     * @return boolean
     */
    public boolean existsById(int id){
        return repo.existsById(id);
    }

    /**
     * set card's information
     * @param card with updated information
     */

    public void setCardInfo(Card card){
        Card c = repo.getById(card.getId());
        c.setDescription(card.getDescription());
        c.setTitle(card.getTitle());
        c.setColor(card.getColor());
        repo.save(c);
    }




}
