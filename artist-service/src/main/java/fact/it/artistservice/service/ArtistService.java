package fact.it.artistservice.service;

import fact.it.artistservice.dto.ArtistRequest;
import fact.it.artistservice.dto.ArtistResponse;
import fact.it.artistservice.model.Artist;
import fact.it.artistservice.repository.ArtistRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    @PostConstruct
    public void loadData(){
        if(artistRepository.count() <= 0){
            Artist artist1 = Artist.builder()
                    .skuCode("AR-MEGA-MINDY-01")
                    .name("Mega Mindy")
                    .repEmail("mmm@studio100.be")
                    .repPhone("+32 (0)3 877 60 35")
                    .bookingPrice(BigDecimal.valueOf(3250.00))
                    .build();

            Artist artist2 = Artist.builder()
                    .skuCode("AR-LAURA-TESORO-01")
                    .name("Laura Tesoro")
                    .repEmail("kim@thebookingcompany.be")
                    .repPhone("+32 496 62 35 20")
                    .bookingPrice(BigDecimal.valueOf(3500.00))
                    .build();

            Artist artist3 = Artist.builder()
                    .skuCode("AR-CLOUSEAU-01")
                    .name("Clouseau")
                    .repEmail("info@clouseau.be")
                    .repPhone("+32 (0)2 345 67 89")
                    .bookingPrice(BigDecimal.valueOf(4000.00))
                    .build();

            Artist artist4 = Artist.builder()
                    .skuCode("AR-NIELS-DESTADSBADER-01")
                    .name("Niels Destadsbader")
                    .repEmail("niels@destadsbader.com")
                    .repPhone("+32 (0)9 123 45 67")
                    .bookingPrice(BigDecimal.valueOf(3800.00))
                    .build();

            Artist artist5 = Artist.builder()
                    .skuCode("AR-NATALIA-01")
                    .name("Natalia")
                    .repEmail("contact@natalia.be")
                    .repPhone("+32 (0)4 987 65 43")
                    .bookingPrice(BigDecimal.valueOf(3600.00))
                    .build();

            artistRepository.save(artist1);
            artistRepository.save(artist2);
            artistRepository.save(artist3);
            artistRepository.save(artist4);
            artistRepository.save(artist5);
        }
    }

    public void createArtist(ArtistRequest artistRequest){
        Artist artist = Artist.builder()
                .skuCode(artistRequest.getSkuCode())
                .name(artistRequest.getName())
                .repPhone(artistRequest.getRepPhone())
                .repEmail(artistRequest.getRepEmail())
                .bookingPrice(artistRequest.getBookingPrice())
                .build();
        artistRepository.save(artist);
    }

    public List<ArtistResponse> getAllArtists(){
        List<Artist> artists = artistRepository.findAll();

        return artists.stream().map(this::mapToArtistResponse).toList();
    }
    public List<ArtistResponse> getAllArtistsById(List<String> id){
        List<Artist> artists = artistRepository.findByArtistIdIn(id);
        return artists.stream().map(this::mapToArtistResponse).toList();
    }

    public ArtistResponse update(ArtistRequest artistRequest){
        Artist artist = artistRepository.findArtistBySkuCode(artistRequest.getSkuCode());
        artist.setName(artistRequest.getName());
        artist.setBookingPrice(artistRequest.getBookingPrice());
        artist.setRepPhone(artistRequest.getRepPhone());
        artist.setRepEmail(artistRequest.getRepEmail());
        artistRepository.save(artist);
        return ArtistResponse.builder()
                .artistId(artist.getArtistId())
                .skuCode(artist.getSkuCode())
                .name(artist.getName())
//                .bookingPrice(artist.getBookingPrice())
//                .repPhone(artist.getRepPhone())
//                .repEmail(artist.getRepEmail())
                .build();
    }

    public void delete(String skuCode){
        String artistCode = artistRepository.findArtistBySkuCode(skuCode).getArtistId();
        artistRepository.deleteById(artistCode);
    }

    private ArtistResponse mapToArtistResponse(Artist artist){
        return ArtistResponse.builder()
                .artistId(artist.getArtistId())
                .skuCode(artist.getSkuCode())
                .name(artist.getName())
//                .repEmail(artist.getRepEmail())
//                .repPhone(artist.getRepPhone())
//                .bookingPrice(artist.getBookingPrice())
                .build();
    }
}
