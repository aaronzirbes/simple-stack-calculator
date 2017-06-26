package org.zirbes.tower

import groovy.transform.ToString
import java.time.LocalTime

interface Event {

    static enum EventType implements Comparable {
        RUNWAY_AVAIL(3),
        RUNWAY_ASSIGN(4),
        ARRIVAL(1),
        DEPARTURE(2)

        final priority

        EventType(int priority) {
            this.priority = priority
        }

    }

    /**
     * Returns the actual time of this flight event.
     * Note: the for <code>RUNWAY_AVAL</code> and <code>RUNWAY_ASSIGN</code>event the
     * scheduled time is the actual time
     * @return the actualTime
     */
    LocalTime getActualTime()

    /**
     * Returns the event type for this flight event
     * @return the event type for this flight event
     */
    EventType getEvent()

    /**
     * Returns the identifier for this flight event
     * @return the ident the flight or runway identifier for this event
     */
    String getIdent()

    /**
     * Returns the schedule time for this event
     * @return the scheduledTime the scheduled time for this event
     */
    LocalTime getScheduledTime()

}

