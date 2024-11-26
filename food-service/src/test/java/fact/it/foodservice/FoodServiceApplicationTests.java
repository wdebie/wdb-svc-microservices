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
        FoodItem foodItem = FoodItem.builder()
                .foodItemId(1L)
                .name("Test Food Item")
                .skuCode("test123")
                .price(BigDecimal.valueOf(9.99))
                .build();

        when(foodItemRepository.findAll()).thenReturn(List.of(foodItem));

        List<FoodItemResponse> foodItems = foodItemService.getAllFoodItems();

        assertEquals(1, foodItems.size());
        assertEquals("Test Food Item", foodItems.get(0).getName());
        assertEquals("test123", foodItems.get(0).getSkuCode());

        verify(foodItemRepository, times(1)).findAll();
    }

    @Test
    void testGetAllFoodItemsBySkuCode() {
        FoodItem foodItem = FoodItem.builder()
                .foodItemId(1L)
                .name("Test Food Item")
                .skuCode("test123")
                .price(BigDecimal.valueOf(9.99))
                .build();

        when(foodItemRepository.findBySkuCodeIn(Arrays.asList("test123"))).thenReturn(List.of(foodItem));

        List<FoodItemResponse> foodItems = foodItemService.getAllFoodItemsBySkuCode(Arrays.asList("test123"));

        assertEquals(1, foodItems.size());
        assertEquals("Test Food Item", foodItems.get(0).getName());
        assertEquals("test123", foodItems.get(0).getSkuCode());

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
        FoodTruck foodTruck = FoodTruck.builder()
                .foodTruckId(1L)
                .name("Test Food Truck")
                .skuCode("test123")
                .repName("John Doe")
                .repPhone("1234567890")
                .build();

        when(foodTruckRepository.findAll()).thenReturn(List.of(foodTruck));

        List<FoodTruckResponse> foodTrucks = foodTruckService.getAllFoodTrucks();

        assertEquals(1, foodTrucks.size());
        assertEquals("Test Food Truck", foodTrucks.get(0).getName());
        assertEquals("test123", foodTrucks.get(0).getSkuCode());

        verify(foodTruckRepository, times(1)).findAll();
    }

    @Test
    void testGetAllFoodTrucksBySkuCode() {
        FoodTruck foodTruck = FoodTruck.builder()
                .foodTruckId(1L)
                .name("Test Food Truck")
                .skuCode("test123")
                .repName("John Doe")
                .repPhone("1234567890")
                .build();

        when(foodTruckRepository.findBySkuCodeIn(Arrays.asList("test123"))).thenReturn(List.of(foodTruck));

        List<FoodTruckResponse> foodTrucks = foodTruckService.getAllFoodTrucksBySkuCode(Arrays.asList("test123"));

        assertEquals(1, foodTrucks.size());
        assertEquals("Test Food Truck", foodTrucks.get(0).getName());
        assertEquals("test123", foodTrucks.get(0).getSkuCode());

        verify(foodTruckRepository, times(1)).findBySkuCodeIn(Arrays.asList("test123"));
    }
}