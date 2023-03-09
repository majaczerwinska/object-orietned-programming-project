package server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisitCounterServiceTest {

    @Test
    void increase() {
        VisitCounterService v = new VisitCounterService();
        v.increase();
        assertEquals(v.getValue(), 1);
    }

    @Test
    void getValue() {
        VisitCounterService v = new VisitCounterService();
        assertEquals(v.getValue(), 0);
    }
}