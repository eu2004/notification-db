package ro.eutm.notificationdb.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationDevice {
    private long id;
    private String token;
}
