package roomescape.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
public class ReservationDaoTest {

    private ReservationDao reservationDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationDao = new ReservationDao(jdbcTemplate);

        jdbcTemplate.execute("DROP TABLE reservations IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE reservations(" +
                "id BIGINT AUTO_INCREMENT, name VARCHAR(255), `date` DATE, `time` TIME)");

    }

    @Test
    void save_test() {
        // given
        Reservation reservation = new Reservation("예약1", LocalDate.of(2026, 6, 8), LocalTime.of(15, 0));

        // when
        Long id = reservationDao.save(reservation);

        // then
        assertThat(id).isNotNull();
    }

    @Test
    void findAll_test() {
        // given
        Reservation reservation1 = new Reservation("예약1", LocalDate.of(2026, 6, 8), LocalTime.of(15, 0));
        Reservation reservation2 = new Reservation("예약2", LocalDate.of(2026, 6, 9), LocalTime.of(15, 0));
        reservationDao.save(reservation1);
        reservationDao.save(reservation2);

        // when
        List<Reservation> reservations = reservationDao.findAll();

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
        Long id = reservationDao.save(reservation);

        // when
        reservationDao.delete(id);
        List<Reservation> reservations = reservationDao.findAll();

        // then
        assertThat(reservations).isEmpty();
    }
}
