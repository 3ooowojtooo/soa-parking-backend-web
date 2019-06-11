package com.qaury.soa.parking.backend.web.events.impl.schedule;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ScheduleManager {

    @EJB
    private ScheduleHandler scheduleHandler;

    @Resource
    private TimerService timerService;

    private Map<TimerHandle, Integer> ticketPurchaseTimersMap;

    private Map<TimerHandle, Integer> ticketExpireTimersMap;

    @PostConstruct
    public void initialize() {
        ticketPurchaseTimersMap = new HashMap<>();
        ticketExpireTimersMap = new HashMap<>();
    }

    public void addTicketPurchaseTimer(int placeId, Date timeToBuyTicket) {
        TimerHandle timerHandle = timerService.createSingleActionTimer(timeToBuyTicket, new TimerConfig()).getHandle();
        ticketPurchaseTimersMap.putIfAbsent(timerHandle, placeId);
    }

    public void removeTicketPurchaseTimerIfExists(int placeId) {
       cancelTimerAndRemoveFromMap(ticketPurchaseTimersMap, placeId);
    }

    public void addTicketExpireTimer(int placeId, Date ticketExpirationTime) {
        cancelTimerAndRemoveFromMap(ticketExpireTimersMap, placeId);
        TimerHandle timerHandle = timerService.createSingleActionTimer(ticketExpirationTime, new TimerConfig()).getHandle();
        ticketExpireTimersMap.put(timerHandle, placeId);
    }

    public void removeTicketExpireTimerIfExists(int placeId) {
        cancelTimerAndRemoveFromMap(ticketExpireTimersMap, placeId);
    }

    @Timeout
    public void timeoutCallback(Timer timer) {
        TimerHandle timerHandle = timer.getHandle();
        if (ticketPurchaseTimersMap.containsKey(timerHandle)) {
            Integer placeId = ticketPurchaseTimersMap.get(timerHandle);
            ticketPurchaseTimersMap.remove(timerHandle);
            scheduleHandler.handleTicketPurchaseTimeout(placeId);
        } else if (ticketExpireTimersMap.containsKey(timerHandle)) {
            Integer placeId = ticketExpireTimersMap.get(timerHandle);
            ticketExpireTimersMap.remove(timerHandle);
            scheduleHandler.handleTicketExpireTimeout(placeId);
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
