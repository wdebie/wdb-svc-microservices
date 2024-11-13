package fact.it.stageservice.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Table(name = "stage")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stageId;
    private String skuCode;
    private String name;
    private Integer capacity;
}
