package ro.eutm.notificationdb.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class NotificationUserDevices {
    NotificationUser notificationUser;
    List<NotificationDevice> notificationDevices;
}
