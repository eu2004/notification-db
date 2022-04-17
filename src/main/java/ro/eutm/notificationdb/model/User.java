package ro.eutm.notificationdb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> devices = new ArrayList<>();


    public User(String email, String address, int countryCode, int phoneNumber, Timestamp createdAt) {
        this.email = email;
        this.address = address;
        this.countryCode = countryCode;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
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

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
