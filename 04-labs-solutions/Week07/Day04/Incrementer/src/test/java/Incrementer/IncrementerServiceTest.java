package Incrementer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class IncrementerServiceTest {
    @Test
    @DisplayName("Test for getCounter")
    void getCounterFromServiceTest(){
        IncrementerService incrementerService=new IncrementerService();
        assertThat(incrementerService.getCounter())
                .isEqualTo(0);
    }

    @Test
    @DisplayName("Test for increment")
    void incrementerFromServiceTest(){
        IncrementerService incrementerService=new IncrementerService();
        assertThat(incrementerService.getCounter())
                .isEqualTo(0);
        incrementerService.increment();
        assertThat(incrementerService.getCounter())
                .isEqualTo(1);
    }



}