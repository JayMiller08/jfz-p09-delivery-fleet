package jfz.fleet;

/** Carries up to 20 kg. Costs R15 plus R3 a km. */
public class Motorbike extends Vehicle {

    public Motorbike(String registration) {
        super(registration, 0); // TODO: pass this vehicle's maximum load
    }

    // TODO: override costCents(int kg, int km) with this vehicle's rule, and
    //       type() so it returns "Motorbike". Put @Override on both.
}
