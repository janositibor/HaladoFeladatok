package Incrementer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class IncrementerControllerITTest {
    @Autowired
    IncrementerService incrementerService;

    @Autowired
    IncrementerController incrementerController;



    @BeforeEach
    void init() {
        incrementerService.setCounter(0);
    }

    @Test
    void testIncrement() {
        String answer = incrementerController.getCount();

        assertThat(answer).startsWith("1");
        assertThat(answer).contains("<br><br>Generated: ");
    }
}
