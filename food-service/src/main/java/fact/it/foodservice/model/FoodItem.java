package fact.it.foodservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "foodItem")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodItemId;
    private String skuCode;
    private String name;
    private BigDecimal price;

    @ManyToOne
    private FoodTruck foodTruck;
}
