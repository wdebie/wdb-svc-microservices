package fact.it.foodservice.repository;

import fact.it.foodservice.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    List<FoodItem> findBySkuCodeIn(List<String> skuCode);
}
