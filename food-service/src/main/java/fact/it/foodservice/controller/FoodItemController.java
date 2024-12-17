package fact.it.foodservice.controller;

import fact.it.foodservice.dto.FoodItemRequest;
import fact.it.foodservice.dto.FoodItemResponse;
import fact.it.foodservice.service.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/fooditem")
@RequiredArgsConstructor
public class FoodItemController {
    private final FoodItemService foodItemService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createFoodItem(@RequestBody FoodItemRequest foodItemRequest){
        foodItemService.createFoodItem(foodItemRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FoodItemResponse> getAllFoodItemsBySkuCode(@RequestParam List<String> skuCode){
        return foodItemService.getAllFoodItemsBySkuCode(skuCode);
    }

    @GetMapping("/by-food-truck/{foodTruckId}")
    public List<FoodItemResponse> getFoodItemsByFoodTruckId(@PathVariable Long foodTruckId) {
        return foodItemService.getFoodItemsByFoodTruckId(foodTruckId);

    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<FoodItemResponse> getAllFoodItems(){return foodItemService.getAllFoodItems();}

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public FoodItemResponse update(@RequestBody FoodItemRequest foodItemRequest){
       return foodItemService.update(foodItemRequest);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFoodItem(@PathVariable Long id){
        foodItemService.delete(id);
    }
}
