package com.ampt.euler155;

import java.util.ArrayList;

/**
 * Created by Ampt on 7/21/2014.
 */
public class main {

    public static void main(String[] args) {

    }

    private class CircuitBuilder {
        public final int capStrength;

        private ArrayList<ArrayList<MultiCircuit>> MasterCircuitsArray;

        public CircuitBuilder(int capStrength) {
            this.capStrength = capStrength;
        }

        public int calculateNumberOfCircuits(int capacitors) {
            if(capacitors == 1) {
                return 1;
            } else if (capacitors == 2){
                return 2;
            } else {
                Circuit c1 = new SimpleCircuit(this.capStrength);
                Circuit c2 = new SimpleCircuit(this.capStrength);
                MultiCircuit mc = new MultiCircuit();
            }
            return Integer.MIN_VALUE;
        }
    }

    enum circuitType{
        parallel,
        serial
    }
    private class MultiCircuit implements Circuit {
        private final circuitType type;
        private final ArrayList<Circuit> innerCircuits;

        public MultiCircuit(circuitType type, ArrayList<Circuit> innerCircuits) {
            this.type = type;
            this.innerCircuits = innerCircuits;
        }

        @Override
        public int calculateEquivalentCapacitance() {
            if(type == circuitType.parallel) {
                int sum = 0;
                for(Circuit c: innerCircuits) {
                    sum += c.calculateEquivalentCapacitance();
                }
                return sum;
            } else if (type == circuitType.serial) {
                double sum = 0;
                for(Circuit c: innerCircuits) {
                    sum += 1.0 / c.calculateEquivalentCapacitance();
                }
                return (int) (1.0 / sum);
            } else {
                return Integer.MIN_VALUE;
            }
        }
    }

    private class SimpleCircuit implements Circuit {
        private final int CapacitorStrength;

        public SimpleCircuit(int capacitorStrength) {
            this.CapacitorStrength = capacitorStrength;
        }

        public int calculateEquivalentCapacitance() {
            return this.CapacitorStrength;
        }
    }

}

