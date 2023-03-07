package server;

import org.springframework.stereotype.Service;

@Service
public class NameExpansionService {
    public int value;
    public NameExpansionService(){
        this.value = 0;
    }
    public void increase() {
        this.value++;
    }
    public int getValue(){
        return this.value;
    }


}
