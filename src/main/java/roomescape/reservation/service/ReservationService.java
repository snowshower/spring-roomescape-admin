package roomescape.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservationtime.dao.ReservationTimeDao;
import roomescape.reservationtime.domain.ReservationTime;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationTimeDao reservationTimeDao, ReservationDao reservationDao) {
        this.reservationTimeDao = reservationTimeDao;
        this.reservationDao = reservationDao;
    }


    @Transactional
    public ReservationResponse create(ReservationRequest request) {
        ReservationTime time = reservationTimeDao.findById(request.timeId());
        Reservation reservation = new Reservation(request.name(), request.date(), time);
        Long id = reservationDao.save(reservation);
        Reservation createdReservation = new Reservation(id, request.name(), request.date(), time);
        return ReservationResponse.from(createdReservation);
    }

    public List<Reservation> read() {
        return reservationDao.findAll();
    }

    @Transactional
    public void delete(Long id) {
        reservationDao.delete(id);
    }
}
