package ro.eutm.notificationdb.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.eutm.notificationdb.NotificationDbStarter;
import ro.eutm.notificationdb.controller.model.NotificationDevice;
import ro.eutm.notificationdb.controller.model.NotificationUser;
import ro.eutm.notificationdb.model.Device;
import ro.eutm.notificationdb.model.User;
import ro.eutm.notificationdb.service.UserService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = NotificationDbStarter.class)
public class UserControllerIT {
    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mvc;

    @BeforeAll
    public static void setProfile() {
        if (System.getProperty("spring.profiles.active") == null) {
            System.setProperty("spring.profiles.active", "dev");
        }
    }

    @Test
    public void givenUsers_whenGetUser_thenStatus200()
            throws Exception {

        String userEmail = "test_user@email.com" + System.currentTimeMillis();
        NotificationUser user = createTestUser(userEmail);

        mvc.perform(get("/api/v1/user/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("email", is(userEmail)));
    }

    private NotificationUser createTestUser(String userEmail) {
        String address = "address_" + System.currentTimeMillis();
        int countryCode = (int) (System.currentTimeMillis() % 100000);
        int phoneNumber = (int) System.currentTimeMillis();
        Timestamp createdAt = Timestamp.from(Instant.now());
        User user = new User(userEmail, address, countryCode, phoneNumber, createdAt);
        Device device = new Device(-1, String.valueOf(System.currentTimeMillis()), user);
        user.setDevices(Collections.singletonList(device));

        User newUser = userService.create(user);

        NotificationUser notificationUser = new NotificationUser();
        notificationUser.setId(newUser.getId());
        notificationUser.setAddress(newUser.getAddress());
        notificationUser.setCountryCode(newUser.getCountryCode());
        notificationUser.setCreatedAt(newUser.getCreatedAt());
        notificationUser.setEmail(newUser.getEmail());
        notificationUser.setDevices(newUser.getDevices().stream().map(device1 -> {
            NotificationDevice notificationDevice = new NotificationDevice();
            notificationDevice.setToken(device1.getToken());
            notificationDevice.setId(device1.getId());
            return notificationDevice;
        }).collect(Collectors.toList()));

        return notificationUser;
    }
}
