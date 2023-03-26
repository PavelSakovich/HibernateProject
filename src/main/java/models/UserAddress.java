package models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name = "user_address")
public class UserAddress {
    @Column (name = "id")
    @Id
    private int id;
    @Column (name = "city", nullable = false)
    private String city;
    @Column (name = "street", nullable = false)
    private String street;
    @Column (name = "house", nullable = false)
    private int house;
    @OneToOne
    @JoinColumn(name = "id")
    private User user;
}
