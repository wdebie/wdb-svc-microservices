package fact.it.foodservice;

import fact.it.foodservice.dto.FoodItemRequest;
import fact.it.foodservice.dto.FoodItemResponse;
import fact.it.foodservice.dto.FoodTruckRequest;
import fact.it.foodservice.dto.FoodTruckResponse;
import fact.it.foodservice.model.FoodItem;
import fact.it.foodservice.model.FoodTruck;
import fact.it.foodservice.repository.FoodItemRepository;
import fact.it.foodservice.repository.FoodTruckRepository;
import fact.it.foodservice.service.FoodItemService;
import fact.it.foodservice.service.FoodTruckService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodServiceApplicationTests {

    @Mock
    private FoodItemRepository foodItemRepository;

    @Mock
    private FoodTruckRepository foodTruckRepository;

    @InjectMocks
    private FoodItemService foodItemService;

    @InjectMocks
    private FoodTruckService foodTruckService;

    @Test
    void testCreateFoodItem() {
        FoodItemRequest foodItemRequest = FoodItemRequest.builder()
                .name("Test Food Item")
                .skuCode("test123")
                .price(BigDecimal.valueOf(9.99))
                .build();

        foodItemService.createFoodItem(foodItemRequest, null);

        verify(foodItemRepository, times(1)).save(any(FoodItem.class));
    }

    @Test
    void testGetAllFoodItems() {
        FoodItem foodItem = new FoodItem();
        foodItem.setFoodItemId(1L);
        foodItem.setName("Test Food Item");
        foodItem.setSkuCode("test123");
        foodItem.setPrice(BigDecimal.valueOf(9.99));

        when(foodItemRepository.findAll()).thenReturn(List.of(foodItem));

        List<FoodItemResponse> foodItems = foodItemService.getAllFoodItems();

        assertEquals(1, foodItems.size());
        assertEquals("Test Food Item", foodItems.get(0).getName());
        assertEquals("test123", foodItems.get(0).getSkuCode());
        assertEquals(BigDecimal.valueOf(9.99), foodItems.get(0).getPrice());

        verify(foodItemRepository, times(1)).findAll();
    }

    @Test
    void testGetAllFoodItemsBySkuCode() {
        FoodItem foodItem = new FoodItem();
        foodItem.setFoodItemId(1L);
        foodItem.setName("Test Food Item");
        foodItem.setSkuCode("test123");
        foodItem.setPrice(BigDecimal.valueOf(9.99));

        when(foodItemRepository.findBySkuCodeIn(Arrays.asList("test123"))).thenReturn(List.of(foodItem));

        List<FoodItemResponse> foodItems = foodItemService.getAllFoodItemsBySkuCode(Arrays.asList("test123"));

        assertEquals(1, foodItems.size());
        assertEquals("Test Food Item", foodItems.get(0).getName());
        assertEquals("test123", foodItems.get(0).getSkuCode());
        assertEquals(BigDecimal.valueOf(9.99), foodItems.get(0).getPrice());

        verify(foodItemRepository, times(1)).findBySkuCodeIn(Arrays.asList("test123"));
    }

    @Test
    void testCreateFoodTruck() {
        FoodTruckRequest foodTruckRequest = FoodTruckRequest.builder()
                .name("Test Food Truck")
                .skuCode("test123")
                .repName("John Doe")
                .repPhone("1234567890")
                .build();

        foodTruckService.createFoodTruck(foodTruckRequest);

        verify(foodTruckRepository, times(1)).save(any(FoodTruck.class));
    }

    @Test
    void testGetAllFoodTrucks() {
        FoodTruck foodTruck = new FoodTruck();
        foodTruck.setFoodTruckId(1L);
        foodTruck.setName("Test Food Truck");
        foodTruck.setSkuCode("test123");
        foodTruck.setRepName("John Doe");
        foodTruck.setRepPhone("1234567890");

        when(foodTruckRepository.findAll()).thenReturn(List.of(foodTruck));

        List<FoodTruckResponse> foodTrucks = foodTruckService.getAllFoodTrucks();

        assertEquals(1, foodTrucks.size());
        assertEquals("Test Food Truck", foodTrucks.get(0).getName());
        assertEquals("test123", foodTrucks.get(0).getSkuCode());
        assertEquals("John Doe", foodTrucks.get(0).getRepName());
        assertEquals("1234567890", foodTrucks.get(0).getRepPhone());

        verify(foodTruckRepository, times(1)).findAll();
    }

    @Test
    void testGetAllFoodTrucksBySkuCode() {
        FoodTruck foodTruck = new FoodTruck();
        foodTruck.setFoodTruckId(1L);
        foodTruck.setName("Test Food Truck");
        foodTruck.setSkuCode("test123");
        foodTruck.setRepName("John Doe");
        foodTruck.setRepPhone("1234567890");

        when(foodTruckRepository.findBySkuCodeIn(Arrays.asList("test123"))).thenReturn(List.of(foodTruck));

        List<FoodTruckResponse> foodTrucks = foodTruckService.getAllFoodTrucksBySkuCode(Arrays.asList("test123"));

        assertEquals(1, foodTrucks.size());
        assertEquals("Test Food Truck", foodTrucks.get(0).getName());
        assertEquals("test123", foodTrucks.get(0).getSkuCode());
        assertEquals("John Doe", foodTrucks.get(0).getRepName());
        assertEquals("1234567890", foodTrucks.get(0).getRepPhone());

        verify(foodTruckRepository, times(1)).findBySkuCodeIn(Arrays.asList("test123"));
    }
}