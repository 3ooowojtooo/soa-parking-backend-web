package com.qaury.soa.parking.backend.web.database.api.dao;

import com.qaury.soa.parking.backend.web.database.api.entity.Auth;
import com.qaury.soa.parking.backend.web.database.api.entity.Zone;

import java.util.List;

public interface IAuthDAO extends IBaseDAO<Auth> {

    Auth findByLogin(String login);

    List<Zone> getAllowedZonesFroPrincipal(String principalName);
}
