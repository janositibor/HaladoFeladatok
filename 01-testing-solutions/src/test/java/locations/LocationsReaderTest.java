package locations;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SoftAssertionsExtension.class)
public class LocationsReaderTest {
    LocationsReader locationsReader;
    List<Location> readLocations;

    @BeforeEach
    void init(){
        locationsReader=new LocationsReader();
        locationsReader.readDataFromFile(Paths.get("src/test/resources/locationsToRead.txt"));
        readLocations=locationsReader.getLocationList();
    }

    @Test
    @DisplayName("Read Location from File Test")
    void testReadLocations(){
        assertEquals(List.of("Perth","Jyvaskyla","Senja","Geneva","Franz-Josef-Land","North Pole"),readLocations.stream()
                .map(Location::getName)
                .toList());
    }

    @Test
    @DisplayName("filterLocationsBeyondArcticCircle Test")
    void filterLocationsBeyondArcticCircle(){
        assertEquals(List.of("Senja","Franz-Josef-Land","North Pole"),locationsReader.filterLocationsBeyondArcticCircle(readLocations).stream()
                .map(Location::getName)
                .toList());
    }

    @Test
    @DisplayName("AssertJ Test")
    void assertJtest(){
        assertThat(readLocations)
                .extracting(Location::getName)
                .hasSize(6)
                .contains("Franz-Josef-Land", "Jyvaskyla")
                .doesNotContain("Jane Doe")
                .containsOnly("Perth","Jyvaskyla","Senja","Franz-Josef-Land","Geneva","North Pole");

        assertThat(readLocations)
                .filteredOn(l -> l.getLatitude()==l.getLongitude())
                .extracting(Location::getName,Location::getLatitude)
                .hasSize(1)
                .containsOnly(tuple("North Pole",90.0));
    }

    @Test
    @DisplayName("SoftAssertion Test (Without @ExtendWith(SoftAssertionsExtension.class))")
    @Disabled("presenting that SoftAssertions can handle multiple test errors")
    void softAssertionWithoutExtendWithSoftAssertionsExtensiontest(){
        Location location=new Location("Abc",15,115);
        SoftAssertions softly=new SoftAssertions();

        softly.assertThat(location.getName())
                .startsWith("b");
        softly.assertThat(location.getName())
                .endsWith("b");

        softly.assertAll();
    }
    @Test
    @DisplayName("SoftAssertion Test")
    @Disabled("presenting that SoftAssertions can handle multiple test errors")
    void softAssertiontest(SoftAssertions softly){
        Location location=new Location("Abc",15,115);

        softly.assertThat(location.getName())
                .startsWith("b");
        softly.assertThat(location.getName())
                .endsWith("b");

    }
}
