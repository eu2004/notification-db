package ro.eutm.notificationdb.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.eutm.notificationdb.controller.model.NotificationDevice;
import ro.eutm.notificationdb.controller.model.NotificationUser;
import ro.eutm.notificationdb.controller.model.NotificationUserCreate;
import ro.eutm.notificationdb.controller.model.NotificationUserUpdate;
import ro.eutm.notificationdb.model.Device;
import ro.eutm.notificationdb.model.User;
import ro.eutm.notificationdb.service.UserService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

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
        User newUser = new User(notificationUser.getEmail(),
                notificationUser.getAddress(),
                notificationUser.getCountryCode(),
                notificationUser.getPhoneNumber(),
                Timestamp.from(Instant.now()));
        newUser.setDevices(notificationUser.getDevices().stream()
                .map(notificationDevice -> new Device(notificationDevice.getId(), notificationDevice.getToken(), null))
                .collect(Collectors.toSet()));
        newUser = userService.create(newUser);
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
            updatedUserObject.setDevices(notificationUser.getDevices().stream().map(notificationDevice
                    -> {
                Device device = modelMapper.map(notificationDevice, Device.class);
                device.setUserId(updatedUserObject);
                return device;
            }).collect(Collectors.toSet()));
            userService.save(updatedUserObject);
            return new ResponseEntity<>(convertToDto(updatedUserObject), HttpStatus.OK);
        }

        return new ResponseEntity<>(new NotificationUser(notificationUser.getEmail(), notificationUser.getPhoneNumber(),
                notificationUser.getCountryCode(), notificationUser.getAddress()), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationUser> getUserById(@PathVariable("id") long id) {
        Optional<User> userData = userService.findById(id);

        return userData.map(user -> new ResponseEntity<>(convertToDto(user), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping("/{email}")
    public ResponseEntity<NotificationUser> getUserByEmail(@RequestBody String email) {
        Optional<User> userData = userService.findByEmail(email);

        return userData.map(user -> new ResponseEntity<>(convertToDto(user), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
        notificationUserDto.setDevices(user.getDevices().stream()
                .map(device -> modelMapper.map(device, NotificationDevice.class))
                .collect(Collectors.toSet()));
        return notificationUserDto;
    }

}
