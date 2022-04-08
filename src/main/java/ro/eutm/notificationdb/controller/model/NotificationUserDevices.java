package ro.eutm.notificationdb.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class NotificationUserDevices {
    NotificationUser notificationUser;
    Set<NotificationDevice> notificationDevices;
}
