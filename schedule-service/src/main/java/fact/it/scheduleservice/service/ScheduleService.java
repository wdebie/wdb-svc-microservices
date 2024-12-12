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
                    .skuCode("SC-EVENT-01")
                    .startTime(LocalDateTime.of(2025, 7, 19, 20, 0))
                    .endTime(LocalDateTime.of(2025, 7, 19, 22, 0))
                    .ArtistSkuCode("AR-MEGA-MINDY-01")
                    .StageId(1L)
                    .FoodTruckId(1L)
                    .build();

            Schedule schedule2 = Schedule.builder()
                    .skuCode("SC-EVENT-02")
                    .startTime(LocalDateTime.of(2025, 7, 19, 19, 0))
                    .endTime(LocalDateTime.of(2025, 7, 19, 21, 0))
                    .ArtistSkuCode("AR-LAURA-TESORO-01")
                    .StageId(2L)
                    .FoodTruckId(2L)
                    .build();

            Schedule schedule3 = Schedule.builder()
                    .skuCode("SC-EVENT-03")
                    .startTime(LocalDateTime.of(2025, 7, 19, 22, 30))
                    .endTime(LocalDateTime.of(2025, 7, 20, 0, 30))
                    .ArtistSkuCode("AR-CLOUSEAU-01")
                    .StageId(1L)
                    .FoodTruckId(1L)
                    .build();

            Schedule schedule4 = Schedule.builder()
                    .skuCode("SC-EVENT-04")
                    .startTime(LocalDateTime.of(2025, 7, 20, 19, 0))
                    .endTime(LocalDateTime.of(2025, 7, 20, 21, 0))
                    .ArtistSkuCode("AR-NIELS-DESTADSBADER-01")
                    .StageId(2L)
                    .FoodTruckId(2L)
                    .build();

            Schedule schedule5 = Schedule.builder()
                    .skuCode("SC-EVENT-05")
                    .startTime(LocalDateTime.of(2025, 7, 20, 22, 0))
                    .endTime(LocalDateTime.of(2025, 7, 21, 0, 0))
                    .ArtistSkuCode("AR-NATALIA-01")
                    .StageId(1L)
                    .FoodTruckId(1L)
                    .build();

            scheduleRepository.save(schedule1);
            scheduleRepository.save(schedule2);
            scheduleRepository.save(schedule3);
            scheduleRepository.save(schedule4);
            scheduleRepository.save(schedule5);
        }
    }

    public void createSchedule(ScheduleRequest scheduleRequest){
        Schedule schedule = Schedule.builder()
                .skuCode(scheduleRequest.getSkuCode())
                .ArtistSkuCode(scheduleRequest.getArtistSkuCode())
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

    public ScheduleResponse update(ScheduleRequest scheduleRequest){
        Schedule schedule = scheduleRepository.findScheduleByScheduleId(scheduleRequest.getScheduleId());
        schedule.setSkuCode(scheduleRequest.getSkuCode());
        schedule.setStartTime(scheduleRequest.getStartTime());
        schedule.setEndTime(scheduleRequest.getEndTime());
        schedule.setStageId(scheduleRequest.getStageId());
        schedule.setArtistSkuCode(scheduleRequest.getArtistSkuCode());
        schedule.setFoodTruckId(scheduleRequest.getFoodTruckId());

        scheduleRepository.save(schedule);
        return ScheduleResponse.builder()
                .scheduleId(schedule.getScheduleId())
                .skuCode(schedule.getSkuCode())
                .endTime(schedule.getEndTime())
                .startTime(schedule.getStartTime())
                .ArtistSkuCode(schedule.getArtistSkuCode())
                .StageId(schedule.getStageId())
                .FoodTruckId(schedule.getFoodTruckId())
                .build();
    }

    public void delete(Long id){
        Long scheduleCode = scheduleRepository.findScheduleByScheduleId(id).getScheduleId();
        scheduleRepository.deleteById(scheduleCode);
    }

    private ScheduleResponse mapToScheduleResponse(Schedule schedule){
        return ScheduleResponse.builder()
                .scheduleId(schedule.getScheduleId())
                .FoodTruckId(schedule.getFoodTruckId())
                .StageId(schedule.getStageId())
                .ArtistSkuCode(schedule.getArtistSkuCode())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .skuCode(schedule.getSkuCode())
                .build();
    }
}
