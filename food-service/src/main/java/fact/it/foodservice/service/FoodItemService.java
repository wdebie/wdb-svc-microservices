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
            foodTruck.setSkuCode("FT-SPAGHETTI-01");
            foodTruck.setName("Spaghetti Delight");
            foodTruck.setRepName("John Pasta");
            foodTruck.setRepPhone("+32 59 79 02 86");

            FoodTruck foodTruck1 = new FoodTruck();
            foodTruck1.setSkuCode("FT-PAELLA-01");
            foodTruck1.setName("Paella Haven");
            foodTruck1.setRepName("Mark Spain");
            foodTruck1.setRepPhone("+32 112 00 00 00");

            foodTruckRepository.save(foodTruck);
            foodTruckRepository.save(foodTruck1);

            FoodItem foodItem = new FoodItem();
            foodItem.setSkuCode("FI-SPAGHETTI-01");
            foodItem.setName("Classic Spaghetti");
            foodItem.setPrice(BigDecimal.valueOf(2.20));
            foodItem.setFoodTruck(foodTruck);

            FoodItem foodItem1  = new FoodItem();
            foodItem1.setSkuCode("FI-PAELLA-01");
            foodItem1.setName("Seafood Paella");
            foodItem1.setPrice(BigDecimal.valueOf(2.50));
            foodItem1.setFoodTruck(foodTruck1);

            FoodItem foodItem2 = new FoodItem();
            foodItem2.setSkuCode("FI-LASAGNA-01");
            foodItem2.setName("Cheesy Lasagna");
            foodItem2.setPrice(BigDecimal.valueOf(3.00));
            foodItem2.setFoodTruck(foodTruck);

            FoodItem foodItem3 = new FoodItem();
            foodItem3.setSkuCode("FI-RAVIOLI-01");
            foodItem3.setName("Spinach Ravioli");
            foodItem3.setPrice(BigDecimal.valueOf(2.80));
            foodItem3.setFoodTruck(foodTruck);

            FoodItem foodItem4 = new FoodItem();
            foodItem4.setSkuCode("FI-TAPAS-01");
            foodItem4.setName("Assorted Tapas");
            foodItem4.setPrice(BigDecimal.valueOf(3.50));
            foodItem4.setFoodTruck(foodTruck1);

            FoodItem foodItem5 = new FoodItem();
            foodItem5.setSkuCode("FI-CHURROS-01");
            foodItem5.setName("Sweet Churros");
            foodItem5.setPrice(BigDecimal.valueOf(1.50));
            foodItem5.setFoodTruck(foodTruck1);

            foodItemRepository.save(foodItem);
            foodItemRepository.save(foodItem1);
            foodItemRepository.save(foodItem2);
            foodItemRepository.save(foodItem3);
            foodItemRepository.save(foodItem4);
            foodItemRepository.save(foodItem5);
        }
    }

    public void createFoodItem(FoodItemRequest foodItemRequest){
        FoodItem foodItem = new FoodItem();
        foodItem.setSkuCode(foodItemRequest.getSkuCode());
        foodItem.setPrice(foodItemRequest.getPrice());
        foodItem.setName(foodItemRequest.getName());


        if(foodItemRequest.getFoodTruck() != null){
            Long foodTruckId = foodItemRequest.getFoodTruck().getFoodTruckId();
            if(foodTruckId != null){
                FoodTruck foodTruck = foodTruckRepository.findFoodTruckByFoodTruckId(foodTruckId);
                if(foodTruck == null){
                    throw new IllegalArgumentException("No FoodTruck found with ID: " + foodTruckId);
                }
                foodItem.setFoodTruck(foodTruck);
            }

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
