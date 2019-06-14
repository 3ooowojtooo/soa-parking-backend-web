package com.quary.soa.parking.backend.data.impl.external;

import com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.IZoneRemoteDAO;
import com.quary.soa.parking.backend.data.api.external.IExternalDataService;
import com.quary.soa.parking.backend.data.api.external.IExternalDataServiceLocal;
import com.quary.soa.parking.backend.data.api.external.IExternalDataServiceRemote;
import com.quary.soa.parking.backend.data.api.external.model.GeneralInfo;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
@Local(IExternalDataServiceLocal.class)
@Remote(IExternalDataServiceRemote.class)
public class ExternalDataService implements IExternalDataService {

    @EJB(lookup = "java:global/database-impl/ParkingPlaceDAO!com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO")
    private IParkingPlaceRemoteDAO parkingPlaceDAO;

    @Override
    @Transactional
    public GeneralInfo createZoneGeneralInfo() {
        List<Object[]> freeInfo = parkingPlaceDAO.getFreePlacesInfo();
        List<Object[]> placesCountInfo = parkingPlaceDAO.getPlacesCount();
        return mergeOccupiedAndFreeInfo(placesCountInfo, freeInfo);
    }

    private GeneralInfo mergeOccupiedAndFreeInfo(List<Object[]> placesCountInfo, List<Object[]> freeInfo) {
        long timestamp = new Date().getTime();
        List<GeneralInfo.GeneralInfoEntry> entries = new ArrayList<>();
        for (int i = 0; i < placesCountInfo.size(); i++) {
            String description = (String)placesCountInfo.get(i)[0];
            long freeAmount = (long)freeInfo.get(i)[1];
            long occupiedAmount = (long)placesCountInfo.get(i)[1] - freeAmount;
            entries.add(new GeneralInfo.GeneralInfoEntry(description, occupiedAmount, freeAmount));
        }
        return new GeneralInfo(timestamp, entries);
    }
}
