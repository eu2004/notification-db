package ro.eutm.notificationdb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "notification_user_device")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    @Id
    @SequenceGenerator(name="notification_user_device_seq",
            sequenceName="notification_user_device_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="notification_user_device_seq")
    @Column(name = "device_id", unique = true, nullable = false)
    private long id;

    @Column(name = "device_token", nullable = false, unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
