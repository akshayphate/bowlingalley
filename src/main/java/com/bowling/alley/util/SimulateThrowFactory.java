package com.bowling.alley.util;

import javax.swing.*;
import java.util.HashMap;
import java.util.HashSet;

public class SimulateThrowFactory {
    private static SimulateThrow simulationFrame;
    private static HashMap<Integer, SimulateThrow> mapOfSimulationFrames = new HashMap<>();

    public static SimulateThrow getSimulationFrame(int laneNumber) {
        if (mapOfSimulationFrames.get(laneNumber) == null) {
            simulationFrame = new SimulateThrow(laneNumber);
            mapOfSimulationFrames.put(laneNumber, simulationFrame);
        }

        return mapOfSimulationFrames.get(laneNumber);
    }

    public static SimulateThrow getSimulationFrame() {
        if (simulationFrame == null) {
            simulationFrame = new SimulateThrow();
        }

        simulationFrame.setVisible(true);
        return simulationFrame;
    }



}
