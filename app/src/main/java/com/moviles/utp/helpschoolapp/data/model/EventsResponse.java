package com.moviles.utp.helpschoolapp.data.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Gustavo Ramos M. on 12/11/2017.
 */

public class EventsResponse {
    private Date date;
    private List<HistoricalResponse> map;

    public EventsResponse(Date date, List<HistoricalResponse> map) {
        this.date = date;
        this.map = map;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<HistoricalResponse> getMap() {
        return map;
    }

    public void setMap(List<HistoricalResponse> map) {
        this.map = map;
    }
}
