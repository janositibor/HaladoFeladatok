package locations;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationRepeatTest {
    double[][] inputData = new double[4][3];

    @BeforeEach
    void init(){
        double[] infoArray1={46.0707, 18.2331,0};
        double[] infoArray2={0, 18.2331,1};
        double[] infoArray3={-31.953512,115.857048,0};
        double[] infoArray4={0, 115.857048,1};

        inputData[0] = infoArray1;
        inputData[1] = infoArray2;
        inputData[2] = infoArray3;
        inputData[3] = infoArray4;
    }

    @RepeatedTest(value = 4,
            name = "Test Equator {currentRepetition}/{totalRepetitions}")

    void testEquator(RepetitionInfo info){
        boolean isOnEquatorExpected= inputData[info.getCurrentRepetition() - 1][2]==1;
        Location location = new Location("", inputData[info.getCurrentRepetition() - 1][0],inputData[info.getCurrentRepetition() - 1][1]);
        assertEquals(isOnEquatorExpected,location.isOnEquator());
    }
}
