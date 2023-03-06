package server;

import org.springframework.stereotype.Service;

@Service
public final class AddCardService {
    private int currentID;
    public AddCardService() {
        this.currentID = 0;
    }

    public int getNextID() {
        this.currentID++;
        return this.currentID;
    }
}
