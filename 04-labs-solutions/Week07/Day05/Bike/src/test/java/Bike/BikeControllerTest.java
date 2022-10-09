package Bike;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BikeControllerTest {
    @Autowired
    BikeController bikeController;

    @Test
    void testGetUserIDs(){
        Set<String> answer=bikeController.getUserIDs();
        assertThat(answer)
                .hasSize(5)
                .containsOnly("US3434","US3a34","US3334","US336","US346");
    }
}