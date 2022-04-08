package ro.eutm.notificationdb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "notification_user")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(mappedBy = "userId")
    private Set<Device> items;

    public User(String email, int countryCode, int phoneNumber, Timestamp createdAt) {
        this.email = email;
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
}
