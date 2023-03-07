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
    @Autowired
    public AddCardService( @Qualifier("card") CardRepository repo) {
        this.currentID = 0;
        this.repo = repo;
    }

    public int getNextID() {
        this.currentID++;
        return this.currentID;
    }

    public Card save(Card card){
        repo.save(card);
        return card;
    }
    public Card delete(Card card){
        repo.delete(repo.getById(card.getId()));
        return card;
    }

    public Card getById(int id){
        Card card = repo.getById(id);
        return card;
    }




}
