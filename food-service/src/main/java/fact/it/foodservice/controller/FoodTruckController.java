package fact.it.foodservice.controller;

import fact.it.foodservice.dto.FoodItemRequest;
import fact.it.foodservice.dto.FoodItemResponse;
import fact.it.foodservice.dto.FoodTruckRequest;
import fact.it.foodservice.dto.FoodTruckResponse;
import fact.it.foodservice.service.FoodTruckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/foodtruck")
@RequiredArgsConstructor
public class FoodTruckController {
    private final FoodTruckService foodTruckService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createFoodTruck(@RequestBody FoodTruckRequest foodTruckRequest){
        foodTruckService.createFoodTruck(foodTruckRequest);
    }

//    @PostMapping("/{foodTruckId}/fooditem")
//    @ResponseStatus(HttpStatus.OK)
//    public void addFoodItemsToFoodTruck(@PathVariable Long foodTruckId, @RequestBody FoodItemRequest foodItemRequest){
//        foodTruckService.addFoodItemToFoodTruck(foodTruckId,foodItemRequest);
//    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FoodTruckResponse> getAllFoodTrucksBySkuCode(@RequestParam List<String> skuCode){
        return foodTruckService.getAllFoodTrucksBySkuCode(skuCode);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<FoodTruckResponse> getAllFoodTrucks(){return foodTruckService.getAllFoodTrucks();}

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public FoodTruckResponse update(@RequestBody FoodTruckRequest foodTruckRequest){
        return foodTruckService.update(foodTruckRequest);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        foodTruckService.delete(id);
    }

}
