package fact.it.foodservice.service;

import fact.it.foodservice.model.FoodTruck;
import fact.it.foodservice.repository.FoodTruckRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@RequiredArgsConstructor
public class FoodTruckService {
    private final FoodTruckRepository foodTruckRepository;

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
}
