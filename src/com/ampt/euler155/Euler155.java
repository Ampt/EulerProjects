package com.ampt.euler155;

import java.util.ArrayList;
import java.util.Collections;

public class Euler155 {

    public static void main(String[] args) {
        CircuitBuilder cb;
        cb = new CircuitBuilder(60);
        System.out.println(cb.calculateNumberOfCircuits(3));
    }

}

class CircuitBuilder {
    private final int capStrength;

    private ArrayList<ArrayList<Circuit>> MasterCircuitsArray;

    public CircuitBuilder(int capStrength) {
        this.capStrength = capStrength;
        MasterCircuitsArray = new ArrayList<ArrayList<Circuit>>();
        ArrayList<Circuit> onesCircuit = new ArrayList<Circuit>();
        onesCircuit.add(new SimpleCircuit(capStrength));
        MasterCircuitsArray.add(onesCircuit);
    }

    public int calculateNumberOfCircuits(int capacitors) {
        for(int i = 1; i < capacitors; i++) {
            populateMasterArray(i);
        }
        ArrayList<Integer> values = pruneMasterList();
        Collections.sort(values);
        System.out.println("Values found!");
        for(int i: values) {
            System.out.println(i);
        }
        return values.size();
    }

    private void populateMasterArray(int count) {
        ArrayList<Circuit> circuitsInThisCount = new ArrayList<Circuit>();
        for(Circuit c: MasterCircuitsArray.get(count - 1)){
            ArrayList<Circuit> newMultiCircuit = new ArrayList<Circuit>();
            newMultiCircuit.add(c);
            newMultiCircuit.add(new SimpleCircuit(this.capStrength));
            circuitsInThisCount.add(new MultiCircuit(circuitType.parallel, newMultiCircuit));
            circuitsInThisCount.add(new MultiCircuit(circuitType.serial, newMultiCircuit));
        }
        MasterCircuitsArray.add(circuitsInThisCount);
    }

    private ArrayList<Integer> pruneMasterList() {
        ArrayList<Integer> foundCapValues = new ArrayList<Integer>();
        for(ArrayList<Circuit> listOfCircuits: MasterCircuitsArray) {
            for(Circuit c: listOfCircuits) {
                int cap = c.calculateEquivalentCapacitance();
                if(!foundCapValues.contains(cap)){
                    System.out.println("New Unique Value found!");
                    System.out.println("Cap: " + cap + " Rep: " + c.getStringRepresentation());
                    foundCapValues.add(cap);
                }
            }
        }
        return foundCapValues;
    }
}

enum circuitType{
    parallel,
    serial
}

class MultiCircuit implements Circuit {
    private final circuitType type;
    private final ArrayList<com.ampt.euler155.Circuit> innerCircuits;

    public MultiCircuit(circuitType type, ArrayList<Circuit> innerCircuits) {
        this.type = type;
        this.innerCircuits = innerCircuits;
    }

    @Override
    public int calculateEquivalentCapacitance() {
        if(type == circuitType.parallel) {
            int sum = 0;
            for(com.ampt.euler155.Circuit c: innerCircuits) {
                sum += c.calculateEquivalentCapacitance();
            }
            return sum;
        } else if (type == circuitType.serial) {
            double sum = 0;
            for(com.ampt.euler155.Circuit c: innerCircuits) {
                sum += 1.0 / c.calculateEquivalentCapacitance();
            }
            return (int) (1.0 / sum);
        } else {
            return Integer.MIN_VALUE;
        }
    }

    @Override
    public String getStringRepresentation() {
        String rep = "" + this.type.toString() + "[";
        for(Circuit c: this.innerCircuits) {
            rep += c.getStringRepresentation() + ", ";
        }
        rep = rep.substring(0, rep.length() - 2);
        rep += "]";
        return rep;
    }
}

class SimpleCircuit implements com.ampt.euler155.Circuit {
    private final int CapacitorStrength;

    public SimpleCircuit(int capacitorStrength) {
        this.CapacitorStrength = capacitorStrength;
    }

    public int calculateEquivalentCapacitance() {
        return this.CapacitorStrength;
    }

    @Override
    public String getStringRepresentation() {
        return "" + this.CapacitorStrength;
    }
}

