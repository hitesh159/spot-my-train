package com.example.hitesh.spotmytrain;

/**
 * Created by hitesh on 2/8/2018.
 */

public class TrainInfo {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrainInfo(String name, String number, String support, String arrival, String departure, String source, String destination, String runs) {
        this.name = name;
        this.number = number;
        this.support = support;
        this.arrival = arrival;
        this.departure = departure;
        this.source = source;
        this.destination = destination;
        this.runs = runs;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    private String name,number,support,arrival,departure,source,destination,runs;

}
