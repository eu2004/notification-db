package ro.eutm.notificationdb.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.eutm.notificationdb.model.Device;
import ro.eutm.notificationdb.model.User;
import ro.eutm.notificationdb.repository.DeviceRepo;
import ro.eutm.notificationdb.repository.UserRepo;

import java.util.*;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepo userRepo;
    private final DeviceRepo deviceRepo;
    static final String regexPattern = "^(.+)@(\\S+)$";

    public boolean deleteByEmail(String userEmail) {
        validateEmail(userEmail);

        List<User> users = userRepo.findByEmail(userEmail);
        if (users != null && !users.isEmpty()) {
            boolean deleted = true;
            for (User user : users) {
                userRepo.delete(user);
                deleted = deleted && userRepo.findById(user.getId()).isEmpty();
            }
            return deleted;
        }
        return false;
    }

    public User create(User user) {
        Set<Device> devices = new HashSet<>(user.getDevices());
        user.setDevices(Collections.emptySet());
        User newUser = save(user);
        devices.forEach(device -> {
            device.setUser(newUser);
            deviceRepo.save(device);
        });
        newUser.setDevices(devices);
        return newUser;
    }

    public User save(User user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(user.getEmail());

        validateEmail(user.getEmail());

        return userRepo.save(user);
    }

    private void validateEmail(String userEmail) {
        if (!Pattern.compile(regexPattern)
                .matcher(userEmail)
                .matches()) {
            throw new IllegalArgumentException("Invalid email address: " + userEmail);
        }
    }

    public Optional<User> findByEmail(String email) {
        Objects.requireNonNull(email);
        List<User> users = userRepo.findByEmail(email);
        if (users != null) {
            return users.stream().findFirst();
        }
        return Optional.empty();
    }

    public Optional<User> findById(long userId) {
        return userRepo.findById(userId);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }
}
