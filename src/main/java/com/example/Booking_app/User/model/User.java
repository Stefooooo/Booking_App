package com.example.Booking_app.User.model;

import com.example.Booking_app.Booking.model.Booking;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Booking> bookings = new HashSet<>();

    @Column
    private String email;

    @Column
    private LocalDateTime createdOn;

    @Column
    private LocalDateTime updatedOn;

}
