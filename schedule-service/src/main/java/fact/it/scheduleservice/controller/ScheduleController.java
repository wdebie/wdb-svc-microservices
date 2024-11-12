package fact.it.scheduleservice.controller;

import fact.it.scheduleservice.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/schedule")
@RequiredArgsConstructor
public class StageController {
    private final ScheduleService scheduleService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String placeSchedule(@RequestBody SCheduleRequest )
}
