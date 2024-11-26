package fact.it.stageservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StageServiceApplicationTests {

    @Mock
    private StageRepository stageRepository;

    @InjectMocks
    private StageService stageService;

    @Test
    void testCreateStage() {
        StageRequest stageRequest = StageRequest.builder()
                .name("Test Stage")
                .skuCode("S1")
                .capacity(100)
                .build();

        stageService.createStage(stageRequest);

        verify(stageRepository, times(1)).save(any(Stage.class));
    }

    @Test
    void testGetAllStages() {
        Stage stage = new Stage();
        stage.setStageId(1L);
        stage.setName("Test Stage");
        stage.setSkuCode("S1");
        stage.setCapacity(100);

        when(stageRepository.findAll()).thenReturn(List.of(stage));

        List<StageResponse> stages = stageService.getAllStages();

        assertEquals(1, stages.size());
        assertEquals("Test Stage", stages.get(0).getName());
        assertEquals("S1", stages.get(0).getSkuCode());
        assertEquals(100, stages.get(0).getCapacity());

        verify(stageRepository, times(1)).findAll();
    }

    @Test
    void testGetAllStagesBySkuCode() {
        Stage stage = new Stage();
        stage.setStageId(1L);
        stage.setName("Test Stage");
        stage.setSkuCode("S1");
        stage.setCapacity(100);

        when(stageRepository.findBySkuCodeIn(Arrays.asList("S1"))).thenReturn(List.of(stage));

        List<StageResponse> stages = stageService.getAllStageBySkuCode(Arrays.asList("S1"));

        assertEquals(1, stages.size());
        assertEquals("Test Stage", stages.get(0).getName());
        assertEquals("S1", stages.get(0).getSkuCode());
        assertEquals(100, stages.get(0).getCapacity());

        verify(stageRepository, times(1)).findBySkuCodeIn(Arrays.asList("S1"));
    }

    // ... additional tests if necessary ...
}
