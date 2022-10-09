package TZJanosi.locations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LocationsController.class)
public class LocationsControllerWebMvcIT {
    @MockBean
    LocationsService locationsService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("All")
    void listLocationsTest() throws Exception{
        when(locationsService.getLocations())
                .thenReturn(List.of(new LocationDto(1L,"Klub",46.1,85.3), new LocationDto(2L,"Müv.ház",46.2,75.4)));
        mockMvc.perform(get("/api/locations"))
//                .andDo(print());
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].name", equalTo("Müv.ház")));
    }

    @Test
    @DisplayName("Filter")
    void listLocationsLikeTest() throws Exception{
        when(locationsService.getLocationsContains(any()))
                .thenReturn(List.of(new LocationDto(1L,"Klub",46.1,85.3), new LocationDto(2L,"Müv.ház",46.2,75.4)));
        mockMvc.perform(get("/api/locationsLike?contains=BarmiLehet"))
//                .andDo(print());
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].name", equalTo("Müv.ház")));
    }

    @Test
    @DisplayName("Location by ID")
    void locationByIDTest() throws Exception{
        when(locationsService.getLocationById(anyLong()))
                .thenReturn(new LocationDto(2L,"Müv.ház",46.2,75.4));
        mockMvc.perform(get("/api/location/101"))
//                .andDo(print());
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Müv.ház")));
    }
}
