package org.zirbes.tower

import spock.lang.Specification
import spock.lang.Unroll

class TowerSpec extends Specification {

    Lundenberg airport

    void setup() {
        airport = new Lundenberg(3)
    }

    void 'can read a file'() {
        given:
        String fileName = 'src/main/resources/airportSim_01.txt'

        when:
        airport.processEventFile(fileName)

        then:
        airport.flightsHandled
        airport.flightsHandled.every { it.scheduledTime }
        airport.flightsHandled.every { it.actualTime }

        when:
        List<Event> arrivals = airport.getFlightsHandled(Event.EventType.ARRIVAL)

        then:
        arrivals
        arrivals.every { it.event == Event.EventType.ARRIVAL }
    }

    @Unroll
    void 'must have runways and reservation times'() {
        when:
        new Lundenberg(runways, arrivalDelay, departureDelay)

        then:
        thrown(Throwable)

        where:
        runways | arrivalDelay | departureDelay
        1       | 1            | 0
        0       | 1            | 1
        1       | 0            | 1
    }

    void 'regex is fun'() {
        expect:
        'the quick brown fox' ==~ /the q[a-z]* b[nowr]* (nico|tor|fox)/
    }

}
