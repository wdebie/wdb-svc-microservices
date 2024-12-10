package fact.it.scheduleservice.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Table(name = "schedule")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    private String skuCode;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
//    private String ArtistId;
//    private Long StageId;
//    private Long FoodTruckId;

}
