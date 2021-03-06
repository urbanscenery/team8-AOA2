package com.aoa.springwebservice.service.support;

import com.aoa.springwebservice.domain.Reservation;
import com.aoa.springwebservice.domain.ReservationRepository;
import com.aoa.springwebservice.domain.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component(value = "lastReservation")
public class LastReservationSelector implements ReservationSelector{

    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public List<Reservation> select(Store store) {
        //todo Exception 날리기 + Refactoring
        LocalDate lastDate = reservationRepository
                .findFirstByStoreAndOpenDateBeforeOrderByOpenDateDesc(store, LocalDate.now())
                .get()
                .getOpenDate();

        List<Reservation> actualReservations = reservationRepository.findAllByStoreAndOpenDate(store, lastDate);
        return reservationRepository.findAllByStoreAndOpenDate(store, LocalDate.now());
    }
}