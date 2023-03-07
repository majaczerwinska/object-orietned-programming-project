package server;

import commons.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.database.ListRepository;

@Service
public final class AddListService {
    private ListRepository repo;
    private int currentID;
    @Autowired
    public AddListService(@Qualifier("list") ListRepository repo) {
        this.currentID = 0;
        this.repo = repo;
    }

    public int getNextID() {
        this.currentID++;
        return this.currentID;
    }

    public List save(List list){
        repo.save(list);
        return list;
    }
    public List delete(List list){
        repo.delete(repo.getById(list.getId()));
        return list;
    }

    public List getById(int id){
        List list = repo.getById(id);
        return list;
    }




}
