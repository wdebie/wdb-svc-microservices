package fact.it.foodservice.dto;

import fact.it.foodservice.model.FoodItem;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodTruckResponse {
    private Long foodTruckId;
    private String skuCode;
    private String name;
    private String repName;
    private String repPhone;

//    @OneToMany(mappedBy = "foodTruck")
//    private List<FoodItem> foodItems;
}
