package com.qaury.soa.parking.backend.web.mobilenotificationswebapp.service;

import com.qaury.soa.parking.backend.web.mobilenotifications.api.messages.TicketExpiredMessage;
import com.qaury.soa.parking.backend.web.mobilenotifications.api.messages.TicketNotPurchasedMessage;
import com.qaury.soa.parking.backend.web.mobilenotificationswebapp.retrofit.NotificationsValidationRestService;
import com.qaury.soa.parking.backend.web.mobilenotificationswebapp.retrofit.StateResponse;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import java.io.IOException;

@Singleton
public class NotificationsHandler {

    private NotificationsValidationRestService restService;

    @Inject
    @Push
    PushContext notificationsChannel;

    @PostConstruct
    public void init() {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://localhost:8080/mobilenotificationsrest/").
                addConverterFactory(JacksonConverterFactory.create()).
                build();
        restService = retrofit.create(NotificationsValidationRestService.class);
    }

    public void handleTicketExpiredMessage(TicketExpiredMessage message) {
        try {
            System.out.println("handleTicketExpiredMessage");
            Response<StateResponse> response = restService.isInTicketExpiredState(message.getPlaceId()).execute();
            if (response.isSuccessful() && response.body() != null && response.body().isState()) {
                notificationsChannel.send("Ticket expiration: placeId=" + message.getPlaceId());
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    public void handleTicketNotPurchasedMessage(TicketNotPurchasedMessage message) {
        try {
            System.out.println("handleTicketNotPurchasedMessage");
            Response<StateResponse> response = restService.isInTicketNotPurchasedState(message.getPlaceId()).execute();
            if (response.isSuccessful() && response.body() != null && response.body().isState()) {
                notificationsChannel.send("Ticket not purchased: placeId=" + message.getPlaceId());
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
