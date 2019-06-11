package com.qaury.soa.parking.backend.web.events.impl.schedule;

import com.qaury.soa.parking.backend.web.events.api.schedule.IScheduleHandlerLocal;
import com.qaury.soa.parking.backend.web.events.api.schedule.IScheduleManagerLocal;
import com.qaury.soa.parking.backend.web.events.api.schedule.IScheduleManagerRemote;
import com.qaury.soa.parking.backend.web.events.api.schedule.IScheduleManager;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Singleton
@Local(IScheduleManagerLocal.class)
@Remote(IScheduleManagerRemote.class)
public class ScheduleManager implements IScheduleManager {

    @EJB(lookup = "java:global/events-impl/ScheduleHandler!com.qaury.soa.parking.backend.web.events.api.schedule.IScheduleHandlerLocal")
    private IScheduleHandlerLocal scheduleHandler;

    @Resource
    private TimerService timerService;

    private Map<TimerHandle, Integer> ticketPurchaseTimersMap;

    private Map<TimerHandle, Integer> ticketExpireTimersMap;

    @PostConstruct
    public void initialize() {
        ticketPurchaseTimersMap = new HashMap<>();
        ticketExpireTimersMap = new HashMap<>();
    }

    @Override
    public void addTicketPurchaseTimer(int placeId, Date timeToBuyTicket) {
        TimerHandle timerHandle = timerService.createSingleActionTimer(timeToBuyTicket, new TimerConfig()).getHandle();
        ticketPurchaseTimersMap.putIfAbsent(timerHandle, placeId);
    }

    @Override
    public void removeTicketPurchaseTimerIfExists(int placeId) {
       cancelTimerAndRemoveFromMap(ticketPurchaseTimersMap, placeId);
    }

    @Override
    public void addTicketExpireTimer(int placeId, Date ticketExpirationTime) {
        cancelTimerAndRemoveFromMap(ticketExpireTimersMap, placeId);
        TimerHandle timerHandle = timerService.createSingleActionTimer(ticketExpirationTime, new TimerConfig()).getHandle();
        ticketExpireTimersMap.put(timerHandle, placeId);
    }

    @Override
    public void removeTicketExpireTimerIfExists(int placeId) {
        cancelTimerAndRemoveFromMap(ticketExpireTimersMap, placeId);
    }

    @Timeout
    public void timeoutCallback(Timer timer) {
        TimerHandle timerHandle = timer.getHandle();
        if (ticketPurchaseTimersMap.containsKey(timerHandle)) {
            Integer placeId = ticketPurchaseTimersMap.get(timerHandle);
            scheduleHandler.handleTicketPurchaseTimeout(placeId);
            ticketPurchaseTimersMap.remove(timerHandle);
        } else if (ticketExpireTimersMap.containsKey(timerHandle)) {
            Integer placeId = ticketExpireTimersMap.get(timerHandle);
            scheduleHandler.handleTicketExpireTimeout(placeId);
            ticketExpireTimersMap.remove(timerHandle);
        }
    }

    private void cancelTimerAndRemoveFromMap(Map<TimerHandle, Integer> map, Integer placeId) {
        if (map.containsValue(placeId)) {
           for (TimerHandle key : map.keySet()) {
               Integer value = map.get(key);
               if (value.equals(placeId)) {
                   key.getTimer().cancel();
                   map.remove(key, value);
                   break;
               }
           }
        }
    }
}
