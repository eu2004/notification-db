package ro.eutm.notificationdb.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ro.eutm.notificationdb.model.Device;

import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
public class NotificationUserUpdate {
    private long id;
    private String email;
    private int phoneNumber;
    private int countryCode;
    private String address;
    private Set<NotificationDevice> devices;
}
