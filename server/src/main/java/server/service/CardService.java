package server.service;

import commons.Card;
import commons.CardList;
import commons.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.database.CardListRepository;
import server.database.CardRepository;

import java.util.*;


@Service
@Transactional
public class CardService {

    private CardRepository repo;

    private CardListRepository cl;


    /**
     * Constructor
     *
     * @param repo card repository
     * @param cl   card list repository
     */
    @Autowired
    public CardService(@Qualifier("card") CardRepository repo, @Qualifier("list") CardListRepository cl) {
        this.repo = repo;
        this.cl = cl;
    }


    /**
     * save a card to the database
     *
     * @param card   card to save
     * @param listId the id of the list in which the card is saved
     * @return the card
     */
    public Card save(Card card, int listId) {
        if (!cl.existsById(listId)) return null;
        CardList list = cl.getById(listId);
        list.getCards().add(card);
        card.setPosition(list.getCards().indexOf(card));
        repo.save(card);
        cl.save(list);
        return card;
    }

    /**
     * delete a card from the database
     *
     * @param card   to delete
     * @param listId the list from which we delete
     * @return the card
     */
    public Card delete(Card card, int listId) {
        if (!cl.existsById(listId)) {
            return null;

        }
        card.getTags().forEach(tag -> tag.getCards().remove(card));
        card.setTags(new HashSet<>());
        repo.delete(card);
        repo.flush();
        List<Card> list = cl.getById(listId).getCards();
        List<Card> l = listsort(list);

       // repo.existsById(card.getId());
        for (int i = 0; i < list.size(); i++) {
            l.get(i).setPosition(i);
            repo.save(l.get(i));
        }
        return card;
    }

    /**
     * Sorts the list
     * @param list the list to be sorted
     * @return sorted list
     */
    public List<Card> listsort(List<Card> list){
        list.sort(Comparator.comparing(Card::getPosition));
        return list;
    }

    /**
     * return card instance given its id
     *
     * @param id card id
     * @return card object
     */
    public Card getById(int id) {
        return repo.getById(id);
    }

    /**
     * return card instance given its id
     *
     * @param id card id
     * @return optional card object
     */
    public Optional<Card> findById(int id) {
        return repo.findById(id);
    }

    /**
     * return true if a card with the given id exists, false otherwise
     *
     * @param id card id
     * @return boolean
     */
    public boolean existsById(int id) {
        return repo.existsById(id);
    }

    /**
     * Sets the information of the card
     *
     * @param card the card
     */
    public void setCardInfo(Card card) {
        Card c = repo.getById(card.getId());
        c.setDescription(card.getDescription());
        c.setTitle(card.getTitle());
        c.setColor(card.getColor());
        c.setFcolor(card.getFcolor());
        c.setPalette(card.getPalette());
        c.setPosition(card.getPosition());
        repo.save(c);
    }

    /**
     * Finds to which list the card belongs
     *
     * @param card the card
     * @return the list it belongs to
     */
    public CardList getListForCard(Card card) {
        if (card == null) return null;

        for (CardList list : cl.findAll()) {
            if (list.getCards().contains(card)) return list;
        }
        return null;
    }

    /**
     * Changes to which list the card belongs
     *
     * @param card   the card
     * @param listID the new list
     * @return the card
     */
    public Card changeListOfCard(Card card, int listID) {
        int id = card.getId();
        CardList oldlist = getListForCard(card);
        Set<Tag> tags = card.getTags();
        delete(card, oldlist.getId());
        card.setId(id);
        card.setTags(tags);
        save(card,listID);
        return card;

    }

    /**
     * Sets the position
     * @param card the card
     * @param position the position
     * @return the card
     */
    public Card setPosition(Card card, int position) {
        CardList list = getListForCard(card);
        List<Card> cardlist = list.getCards();
        cardlist.remove(card);
        cardlist.sort(Comparator.comparing(Card::getPosition));
        if (position > card.getPosition()) position = position - 1;
        cardlist.add(position, card);
        for (int i = 0; i < cardlist.size(); i++) {
            cardlist.get(i).setPosition(i);
            repo.save(cardlist.get(i));
        }
        list.setCards(cardlist);
//        repo.flush();
        //System.out.println("\n\n\n\n\n\n" + list.getCards());
        cl.save(list);
        //System.out.println("\n\n\n\n\n\n" + cl.getById(list.getId()).getCards());
        return card;
    }


}
