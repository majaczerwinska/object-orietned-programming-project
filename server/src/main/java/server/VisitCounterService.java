package server;

import org.springframework.stereotype.Service;

@Service
public final class VisitCounterService {
    private int value;

    /**
     *
     */
    public VisitCounterService() {
        this.value = 0;
    }

    /**
     *
     */
    public void increase() {
        this.value++;
    }

    /**
     *
     * @return -
     */
    public int getValue() {
        return this.value;
    }
}
