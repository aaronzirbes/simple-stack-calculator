package org.zirbes.tower

import groovy.transform.CompileStatic

import java.io.FileNotFoundException
import java.io.PrintWriter
import java.time.LocalTime
import java.util.Random

@CompileStatic
class Lundenberg extends Airport {

    static final LocalTime MIDNIGHT = LocalTime.parse('11:59')

    protected final List<Flight> flights = []
    protected final List<PriorityQueue> runways
    protected final int numRunways
    protected final int arrivalReserveTime
    protected final int departureReserveTime

    Lundenberg(int numRunways) {
        this(numRunways, 2, 3)
    }

    Lundenberg(int numRunways, int arrivalReserveTime, int departureReserveTime) {
        this.numRunways = numRunways
        this.arrivalReserveTime = arrivalReserveTime
        this.departureReserveTime = departureReserveTime

        runways = (1..numRunways).collect { new PriorityQueue<Flight>() }

        assert numRunways > 1
        assert arrivalReserveTime > 1
        assert departureReserveTime > 1
    }


    /**
     * Reads and processes the specified flight event file.
     * @param filename path to the flight event file.
     */
    @Override
    void processEventFile(String filename) {
        try {
            new File(filename).splitEachLine('\\|') { List<String> columns ->
                LocalTime time = LocalTime.parse(columns[0])
                Event.EventType eventType = Event.EventType.valueOf(columns[1])
                String flightNumber = columns[2]
                flights << new Flight(time, eventType, flightNumber)

                schedule()
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace()
        }
    }

    protected void schedule() {
        // Sort all flights by arrival first, then by time
        flights.sort { "${it.event}:${it.scheduledTime}" }

        // Start by assiging the actual time as the scheduled time (best case)
        flights.each { it.actualTime = it.scheduledTime }

        flights.each { Flight flight ->
            boolean runwayFound = false

            while (!runwayFound && flight.actualTime < MIDNIGHT) {
                //Look for an open runway.. if we find one, assign it
                runways.eachWithIndex { PriorityQueue runway, int runwayNum ->
                    // See what the flight is for the runway
                    Flight lastFlight = runway.peek()

                    // If there are no flights for this runway yet, just put the flight on this runway
                    if (!lastFlight) {
                        runwayFound = true
                        flight.runway = runwayNum
                        runway.add(flight)
                    } else {
                        // figure out the duration for the flight
                        int duration = 1
                        if (lastFlight.event == Event.EventType.ARRIVAL) {
                            duration = arrivalReserveTime
                        } else if (lastFlight.event == Event.EventType.DEPARTURE) {
                            duration = departureReserveTime
                        }

                        // figure when the flight's reservation actually ends
                        LocalTime endTime = lastFlight.actualTime.plusMinutes(duration)

                        // if the last flight ends before this flight is going, add it to the runway
                        if (endTime < flight.actualTime) {
                            runwayFound = true
                            flight.runway = runwayNum
                            runway.add(flight)
                        }

                        // Looks like we're gonna try the next queue
                    }
                }
                flight.actualTime = flight.actualTime.plusMinutes(1)
            }
        }


    }

    /**
     * Returns an array of all processed flight arrivals and departures
     *
     * @return array of all flights processed
     */
    @Override
    Event[] getFlightsHandled() {
        flights as Event[]
    }

    Event[] getFlightsHandled(Event.EventType eventType) {
        flights.findAll { it.event == eventType } as Event[]
    }

}

