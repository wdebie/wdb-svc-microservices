package fact.it.artistservice.repository;

import fact.it.artistservice.model.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public interface ArtistRepository extends MongoRepository<Artist, String> {
    List<Artist> findByArtistIdIn(List<String> artistId);

    Artist findArtistBySkuCode(String skuCode);
}
