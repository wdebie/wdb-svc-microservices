package fact.it.foodservice;

import fact.it.foodservice.model.FoodItem;
import fact.it.foodservice.model.FoodTruck;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FoodServiceApplicationTests {

    @Test
    void testFoodTruckCreation() {
        FoodTruck foodTruck = new FoodTruck();
        foodTruck.setName("Test Food Truck");
        foodTruck.setSkuCode("TFT001");
        foodTruck.setRepName("John Doe");
        foodTruck.setRepPhone("1234567890");

        assertNotNull(foodTruck);
        assertEquals("Test Food Truck", foodTruck.getName());
        assertEquals("TFT001", foodTruck.getSkuCode());
        assertEquals("John Doe", foodTruck.getRepName());
        assertEquals("1234567890", foodTruck.getRepPhone());
    }

    @Test
    void testFoodItemCreation() {
        FoodItem foodItem = new FoodItem();
        foodItem.setName("Test Food Item");
        foodItem.setSkuCode("TFI001");
        foodItem.setPrice(BigDecimal.valueOf(9.99));

        assertNotNull(foodItem);
        assertEquals("Test Food Item", foodItem.getName());
        assertEquals("TFI001", foodItem.getSkuCode());
        assertEquals(BigDecimal.valueOf(9.99), foodItem.getPrice());
    }
}
