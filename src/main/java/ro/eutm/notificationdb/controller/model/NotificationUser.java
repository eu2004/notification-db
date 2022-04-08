package ro.eutm.notificationdb.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
public class NotificationUser {
    private long id;
    private String email;
    private int phoneNumber;
    private int countryCode;
    private Timestamp createdAt;
    private String address;
    private Set<NotificationDevice> devices;

    public NotificationUser(String email, int phoneNumber, int countryCode, String address) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.countryCode = countryCode;
        this.address = address;
    }
}
