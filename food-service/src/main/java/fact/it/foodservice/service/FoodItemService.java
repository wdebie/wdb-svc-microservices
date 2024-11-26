package fact.it.foodservice.service;

import fact.it.foodservice.dto.FoodItemRequest;
import fact.it.foodservice.dto.FoodItemResponse;
import fact.it.foodservice.model.FoodItem;
import fact.it.foodservice.model.FoodTruck;
import fact.it.foodservice.repository.FoodItemRepository;
import fact.it.foodservice.repository.FoodTruckRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodItemService {
    private final FoodItemRepository foodItemRepository;
    private final FoodTruckRepository foodTruckRepository;

    @PostConstruct
    public void loadData(){
        if(foodItemRepository.count() <= 0){
            FoodItem foodItem = new FoodItem();
            foodItem.setSkuCode("spag1");
            foodItem.setName("Spaghetti");
            foodItem.setPrice(BigDecimal.valueOf(2.20));

            FoodItem foodItem1  = new FoodItem();
            foodItem.setSkuCode("es");
            foodItem.setName("Paella");
            foodItem.setPrice(BigDecimal.valueOf(2.50));

            foodItemRepository.save(foodItem);
            foodItemRepository.save(foodItem1);
        }
    }

    public void createFoodItem(FoodItemRequest foodItemRequest, Long foodTruckId){
        FoodItem foodItem = new FoodItem();
        foodItem.setSkuCode(foodItemRequest.getSkuCode());
        foodItem.setPrice(foodItemRequest.getPrice());
        foodItem.setName(foodItemRequest.getName());

        if(foodTruckId != null){
            FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId)
                    .orElseThrow(()-> new IllegalArgumentException("FoodTruck not found"));
            foodItem.setFoodTruck(foodTruck);
            foodTruck.getFoodItems().add(foodItem);
        }

        foodItemRepository.save(foodItem);
    }

    public List<FoodItemResponse> getAllFoodItems(){
        List<FoodItem> foodItems = foodItemRepository.findAll();

        return foodItems.stream().map(this::mapToFoodItemResponse).toList();
    }

    public List<FoodItemResponse> getAllFoodItemsBySkuCode(List<String> skuCode){
        List<FoodItem> foodItems = foodItemRepository.findBySkuCodeIn(skuCode);

        return foodItems.stream().map(this::mapToFoodItemResponse).toList();
    }

    private FoodItemResponse mapToFoodItemResponse(FoodItem foodItem){
        return FoodItemResponse.builder()
                .foodItemId(foodItem.getFoodItemId())
                .name(foodItem.getName())
                .price(foodItem.getPrice())
                .skuCode(foodItem.getSkuCode())
                .build();
    }
}
