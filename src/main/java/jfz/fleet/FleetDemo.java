package jfz.fleet;

/** Run this to try your fleet by hand. The tests don't use it, so change it as you like. */
public class FleetDemo {
    public static void main(String[] args) {
        Fleet fleet = new Fleet();
        fleet.add(new Motorbike("BIKE-01"));
        fleet.add(new Bakkie("BAKKIE-01"));
        fleet.add(new Truck("TRUCK-01"));

        int[][] parcels = {{5, 12}, {300, 40}, {2500, 80}, {9000, 10}};
        for (int[] parcel : parcels) {
            int kg = parcel[0];
            int km = parcel[1];
            Vehicle best = fleet.cheapestFor(kg, km);
            if (best == null) {
                System.out.println(kg + " kg, " + km + " km: no vehicle can carry it");
            } else {
                System.out.println(kg + " kg, " + km + " km: " + best + " for " + best.costCents(kg, km) + " cents");
            }
        }
    }
}
