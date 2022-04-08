package ro.eutm.notificationdb.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.eutm.notificationdb.model.User;
import ro.eutm.notificationdb.repository.UserRepo;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    static final String regexPattern = "^(.+)@(\\S+)$";

    public boolean deleteByEmail(String userEmail) {
        validateEmail(userEmail);

        List<User> users = userRepo.findByEmail(userEmail);
        if (users != null || !users.isEmpty()) {
            boolean deleted = true;
            for (User user : users) {
                userRepo.delete(user);
                deleted = deleted && !userRepo.findById(user.getId()).isPresent();
            }
            return deleted;
        }
        return false;
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
