package fact.it.scheduleservice;

import fact.it.scheduleservice.dto.ScheduleRequest;
import fact.it.scheduleservice.dto.ScheduleResponse;
import fact.it.scheduleservice.model.Schedule;
import fact.it.scheduleservice.repository.ScheduleRepository;
import fact.it.scheduleservice.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTests {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    @Test
    void testCreateSchedule() {
        ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                .skuCode("event123")
                .startTime(LocalDateTime.of(2023, 10, 1, 10, 0))
                .endTime(LocalDateTime.of(2023, 10, 1, 12, 0))
                .ArtistId("1")
                .StageId(2L)
                .FoodTruckId(1L)
                .build();

        scheduleService.createSchedule(scheduleRequest);

        verify(scheduleRepository, times(1)).save(any(Schedule.class));
    }

    @Test
    void testGetAllSchedules() {
        Schedule schedule = new Schedule();
        schedule.setScheduleId(1L);
        schedule.setSkuCode("event123");
        schedule.setStartTime(LocalDateTime.of(2023, 10, 1, 10, 0));
        schedule.setEndTime(LocalDateTime.of(2023, 10, 1, 12, 0));
        schedule.setArtistId("1");
        schedule.setStageId(2L);
        schedule.setFoodTruckId(1L);

        when(scheduleRepository.findAll()).thenReturn(List.of(schedule));

        List<ScheduleResponse> schedules = scheduleService.getAllSchedules();

        assertEquals(1, schedules.size());
        assertEquals("event123", schedules.get(0).getSkuCode());

        verify(scheduleRepository, times(1)).findAll();
    }

    @Test
    void testGetAllSchedulesBySkuCode() {
        Schedule schedule = new Schedule();
        schedule.setScheduleId(1L);
        schedule.setSkuCode("event123");
        schedule.setStartTime(LocalDateTime.of(2023, 10, 1, 10, 0));
        schedule.setEndTime(LocalDateTime.of(2023, 10, 1, 12, 0));
        schedule.setArtistId("1");
        schedule.setStageId(2L);
        schedule.setFoodTruckId(1L);

        when(scheduleRepository.findScheduleBySkuCodeIn(List.of("event123"))).thenReturn(List.of(schedule));

        List<ScheduleResponse> schedules = scheduleService.getAllSchedulesBySkuCode(List.of("event123"));

        assertEquals(1, schedules.size());
        assertEquals("event123", schedules.get(0).getSkuCode());

        verify(scheduleRepository, times(1)).findScheduleBySkuCodeIn(List.of("event123"));
    }

    // ... additional tests if necessary ...
} 