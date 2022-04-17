package ro.eutm.notificationdb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "notification_user")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @SequenceGenerator(name = "notification_user_seq",
            sequenceName = "notification_user_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "notification_user_seq")
    @Column(name = "user_id", unique = true, nullable = false)
    private long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "country_code", nullable = false)
    private int countryCode;

    @Column(name = "phone_number", nullable = false)
    private int phoneNumber;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Device> devices;


    public User(String email, String address, int countryCode, int phoneNumber, Timestamp createdAt, Set<Device> devices) {
        this.email = email;
        this.address = address;
        this.countryCode = countryCode;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.devices = devices;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && countryCode == user.countryCode && phoneNumber == user.phoneNumber && Objects.equals(email, user.email) && Objects.equals(createdAt, user.createdAt) && Objects.equals(address, user.address) && Objects.equals(devices, user.devices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, countryCode, phoneNumber, createdAt, address, devices);
    }
}
