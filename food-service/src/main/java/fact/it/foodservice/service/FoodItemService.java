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
            FoodTruck foodTruck = new FoodTruck();
            foodTruck.setSkuCode("ft122");
            foodTruck.setName("Spaghetti Parade");
            foodTruck.setRepName("Mister Spaghetti");
            foodTruck.setRepPhone("+32 59 79 02 86");

            FoodTruck foodTruck1 = new FoodTruck();
            foodTruck1.setSkuCode("parking12");
            foodTruck1.setName("De Parking Paellas");
            foodTruck1.setRepName("Mark Spanje");
            foodTruck1.setRepPhone("112");

            foodTruckRepository.save(foodTruck);
            foodTruckRepository.save(foodTruck1);
            FoodItem foodItem = new FoodItem();
            foodItem.setSkuCode("spag1");
            foodItem.setName("Spaghetti");
            foodItem.setPrice(BigDecimal.valueOf(2.20));
            foodItem.setFoodTruck(foodTruck);


            FoodItem foodItem1  = new FoodItem();
            foodItem1.setSkuCode("es");
            foodItem1.setName("Paella");
            foodItem1.setPrice(BigDecimal.valueOf(2.50));
            foodItem1.setFoodTruck(foodTruck1);

            foodItemRepository.save(foodItem);
            foodItemRepository.save(foodItem1);
        }
    }

    public void createFoodItem(FoodItemRequest foodItemRequest, Long foodTruckId){
        FoodItem foodItem = new FoodItem();
        foodItem.setSkuCode(foodItemRequest.getSkuCode());
        foodItem.setPrice(foodItemRequest.getPrice());
        foodItem.setName(foodItemRequest.getName());

//        if(foodTruckId != null){
//            FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId)
//                    .orElseThrow(()-> new IllegalArgumentException("FoodTruck not found"));
//            foodItem.setFoodTruck(foodTruck);
//            foodTruck.getFoodItems().add(foodItem);
//        }

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

    public List<FoodItemResponse> getFoodItemsByFoodTruckId(Long foodTruckId){
        List<FoodItem> foodItems = foodItemRepository.findFoodItemByFoodTruck_FoodTruckId(foodTruckId);
        return foodItems.stream().map(this::mapToFoodItemResponse).toList();
    }

    public FoodItemResponse update(FoodItemRequest foodItemRequest){
        FoodItem foodItem = foodItemRepository.findFoodItemByFoodItemId(foodItemRequest.getFoodItemId());
        foodItem.setName(foodItemRequest.getName());
        foodItem.setPrice(foodItemRequest.getPrice());
        foodItem.setSkuCode(foodItemRequest.getSkuCode());

        foodItemRepository.save(foodItem);
        return FoodItemResponse.builder()
                .foodItemId(foodItem.getFoodItemId())
                .skuCode(foodItem.getSkuCode())
                .name(foodItem.getName())
                .price(foodItem.getPrice())
                .build();

    }

    public void delete(Long foodItemId){
        Long foodItemCode = foodItemRepository.findFoodItemByFoodItemId(foodItemId).getFoodItemId();
        foodItemRepository.deleteById(foodItemCode);
    }

    private FoodItemResponse mapToFoodItemResponse(FoodItem foodItem){
        return FoodItemResponse.builder()
                .foodItemId(foodItem.getFoodItemId())
                .name(foodItem.getName())
                .price(foodItem.getPrice())
                .skuCode(foodItem.getSkuCode())
                .foodTruck(foodItem.getFoodTruck())
                .build();
    }

}
