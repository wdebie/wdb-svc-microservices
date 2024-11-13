package fact.it.stageservice.repository;

import fact.it.stageservice.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StageRepository extends JpaRepository<Stage, Long> {
    List<Stage> findBySkuCodeIn(List<String> skuCode);
}
