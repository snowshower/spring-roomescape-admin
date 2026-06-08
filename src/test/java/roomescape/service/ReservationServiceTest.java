package roomescape.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Test
    void save_test() {
        // given
        Reservation reservation = new Reservation("예약1", LocalDate.of(2026, 6, 8), LocalTime.of(15, 0));

        // when
        Reservation createdReservation = reservationService.create(reservation);
        Long id = createdReservation.getId();

        // then
        assertThat(id).isNotNull();
    }

    @Test
    void read_test() {
        // given
        Reservation reservation1 = new Reservation("예약1", LocalDate.of(2026, 6, 8), LocalTime.of(15, 0));
        Reservation reservation2 = new Reservation("예약2", LocalDate.of(2026, 6, 9), LocalTime.of(15, 0));
        reservationService.create(reservation1);
        reservationService.create(reservation2);

        // when
        List<Reservation> reservations = reservationService.read();

        // then
        assertAll(
                () -> assertThat(reservations).isNotNull(),
                () -> assertThat(reservations).hasSize(2),
                () -> assertThat(reservations.get(0).getName()).isEqualTo("예약1"),
                () -> assertThat(reservations.get(1).getName()).isEqualTo("예약2")
        );
    }

    @Test
    void delete_test() {
        // given
        Reservation reservation = new Reservation("예약1", LocalDate.of(2026, 6, 8), LocalTime.of(15, 0));
        Reservation createdReservation = reservationService.create(reservation);
        Long id = createdReservation.getId();

        // when
        reservationService.delete(id);
        List<Reservation> reservations = reservationService.read();

        // then
        assertThat(reservations).isEmpty();
    }
}
