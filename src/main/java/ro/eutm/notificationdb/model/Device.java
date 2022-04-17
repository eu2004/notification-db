package ro.eutm.notificationdb.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "notification_user_device")
@NoArgsConstructor
public class Device {
    @Id
    @SequenceGenerator(name = "notification_user_device_seq",
            sequenceName = "notification_user_device_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "notification_user_device_seq")
    @Column(name = "device_id", unique = true, nullable = false)
    private long id;

    @Column(name = "device_token", nullable = false, unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Device(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return id == device.id && Objects.equals(token, device.token) && Objects.equals(user, device.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, user != null ? user.getId() : 0);
    }
}
