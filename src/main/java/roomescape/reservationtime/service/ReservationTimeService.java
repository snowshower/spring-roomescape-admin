package roomescape.reservationtime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservationtime.dao.ReservationTimeDao;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.ReservationTimeRequest;
import roomescape.reservationtime.dto.ReservationTimeResponse;

import java.util.List;

@Service
public class ReservationTimeService {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @Transactional
    public ReservationTimeResponse create(ReservationTimeRequest request) {
        ReservationTime time = new ReservationTime(request.startAt());
        Long id = reservationTimeDao.save(time);
        ReservationTime createdTime = new ReservationTime(id, request.startAt());
        return ReservationTimeResponse.from(createdTime);
    }

    public List<ReservationTime> read() {
        return reservationTimeDao.findAll();
    }

    public void delete(Long id) {
        reservationTimeDao.delete(id);
    }
}
