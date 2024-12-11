package fact.it.artistservice;

import fact.it.artistservice.dto.ArtistRequest;
import fact.it.artistservice.dto.ArtistResponse;
import fact.it.artistservice.model.Artist;
import fact.it.artistservice.repository.ArtistRepository;
import fact.it.artistservice.service.ArtistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistServiceApplicationTests {

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistService artistService;

    @Test
    void testCreateArtist() {
        ArtistRequest artistRequest = ArtistRequest.builder()
                .name("Test Artist")
                .skuCode("test123")
                .repEmail("test@example.com")
                .repPhone("1234567890")
                .bookingPrice(BigDecimal.valueOf(1000))
                .build();

        artistService.createArtist(artistRequest);

        verify(artistRepository, times(1)).save(any(Artist.class));
    }

    @Test
    void testGetAllArtists() {
        Artist artist = Artist.builder()
                .artistId("1")
                .name("Test Artist")
                .skuCode("test123")
                .repEmail("test@example.com")
                .repPhone("1234567890")
                .bookingPrice(BigDecimal.valueOf(1000))
                .build();

        when(artistRepository.findAll()).thenReturn(List.of(artist));

        List<ArtistResponse> artists = artistService.getAllArtists();

        assertEquals(1, artists.size());
        assertEquals("Test Artist", artists.get(0).getName());
        assertEquals("test123", artists.get(0).getSkuCode());

        verify(artistRepository, times(1)).findAll();
    }

    @Test
    void testGetAllArtistsBySkuCode() {
        Artist artist = Artist.builder()
                .artistId("1")
                .name("Test Artist")
                .skuCode("test123")
                .repEmail("test@example.com") 
                .repPhone("1234567890")
                .bookingPrice(BigDecimal.valueOf(1000))
                .build();

        when(artistRepository.findByArtistIdIn(Arrays.asList("test123"))).thenReturn(List.of(artist));

        List<ArtistResponse> artists = artistService.getAllArtistsById(Arrays.asList("test123"));

        assertEquals(1, artists.size());
        assertEquals("Test Artist", artists.get(0).getName());
        assertEquals("test123", artists.get(0).getSkuCode());

        verify(artistRepository, times(1)).findByArtistIdIn(Arrays.asList("test123"));
    }
}
