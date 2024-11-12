package fact.it.artistservice.controller;

import fact.it.artistservice.dto.ArtistRequest;
import fact.it.artistservice.dto.ArtistResponse;
import fact.it.artistservice.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/artist")
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistService artistService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createArtist(
            @RequestBody ArtistRequest artistRequest){
        artistService.createArtist(artistRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistResponse> getAllArtistsBySkuCode(@RequestParam List<String> skuCode){
        return artistService.getAllArtistsBySkuCode(skuCode);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistResponse> getAllArtists(){return artistService.getAllArtists();}
}
