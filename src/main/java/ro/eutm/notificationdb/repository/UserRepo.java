package ro.eutm.notificationdb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ro.eutm.notificationdb.model.User;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
}
