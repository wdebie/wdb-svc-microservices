package fact.it.foodservice.dto;

import fact.it.foodservice.model.FoodItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodTruckRequest {
    private String name;
    private String skuCode;
    private String repName;
    private String repPhone;

    private List<FoodItem> foodItemsList;
}
