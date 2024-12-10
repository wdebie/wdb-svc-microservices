package fact.it.scheduleservice.controller;

import fact.it.scheduleservice.dto.ScheduleRequest;
import fact.it.scheduleservice.dto.ScheduleResponse;
import fact.it.scheduleservice.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/stage")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ScheduleResponse> getAllSchedulesBySkuCode(@RequestParam List<String> skuCode){
        return scheduleService.getAllSchedulesBySkuCode(skuCode);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ScheduleResponse> getAllSchedules(){return scheduleService.getAllSchedules();}

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createSchedule(@RequestBody ScheduleRequest scheduleRequest){
        scheduleService.createSchedule(scheduleRequest);
    }
}
