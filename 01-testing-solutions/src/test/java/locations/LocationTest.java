package locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    Location location;

    @BeforeEach
    void init(){
        location = new Location("Pécs", 46.0707, 18.2331);
    }



    @DisplayName("Parametrized Test from File")
    @ParameterizedTest(name = "Latitude = {0}, Longitude = {1}, Expected = {2}")
    @CsvFileSource(resources = "/location.csv")
    void testPrimeMeridianFromFile(int latitude, int longitude, boolean expected){
        Location location = new Location("", latitude,longitude);
        assertEquals(expected,location.isOnPrimeMeridian());
    }

    @DisplayName("Parametrized Test")
    @ParameterizedTest(name = "Latitude = {0}, Longitude = {1}, Expected = {2}")
    @MethodSource("getLocations")
    void testPrimeMeridian(int latitude, int longitude, boolean expected){
        Location location = new Location("", latitude,longitude);
        assertEquals(expected,location.isOnPrimeMeridian());
    }

    static Stream<Arguments> getLocations() {
        return Stream.of(
                Arguments.arguments(46, 18, false),
                Arguments.arguments(46, 0, true),
                Arguments.arguments(-31,115, false),
                Arguments.arguments(-31,0, true)
        );
    }


    @Test
    @DisplayName("WrongLatitudeTest")
    void wrongLatitude(){
        IllegalArgumentException iae1 = assertThrows(IllegalArgumentException.class, () -> new Location("FakePlace",-91,85));
        IllegalArgumentException iae2 = assertThrows(IllegalArgumentException.class, () -> new Location("FakePlace",91,85));

        assertAll(
                () -> assertEquals("The latitude must be between -90 and 90 degree!", iae1.getMessage()),
                () -> assertEquals("The latitude must be between -90 and 90 degree!", iae2.getMessage())
        );
    }

    @Test
    @DisplayName("WrongLongitudeTest")
    void wrongLongitude(){
        IllegalArgumentException iae1 = assertThrows(IllegalArgumentException.class, () -> new Location("FakePlace",-19,-181));
        IllegalArgumentException iae2 = assertThrows(IllegalArgumentException.class, () -> new Location("FakePlace",19,181));

        assertAll(
                () -> assertEquals("The longitude must be between -180 and 180 degree!", iae1.getMessage()),
                () -> assertEquals("The longitude must be between -180 and 180 degree!", iae2.getMessage())
        );
    }

    @Test
    @DisplayName("getNameTest")
    void getName() {
        assertEquals("Pécs", location.getName());
    }

    @Test
    @DisplayName("setNameTest")
    void setName() {
        location.setName("Belvárdgyula");
        assertEquals("Belvárdgyula", location.getName());
    }

    @Test
    @DisplayName("getLatitudeTest")
    void getLatitude() {
        assertEquals(46.0707, location.getLatitude());
    }

    @Test
    @DisplayName("getLatitudeTest")
    void getLongitude() {
        assertEquals(18.2331, location.getLongitude());
    }

    @Test
    @DisplayName("isOnEquatorTest")
    void isOnEquator() {
        assertEquals(false, location.isOnEquator());
        location.setLatitude(0);
        assertEquals(true, location.isOnEquator());
    }

    @Test
    @DisplayName("isOnPrimeMeridianTest")
    void isOnPrimeMeridian() {
        assertEquals(false, location.isOnPrimeMeridian());
        location.setLongitude(0);
        assertEquals(true, location.isOnPrimeMeridian());
    }

    @Test
    @DisplayName("distanceFromDifferentCityTest")
    void distanceFrom(){
        Location location1 = new Location("Budapest", 47.49791, 19.04023);
        Location location2 = new Location("Debrecen", 47.52997, 21.63916);

        assertEquals(195.2,location1.distanceFrom(location2),0.01);
    }

    @Test
    @DisplayName("distanceFromSameCityTest")
    void distanceFromSame(){
        Location location1 = new Location("Budapest", 47.49791, 19.04023);
        Location location2 = new Location("Debrecen", 47.52997, 21.63916);

        assertEquals(0,location1.distanceFrom(location1));
    }


    @Test
    @DisplayName("differentLocationsTest")
    void testDifferentLocations(){
        Location location1 = new Location("Budapest", 47.49791, 19.04023);
        Location location2 = new Location("Budapest", 47.49791, 19.04023);

        assertAll(
                () -> assertNotSame(location1,location2),
                () -> assertEquals(location1,location2)
        );
    }

}