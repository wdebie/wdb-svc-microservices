package fact.it.foodservice.repository;

import fact.it.foodservice.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    List<FoodItem> findBySkuCodeIn(List<String> skuCode);
}
