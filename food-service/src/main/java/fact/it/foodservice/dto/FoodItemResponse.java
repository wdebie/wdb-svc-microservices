package fact.it.foodservice.dto;

import fact.it.foodservice.model.FoodTruck;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemResponse {
    private Long foodItemId;
    private String skuCode;
    private String name;
    private BigDecimal price;

    @ManyToOne
    private FoodTruck foodTruck;
}
