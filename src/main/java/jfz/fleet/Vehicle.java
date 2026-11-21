package jfz.fleet;

/** Any delivery vehicle. This class is finished: your subclasses build on it. */
public class Vehicle {
    private final String registration;
    private final int maxKg;

    public Vehicle(String registration, int maxKg) {
        this.registration = registration;
        this.maxKg = maxKg;
    }

    public String getRegistration() {
        return registration;
    }

    public int getMaxKg() {
        return maxKg;
    }

    /** True for a parcel of at least 1 kg and no more than maxKg. */
    public boolean canCarry(int kg) {
        return kg >= 1 && kg <= maxKg;
    }

    /** The generic rate, R100 plus R5 a km, in cents. Subclasses override it. */
    public int costCents(int kg, int km) {
        return 10000 + 500 * km;
    }

    /** The kind of vehicle, used by toString. Subclasses override it. */
    public String type() {
        return "Vehicle";
    }

    @Override
    public String toString() {
        return type() + " " + registration + " (max " + maxKg + " kg)";
    }
}
