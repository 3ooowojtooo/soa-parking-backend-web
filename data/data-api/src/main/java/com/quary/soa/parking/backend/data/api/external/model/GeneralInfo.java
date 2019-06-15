package com.quary.soa.parking.backend.data.api.external.model;

import java.io.Serializable;
import java.util.List;

public class GeneralInfo implements Serializable {

    private long timestamp;
    private List<GeneralInfoEntry> zones;

    public GeneralInfo() {
    }

    public GeneralInfo(long timestamp, List<GeneralInfoEntry> zones) {
        this.timestamp = timestamp;
        this.zones = zones;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<GeneralInfoEntry> getZones() {
        return zones;
    }

    public void setZones(List<GeneralInfoEntry> zones) {
        this.zones = zones;
    }

    public static class GeneralInfoEntry implements Serializable {
        private String description;
        private long occupied;
        private long free;

        public GeneralInfoEntry() {
        }

        public GeneralInfoEntry(String description, long occupied, long free) {
            this.description = description;
            this.occupied = occupied;
            this.free = free;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public long getOccupied() {
            return occupied;
        }

        public void setOccupied(long occupied) {
            this.occupied = occupied;
        }

        public long getFree() {
            return free;
        }

        public void setFree(long free) {
            this.free = free;
        }
    }
}
