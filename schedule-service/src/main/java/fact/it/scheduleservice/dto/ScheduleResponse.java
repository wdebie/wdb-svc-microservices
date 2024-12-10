package fact.it.scheduleservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleResponse {
    private Long scheduleId;
    private String skuCode;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String ArtistId;
    private Long StageId;
    private Long FoodTruckId;
}
