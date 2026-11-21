package jfz.fleet;

/** Carries up to 8000 kg. Costs R200 plus R12 a km, plus R1 for every started 100 kg. */
public class Truck extends Vehicle {

    public Truck(String registration) {
        super(registration, 0); // TODO: pass this vehicle's maximum load
    }

    // TODO: override costCents(int kg, int km) with this vehicle's rule, and
    //       type() so it returns "Truck". Put @Override on both.
}
