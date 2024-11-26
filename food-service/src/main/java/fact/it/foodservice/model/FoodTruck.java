package fact.it.foodservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "foodTruck")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodTruck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodTruckId;
    private String name;
    private String skuCode;
    private String repName;
    private String repPhone;

    @OneToMany(mappedBy = "foodTruck")
    private List<FoodItem> foodItems;
}
