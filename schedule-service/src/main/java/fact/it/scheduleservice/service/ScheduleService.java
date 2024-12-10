package fact.it.scheduleservice.service;

import fact.it.scheduleservice.dto.ScheduleRequest;
import fact.it.scheduleservice.dto.ScheduleResponse;
import fact.it.scheduleservice.model.Schedule;
import fact.it.scheduleservice.repository.ScheduleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @PostConstruct
    public void loadData() {
        if (scheduleRepository.count() <= 0) {
            Schedule schedule1 = Schedule.builder()
                    .skuCode("event123")
                    .startTime(LocalDateTime.of(2023, 10, 1, 10, 0))
                    .endTime(LocalDateTime.of(2023, 10, 1, 12, 0))
                    .ArtistId("1")
                    .StageId(2L)
                    .FoodTruckId(1L)
                    .build();

            Schedule schedule2 = Schedule.builder()
                    .skuCode("event456")
                    .startTime(LocalDateTime.of(2023, 10, 2, 14, 0))
                    .endTime(LocalDateTime.of(2023, 10, 2, 16, 0))
                    .ArtistId("2")
                    .StageId(1L)
                    .FoodTruckId(2L)
                    .build();

            scheduleRepository.save(schedule1);
            scheduleRepository.save(schedule2);
        }
    }

    public void createSchedule(ScheduleRequest scheduleRequest){
        Schedule schedule = Schedule.builder()
                .skuCode(scheduleRequest.getSkuCode())
                .ArtistId(scheduleRequest.getArtistId())
                .FoodTruckId(scheduleRequest.getFoodTruckId())
                .StageId(scheduleRequest.getStageId())
                .endTime(scheduleRequest.getEndTime())
                .startTime(scheduleRequest.getStartTime())
                .build();
        scheduleRepository.save(schedule);
    }

    public List<ScheduleResponse> getAllSchedules(){
        List<Schedule> schedules = this.scheduleRepository.findAll();
        return schedules.stream().map(this::mapToScheduleResponse).toList();
    }

    public List<ScheduleResponse> getAllSchedulesBySkuCode(List<String> skuCode){
        List<Schedule> schedules = scheduleRepository.findScheduleBySkuCodeIn(skuCode);

        return schedules.stream().map(this::mapToScheduleResponse).toList();
    }

    private ScheduleResponse mapToScheduleResponse(Schedule schedule){
        return ScheduleResponse.builder()
                .scheduleId(schedule.getScheduleId())
//                .FoodTruckId(schedule.getFoodTruckId())
//                .StageId(schedule.getStageId())
//                .ArtistId(schedule.getArtistId())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .skuCode(schedule.getSkuCode())
                .build();
    }
}
