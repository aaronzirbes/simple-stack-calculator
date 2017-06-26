package org.zirbes.tower

import groovy.transform.CompileStatic
import java.time.LocalTime

@CompileStatic
class Flight implements Event, Comparable<Flight> {

    Flight(LocalTime time, EventType eventType, String flightNumber) {
        this.scheduledTime = time
        this.event = eventType
        this.ident = flightNumber
    }

    LocalTime actualTime
    EventType event
    String ident
    LocalTime scheduledTime
    Integer runway

    @Override
    int compareTo(Flight other) {
        this.actualTime <=> other.actualTime
    }

}
