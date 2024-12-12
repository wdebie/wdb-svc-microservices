package fact.it.foodservice.service;

import fact.it.foodservice.dto.FoodTruckRequest;
import fact.it.foodservice.dto.FoodTruckResponse;
import fact.it.foodservice.model.FoodTruck;
import fact.it.foodservice.repository.FoodItemRepository;
import fact.it.foodservice.repository.FoodTruckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodTruckService {
    private final FoodTruckRepository foodTruckRepository;
    private final FoodItemRepository foodItemRepository;

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

    public FoodTruckResponse update(FoodTruckRequest foodTruckRequest){
        FoodTruck foodTruck = foodTruckRepository.findFoodTruckByFoodTruckId(foodTruckRequest.getFoodTruckId());
        foodTruck.setName(foodTruckRequest.getName());
        foodTruck.setSkuCode(foodTruckRequest.getSkuCode());
        foodTruck.setRepName(foodTruckRequest.getRepName());
        foodTruck.setRepPhone(foodTruckRequest.getRepPhone());
        foodTruckRepository.save(foodTruck);
        return FoodTruckResponse.builder()
                .foodTruckId(foodTruck.getFoodTruckId())
                .skuCode(foodTruck.getSkuCode())
                .name(foodTruck.getName())
                .repName(foodTruck.getRepName())
                .repPhone(foodTruck.getRepPhone())
                .build();
    }

    public void delete(Long id){
        Long foodTruckCode = foodTruckRepository.findFoodTruckByFoodTruckId(id).getFoodTruckId();
        foodTruckRepository.deleteById(foodTruckCode);
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


}
