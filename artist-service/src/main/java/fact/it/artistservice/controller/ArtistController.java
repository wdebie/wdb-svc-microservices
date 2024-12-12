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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistResponse> getAllArtistsByArtistId(@RequestParam List<String> id){
        return artistService.getAllArtistsById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistResponse> getAllArtists(){return artistService.getAllArtists();}

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createArtist(
            @RequestBody ArtistRequest artistRequest){
        artistService.createArtist(artistRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ArtistResponse update(@RequestBody ArtistRequest artistRequest){
        return artistService.update(artistRequest);
    }

    @DeleteMapping({"/{skuCode}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable String skuCode){
        artistService.delete(skuCode);
    }


}
