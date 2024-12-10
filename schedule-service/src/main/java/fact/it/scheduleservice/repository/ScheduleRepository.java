package fact.it.scheduleservice.repository;

import fact.it.scheduleservice.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findScheduleBySkuCodeIn(List<String> skuCode);

    Schedule findScheduleBySkuCode(String skuCode);
}
