package server.service;

import commons.Card;
import commons.CardList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.database.CardListRepository;
import server.database.CardRepository;
import java.util.Optional;


@Service

public final class CardService {

    private CardRepository repo;

    private CardListRepository cl;


    /**
     * Constructor
     * @param repo card repository
     * @param cl card list repository
     */
    @Autowired
    public CardService(@Qualifier("card") CardRepository repo, @Qualifier("list") CardListRepository cl) {
        this.repo = repo;
        this.cl = cl;
    }



    /**
     * save a card to the database
     * @param card card to save
     * @param listId the id of the list in which the card is saved
     * @return the card
     */
    public Card save(Card card, int listId){
        if(!cl.existsById(listId)) return null;
        repo.save(card);
        CardList list = cl.getById(listId);
        list.getCards().add(card);
        cl.save(list);
        return card;
    }

    /**
     * delete a card from the database
     * @param card to delete
     * @param listId the list from which we delete
     * @return the card
     */
    public Card delete(Card card, int listId){
        if(!cl.existsById(listId)) return null;
        repo.delete(repo.getById(card.getId()));
        CardList list = cl.getById(listId);
        list.getCards().remove(card);
        cl.save(list);
        return card;
    }

    /**
     * return card instance given its id
     * @param id card id
     * @return card object
     */
    public Card getById(int id){
         return repo.getById(id);
    }

    /**
     * return card instance given its id
     * @param id card id
     * @return optional card object
     */
    public Optional<Card> findById(int id){
        return repo.findById(id);
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

    /**
     * Finds to which list the card belongs
     * @param card the card
     * @return the list it belongs to
     */
    public CardList getListForCard(Card card){
        if(card==null) return null;

        for(CardList list: cl.findAll()){
            if(list.getCards().contains(card)) return list;
        }
        return null;
    }

    /**
     * Changes to which list the card belongs
     * @param card the card
     * @param listID the new list
     * @return the card
     */
    public Card changeListOfCard(Card card, int listID){
        int id = card.getId();
        CardList oldlist = getListForCard(card);

        oldlist.getCards().remove(card);
        cl.save(oldlist);
        CardList newlist = cl.getById(listID);
        newlist.getCards().add(card);
//        int size = cl.getById(listID).getCards().size();
        System.out.println("List with id="+listID+"\n\n"+cl.getById(listID)+"\n\n\n");
        System.out.println(card+"\n\n");
        cl.save(newlist);
        System.out.println(cl.getById(listID)+"\n\n\n");

        System.out.println(oldlist);
        return newlist.getCards().get(newlist.getCards().size()-1);
    }








}
