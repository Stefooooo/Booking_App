package com.example.Booking_app.Booking.service;

import com.example.Booking_app.Booking.model.Booking;
import com.example.Booking_app.Booking.model.BookingStatus;
import com.example.Booking_app.Booking.reposotory.BookingRepository;
import com.example.Booking_app.User.model.User;
import com.example.Booking_app.User.model.UserRole;
import com.example.Booking_app.User.reposotory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, UserRepository userRepository){
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    public Booking createBooking(Booking booking, UUID userId){

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));

        LocalDateTime now = LocalDateTime.now();

        booking.setCreatedOn(now);
        booking.setUpdatedOn(now);

        booking.setStatus(BookingStatus.PENDING);
        booking.setUser(user);

        return bookingRepository.save(booking);
    }

    public Booking approveBooking(Booking booking){

        booking.setUpdatedOn(LocalDateTime.now());
        booking.setStatus(BookingStatus.APPROVED);

        return bookingRepository.save(booking);
    }

    public Booking rejectBooking(Booking booking){

        booking.setUpdatedOn(LocalDateTime.now());
        booking.setStatus(BookingStatus.REJECTED);

        return bookingRepository.save(booking);
    }

    public Set<Booking> getAllBookingsByUser(User user){
        return bookingRepository.findByUserId(user.getId()).orElse(new HashSet<>());
    }
}
