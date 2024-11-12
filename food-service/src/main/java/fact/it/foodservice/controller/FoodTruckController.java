package fact.it.foodservice.controller;

import fact.it.foodservice.dto.FoodTruckRequest;
import fact.it.foodservice.dto.FoodTruckResponse;
import fact.it.foodservice.service.FoodTruckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/foodtruck")
@RequiredArgsConstructor
public class FoodTruckController {
    private final FoodTruckService foodTruckService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createFoodtruck(@RequestBody FoodTruckRequest foodTruckRequest){
        foodTruckService.createFoodTruck(foodTruckRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FoodTruckResponse> getAllFoodTrucksBySkuCode(@RequestParam List<String> skuCode){
        return foodTruckService.getAllFoodTrucksBySkuCode(skuCode);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<FoodTruckResponse> getAllFoodTrucks(){return foodTruckService.getAllFoodTrucks();}

}
