package roomescape.reservation.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Repository
public class ReservationDao {
    private JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Reservation reservation) {
        String sql = """
                INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setObject(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT r.id as reservation_id,
                       r.name,
                       r.date,
                       t.id as time_id,
                       t.start_at
                FROM reservation as r 
                INNER JOIN reservation_time as t ON r.time_id=t.id
                """;
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            ReservationTime time = new ReservationTime(
                    resultSet.getLong("time_id"),
                    resultSet.getObject("start_at", LocalTime.class)
            );
            return new Reservation(
                    resultSet.getLong("reservation_id"),
                    resultSet.getString("name"),
                    resultSet.getObject("date", LocalDate.class),
                    time
            );
        });
    }

    public void delete(Long id) {
        String sql = """
                DELETE FROM reservation WHERE id = ?
                """;
        jdbcTemplate.update(sql, id);
    }
}
