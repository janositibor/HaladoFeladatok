package Incrementer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncrementerControllerTest {
    @Mock
    IncrementerService incrementerService;

    @InjectMocks
    IncrementerController incrementerControllerByMock;

    @Test
    @DisplayName("Test for the Controller class")
    void controllerTest(){
        when(incrementerService.getCounter()).thenReturn(2);
        String answer = incrementerControllerByMock.getCount();

        assertThat(answer).startsWith("2");
        assertThat(answer).contains("<br><br>Generated: ");
    }

}