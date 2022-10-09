package locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationParserTest {
    LocationParser locationParser;

    @BeforeEach
    void init(){
    locationParser=new LocationParser();
    }

    @Test
    @DisplayName("parseTest")
    void testParse(){
        Location location= locationParser.parse("Pécs,46.0707,18.2331");

        assertEquals("Pécs", location.getName());
        assertEquals(46.0707, location.getLatitude());
        assertEquals(18.2331, location.getLongitude());
    }

    @Test
    @DisplayName("simultaneousParseTest")
    void testsimultaneousParse(){
        Location location= locationParser.parse("Pécs,46.0707,18.2331");

        assertAll(
                () -> assertEquals("Pécs", location.getName()),
                () -> assertEquals(46.0707, location.getLatitude()),
                () -> assertEquals(18.2331, location.getLongitude())
        );
    }

}