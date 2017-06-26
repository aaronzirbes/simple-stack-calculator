package org.zirbes.tower

import groovy.transform.CompileStatic

import java.io.FileNotFoundException
import java.io.PrintWriter
import java.time.LocalTime
import java.util.Random

@CompileStatic
abstract class Airport {

    static final double DEPARTURE_PROB = 0.20
    static final double ARRIVAL_PROB = 0.15
    static final int ARR_RESERVE_TIME = 2
    static final int DEP_RESERVE_TIME = 3
    static final int SIM_MINUTES = 60
    static final long SEED = 20170620001L
    static final Random RAND = new Random(SEED)
    static final String DELIM = '|'

    static void genEventFile(String fileName) {
        genEventFile(fileName, ARRIVAL_PROB, DEPARTURE_PROB, SIM_MINUTES)
    }

    static void genEventFile(String fileName,
                             double arrivalProbability,
                             double departureProbability,
                             int minutes) {
        int arrFlightId = 1
        int depFlightId = 1
        try {
            File simFile = new File(fileName)
            LocalTime time = LocalTime.parse("00:00")
            (0..minutes).each { i ->
                LocalTime now = time.plusMinutes(i)
                if (RAND.nextDouble() <= arrivalProbability) {
                    simFile << "${now}|${Event.EventType.ARRIVAL}|A${arrFlightId++}\n"
                }
                if (RAND.nextDouble() <= departureProbability) {
                    simFile << "${now}|${Event.EventType.DEPARTURE}|D${depFlightId++}\n"
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace()
        }
    }

    /**
     * Reads and processes the specified flight event file.
     * @param filename path to the flight event file.
     */
    abstract void processEventFile(String filename)

    /**
     * Returns an array of all processed flight arrivals and departures
     *
     * @return array of all flights processed
     */
    abstract Event[] getFlightsHandled()

}

