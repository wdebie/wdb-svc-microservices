package fact.it.scheduleservice.repository;

import fact.it.scheduleservice.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findScheduleBySkuCodeIn(List<String> skuCode);
    List<Schedule> findScheduleByScheduleId(List<Long> id);

    Schedule findScheduleByScheduleId(Long id);
}
