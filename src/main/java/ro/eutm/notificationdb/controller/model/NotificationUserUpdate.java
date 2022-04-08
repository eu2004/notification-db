package ro.eutm.notificationdb.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class NotificationUserUpdate {
    private long id;
    private String email;
    private int phoneNumber;
    private int countryCode;
    private String address;
}
