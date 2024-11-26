package fact.it.foodservice.service;

import fact.it.foodservice.dto.FoodItemRequest;
import fact.it.foodservice.dto.FoodItemResponse;
import fact.it.foodservice.dto.FoodTruckRequest;
import fact.it.foodservice.dto.FoodTruckResponse;
import fact.it.foodservice.model.FoodItem;
import fact.it.foodservice.model.FoodTruck;
import fact.it.foodservice.repository.FoodItemRepository;
import fact.it.foodservice.repository.FoodTruckRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodTruckService {
    private final FoodTruckRepository foodTruckRepository;
    private final FoodItemRepository foodItemRepository;

    @PostConstruct
    public void loadData(){
        if(foodTruckRepository.count() <= 0){
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
        }
    }

    public void createFoodTruck(FoodTruckRequest foodTruckRequest){
        FoodTruck foodTruck = new FoodTruck();
        foodTruck.setName(foodTruckRequest.getName());
        foodTruck.setSkuCode(foodTruckRequest.getSkuCode());
        foodTruck.setRepName(foodTruckRequest.getRepName());
        foodTruck.setRepPhone(foodTruckRequest.getRepPhone());

        foodTruckRepository.save(foodTruck);
    }

    public List<FoodTruckResponse> getAllFoodTrucks(){
        List<FoodTruck> foodTrucks = foodTruckRepository.findAll();

        return foodTrucks.stream().map(this::mapToFoodTruckResponse).toList();
    }

    public List<FoodTruckResponse> getAllFoodTrucksBySkuCode(List<String> skuCode){
        List<FoodTruck> foodTrucks = foodTruckRepository.findBySkuCodeIn(skuCode);

        return foodTrucks.stream().map(this::mapToFoodTruckResponse).toList();
    }

    private FoodTruckResponse mapToFoodTruckResponse(FoodTruck foodTruck){
        return FoodTruckResponse.builder()
                .foodTruckId(foodTruck.getFoodTruckId())
                .name(foodTruck.getName())
                .repName(foodTruck.getRepName())
                .repPhone(foodTruck.getRepPhone())
                .skuCode(foodTruck.getSkuCode())
                .build();
    }

    public void addFoodItemToFoodTruck(Long foodTruckId, FoodItemRequest foodItemRequest){
        FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId)
                .orElseThrow(() -> new IllegalArgumentException("FoodTruck not found"));

        FoodItem foodItem = new FoodItem();
        foodItem.setSkuCode(foodItemRequest.getSkuCode());
        foodItem.setName(foodItemRequest.getName());
        foodItem.setPrice(foodItemRequest.getPrice());
        foodItem.setFoodTruck(foodTruck);

        foodItemRepository.save(foodItem);
        foodTruck.getFoodItems().add(foodItem);

        foodTruckRepository.save(foodTruck);
    }

    public List<FoodItemResponse> getFoodItemsByFoodTruck(Long foodTruckId){
        FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId)
                .orElseThrow(() -> new IllegalArgumentException("FoodTruck not found"));

        return foodTruck.getFoodItems().stream()
                .map(this::mapToFoodItemResponse)
                .toList();
    }

    private FoodItemResponse mapToFoodItemResponse(FoodItem foodItem) {
        return FoodItemResponse.builder()
                .foodItemId(foodItem.getFoodItemId())
                .name(foodItem.getName())
                .price(foodItem.getPrice())
                .build();
    }
}
