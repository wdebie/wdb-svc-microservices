package fact.it.stageservice.service;

import fact.it.stageservice.dto.StageRequest;
import fact.it.stageservice.dto.StageResponse;
import fact.it.stageservice.model.Stage;
import fact.it.stageservice.repository.StageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StageService {
    private final StageRepository stageRepository;

    public void loadData(){
        if (stageRepository.count()<=0){
            Stage stage = new Stage();
            stage.setSkuCode("S1");
            stage.setName("Stage 1");
            stage.setCapacity(100);

            Stage stage1 = new Stage();
            stage1.setSkuCode("S2");
            stage1.setName("Stage 2");
            stage1.setCapacity(200);

            stageRepository.save(stage);
            stageRepository.save(stage1);
        }
    }

    public void createStage(StageRequest stageRequest){
        Stage stage =  new Stage();
        stage.setSkuCode(stageRequest.getSkuCode());
        stage.setName(stageRequest.getName());
        stage.setCapacity(stageRequest.getCapacity());

        stageRepository.save(stage);
    }

    public List<StageResponse> getAllStages(){
        List<Stage> stages = stageRepository.findAll();

        return stages.stream().map(this::mapToStageResponse).toList();
    }

    public List<StageResponse> getAllStageBySkuCode(List<String> skuCode){
        List<Stage> stages = stageRepository.findBySkuCodeIn(skuCode);

        return stages.stream().map(this::mapToStageResponse).toList();
    }

    private StageResponse mapToStageResponse(Stage stage){
        return StageResponse.builder()
                .stageId(stage.getStageId())
                .capacity(stage.getCapacity())
                .name(stage.getName())
                .skuCode(stage.getSkuCode())
                .build();
    }
}
