package com.example.Booking_app.Booking.reposotory;

import com.example.Booking_app.Booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    Optional<Set<Booking>> findByUserId(UUID id);

}
