package fact.it.foodservice.controller;

import fact.it.foodservice.dto.FoodItemRequest;
import fact.it.foodservice.dto.FoodItemResponse;
import fact.it.foodservice.service.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "api/fooditems")
@RequiredArgsConstructor
public class FoodItemController {
    private final FoodItemService foodItemService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createFoodItem(@RequestBody FoodItemRequest foodItemRequest, @RequestParam(required = false) Long foodTruckId){
        foodItemService.createFoodItem(foodItemRequest,foodTruckId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FoodItemResponse> getAllFoodItemsBySkuCode(@RequestParam List<String> skuCode){
        return foodItemService.getAllFoodItemsBySkuCode(skuCode);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<FoodItemResponse> getAllFoodItems(){return foodItemService.getAllFoodItems();}
}
