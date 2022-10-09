package locations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationsServiceTest {
    @Mock
    LocationsRepository locationsRepository;

    @InjectMocks
    LocationsService locationsServiceByMock;

    @Test
    @DisplayName("Test calculateDistance without Mockito")
    void calculateDistanceTestWithoutMockito(){
        LocationsRepository locationsRepository=new LocationsRepository();
        LocationsService locationsService=new LocationsService(locationsRepository);
        assertEquals(Optional.empty(),locationsService.calculateDistance("Budapest","Debrecen"));
    }

    @Test
    @DisplayName("Test calculateDistance with Mockito")
    void calculateDistanceTestWithMockito(){
        LocationsRepository locationsRepository=mock(LocationsRepository.class);
        LocationsService locationsService=new LocationsService(locationsRepository);
        assertEquals(Optional.empty(),locationsService.calculateDistance("Budapest","Debrecen"));
        verify(locationsRepository).findByName("Budapest");
        verify(locationsRepository).findByName("Debrecen");
    }

    @Test
    @DisplayName("Test calculateDistance with Mockito ExtendWith")
    void calculateDistanceTestWithMockitoExtendWith(){
        when(locationsRepository.findByName("Budapest"))
                .thenReturn(Optional.of(new Location("Budapest", 47.49791, 19.04023)));
        when(locationsRepository.findByName("Debrecen"))
                .thenReturn(Optional.of(new Location("Debrecen", 47.52997, 21.63916)));

        assertEquals(195.2,locationsServiceByMock.calculateDistance("Budapest","Debrecen").get(),0.01);

        verify(locationsRepository).findByName("Budapest");
        verify(locationsRepository).findByName("Debrecen");
        verify(locationsRepository,never()).saveLocation(any());

        verify(locationsRepository,times(2)).findByName(argThat(s->(s.equals("Budapest")|| s.equals("Debrecen"))));
    }

    @Test
    @DisplayName("Test calculateDistance with Same place")
    void calculateDistanceTestWithSamePlace(){
        when(locationsRepository.findByName(any()))
                .thenReturn(Optional.of(new Location("Budapest", 47.49791, 19.04023)));

        assertEquals(0,locationsServiceByMock.calculateDistance("Budapest","Debrecen").get());

        verify(locationsRepository).findByName("Budapest");
        verify(locationsRepository,never()).saveLocation(any());

        verify(locationsRepository,times(2)).findByName(argThat(s->(s.equals("Budapest")|| s.equals("Debrecen"))));
    }

    @Test
    @DisplayName("Test calculateDistance with Fake Place")
    void calculateDistanceTestWithFakePlace1(){
        when(locationsRepository.findByName("Debrecen"))
                .thenReturn(Optional.of(new Location("Debrecen", 47.52997, 21.63916)));
        when(locationsRepository.findByName("FakePlace"))
                .thenReturn(Optional.empty());

        assertEquals(Optional.empty(),locationsServiceByMock.calculateDistance("FakePlace","Debrecen"));

        verify(locationsRepository).findByName("Debrecen");
        verify(locationsRepository).findByName("FakePlace");
        verify(locationsRepository,never()).saveLocation(any());

        verify(locationsRepository,times(2)).findByName(anyString());
    }

    @Test
    @DisplayName("Test calculateDistance with Fake Place as Secon Parameter")
    void calculateDistanceTestWithFakePlace2(){
        when(locationsRepository.findByName(any()))
                .thenReturn(Optional.of(new Location("Budapest", 47.49791, 19.04023)));
        when(locationsRepository.findByName("FakePlace"))
                .thenReturn(Optional.empty());

        assertEquals(Optional.empty(),locationsServiceByMock.calculateDistance("Budapest","FakePlace"));

        verify(locationsRepository).findByName("Budapest");
        verify(locationsRepository).findByName("FakePlace");
        verify(locationsRepository,never()).saveLocation(any());

        verify(locationsRepository,times(2)).findByName(anyString());
    }



}