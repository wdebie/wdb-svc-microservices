package fact.it.stageservice.controller;

import fact.it.stageservice.dto.StageRequest;
import fact.it.stageservice.dto.StageResponse;
import fact.it.stageservice.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/stage")
@RequiredArgsConstructor
public class StageController {
    private final StageService stageService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createStage(@RequestBody StageRequest stageRequest){
        stageService.createStage(stageRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StageResponse> getAllStagesBySkuCode(@RequestParam List<String> skuCode){
        return stageService.getAllStageBySkuCode(skuCode);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<StageResponse> getAllStages(){return stageService.getAllStages();}
}
