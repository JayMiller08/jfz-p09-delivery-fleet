package jfz.fleet;

import java.util.ArrayList;

/** A company's vehicles. Every vehicle is treated as a Vehicle, whatever its real class. */
public class Fleet {
    // TODO: a private list of Vehicles, created here.

    /** Adds a vehicle, or refuses one whose registration is already in the fleet. */
    public boolean add(Vehicle vehicle) {
        return false; // TODO
    }

    public int size() {
        return 0; // TODO
    }

    /** The vehicles that can carry this parcel, in the order they were added. */
    public ArrayList<Vehicle> vehiclesThatCanCarry(int kg) {
        return new ArrayList<>(); // TODO
    }

    /** The cheapest vehicle that can carry the parcel, or null if none can. A tie goes to the first added. */
    public Vehicle cheapestFor(int kg, int km) {
        return null; // TODO
    }
}
