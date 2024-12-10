package fact.it.scheduleservice.model;

import fact.it.artistservice.model.Artist;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
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
    private String ArtistId;
    private Long StageId;
    private Long FoodTruckId;

}
