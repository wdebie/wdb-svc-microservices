package fact.it.stageservice.repository;

import fact.it.stageservice.model.Stage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface StageRepository extends JpaRepository<Stage, Long> {
    List<Stage> findBySkuCodeIn(List<String> skuCode);
    Stage findStageByStageId(Long id);
}
