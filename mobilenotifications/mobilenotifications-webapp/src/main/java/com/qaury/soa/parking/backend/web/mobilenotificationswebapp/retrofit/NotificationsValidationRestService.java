package com.qaury.soa.parking.backend.web.mobilenotificationswebapp.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NotificationsValidationRestService {

    @GET("parkingplaces/{placeId}/isinticketnotpurchasedstate")
    Call<StateResponse> isInTicketNotPurchasedState(@Path("placeId") Integer placeId);

    @GET("parkingplaces/{placeId}/isinticketexpiredstate")
    Call<StateResponse> isInTicketExpiredState(@Path("placeId") Integer placeId);
}
