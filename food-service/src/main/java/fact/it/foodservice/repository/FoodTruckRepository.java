package fact.it.foodservice.repository;

import fact.it.foodservice.model.FoodTruck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodTruckRepository extends JpaRepository<FoodTruck, Long> {
    List<FoodTruck> findBySkuCodeIn(List<String> skuCode);
}
