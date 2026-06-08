package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Transactional
    public Reservation create(Reservation reservation) {
        Long id = reservationDao.save(reservation);
        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public List<Reservation> read() {
        return reservationDao.findAll();
    }

    @Transactional
    public void delete(Long id) {
        reservationDao.delete(id);
    }
}
