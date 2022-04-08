package ro.eutm.notificationdb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ro.eutm.notificationdb.model.Device;

import java.util.List;

public interface DeviceRepo extends JpaRepository<Device, Long> {
    List<Device> findByUserId(long id);
}
