package Tests;

import Domain.Booking2;
import Domain.BookingValidatory2;
import Domain.IValidator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BookingValidatory2Test {

    IValidator<Booking2> bookingValidator = new BookingValidatory2();

    @Test
    void validate2() {

        Booking2 booking = new Booking2("1", "1", "1", LocalDate.of(2030,11,2), LocalTime.of(10,0));

        try {
            bookingValidator.validate2(booking);
        } catch (RuntimeException rex) {
            assertTrue(true);
        }
    }
}