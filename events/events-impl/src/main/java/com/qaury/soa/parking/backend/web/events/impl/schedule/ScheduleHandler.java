package com.qaury.soa.parking.backend.web.events.impl.schedule;

import com.qaury.soa.parking.backend.web.events.api.schedule.IScheduleHandler;
import com.qaury.soa.parking.backend.web.events.api.schedule.IScheduleHandlerLocal;
import com.qaury.soa.parking.backend.web.events.api.schedule.IScheduleHandlerRemote;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;

@Singleton
@Local(IScheduleHandlerLocal.class)
@Remote(IScheduleHandlerRemote.class)
public class ScheduleHandler implements IScheduleHandler {

    @Override
    public void handleTicketPurchaseTimeout(Integer placeId) {

    }

    @Override
    public void handleTicketExpireTimeout(Integer placeId) {

    }
}
