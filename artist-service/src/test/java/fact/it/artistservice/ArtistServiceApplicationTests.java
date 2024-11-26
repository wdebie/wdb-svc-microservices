package fact.it.artistservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import fact.it.artistservice.repository.ArtistRepository;

@SpringBootTest
class ArtistServiceApplicationTests {

    @MockBean
    private ArtistRepository artistRepository;

    @Test
    void contextLoads() {
    }

}
