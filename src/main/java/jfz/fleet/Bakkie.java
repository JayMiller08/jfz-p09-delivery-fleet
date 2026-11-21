package jfz.fleet;

/** Carries up to 1000 kg. Costs R50 plus R6 a km. */
public class Bakkie extends Vehicle {

    public Bakkie(String registration) {
        super(registration, 0); // TODO: pass this vehicle's maximum load
    }

    // TODO: override costCents(int kg, int km) with this vehicle's rule, and
    //       type() so it returns "Bakkie". Put @Override on both.
}
