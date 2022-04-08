package ro.eutm.notificationdb.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.eutm.notificationdb.controller.model.NotificationUser;
import ro.eutm.notificationdb.controller.model.NotificationUserCreate;
import ro.eutm.notificationdb.controller.model.NotificationUserUpdate;
import ro.eutm.notificationdb.model.User;
import ro.eutm.notificationdb.service.UserService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/")
    public ResponseEntity<NotificationUser> create(@RequestBody NotificationUserCreate notificationUser) {
        User newUser = userService.save(new User(notificationUser.getEmail(),
                notificationUser.getCountryCode(),
                notificationUser.getPhoneNumber(),
                Timestamp.from(Instant.now())));
        return new ResponseEntity<>(convertToDto(newUser), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<NotificationUser> update(@RequestBody NotificationUserUpdate notificationUser) {
        Optional<User> updatedUser = userService.findById(notificationUser.getId());
        if (updatedUser.isPresent()) {
            User updatedUserObject = updatedUser.get();
            updatedUserObject.setEmail(notificationUser.getEmail());
            updatedUserObject.setPhoneNumber(notificationUser.getPhoneNumber());
            updatedUserObject.setCountryCode(notificationUser.getCountryCode());
            updatedUserObject.setAddress(notificationUser.getAddress());
            userService.save(updatedUserObject);
            return new ResponseEntity<>(convertToDto(updatedUserObject), HttpStatus.OK);
        }

        return new ResponseEntity<>(new NotificationUser(notificationUser.getEmail(), notificationUser.getPhoneNumber(),
                notificationUser.getCountryCode(), notificationUser.getAddress()), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationUser> getUserById(@PathVariable("id") long id) {
        Optional<User> userData = userService.findById(id);

        if (userData.isPresent()) {
            return new ResponseEntity<>(convertToDto(userData.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{email}")
    public ResponseEntity<NotificationUser> getUserByEmail(@RequestBody String email) {
        Optional<User> userData = userService.findByEmail(email);

        if (userData.isPresent()) {
            return new ResponseEntity<>(convertToDto(userData.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<HttpStatus> deleteByEmail(@PathVariable("email") String email) {
        try {
            userService.deleteByEmail(email);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private NotificationUser convertToDto(User user) {
        NotificationUser notificationUserDto = modelMapper.map(user, NotificationUser.class);
        return notificationUserDto;
    }

    private User convertToEntity(NotificationUser notificationUserDto) {
        User user = modelMapper.map(notificationUserDto, User.class);
        return user;
    }
}
