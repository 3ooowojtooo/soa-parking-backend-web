package com.qaury.soa.parking.backend.web.database.api.dao;

import com.qaury.soa.parking.backend.web.database.api.entity.Controller;
import com.qaury.soa.parking.backend.web.database.api.entity.ParkingPlace;

public interface IParkingPlaceDAO extends IBaseDAO<ParkingPlace> {

    Controller findControllerForParkingPlace(int placeId);

    boolean isPlaceTicketNotPurchasedNotificationValid(int placeId);

    boolean isPlaceTicketExpiredNotificationValid(int placeId);

    void setPlaceTicketNotPurchased(int placeId, boolean state);

    void setPlaceTicketExpired(int placeId, boolean state);
}
