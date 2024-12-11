package fact.it.foodservice.repository;

import fact.it.foodservice.model.FoodTruck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface FoodTruckRepository extends JpaRepository<FoodTruck, Long> {
    List<FoodTruck> findBySkuCodeIn(List<String> skuCode);
    FoodTruck findFoodTruckByFoodTruckId(Long id);
}
