package ro.eutm.notificationdb.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class NotificationUserCreate {
    private String email;
    private int phoneNumber;
    private int countryCode;
    private String address;
}
