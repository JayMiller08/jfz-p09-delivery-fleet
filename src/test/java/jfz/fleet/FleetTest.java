package jfz.fleet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FleetTest {

    @Test
    @DisplayName("Motorbike, Bakkie and Truck each extend Vehicle directly")
    void subclassesExtendVehicle() {
        assertEquals(Vehicle.class, Motorbike.class.getSuperclass(), "Motorbike should be declared with extends Vehicle.");
        assertEquals(Vehicle.class, Bakkie.class.getSuperclass(), "Bakkie should be declared with extends Vehicle.");
        assertEquals(Vehicle.class, Truck.class.getSuperclass(), "Truck should be declared with extends Vehicle.");
    }

    @Test
    @DisplayName("Each vehicle has its own maximum load, passed up with super(...)")
    void capacities() {
        assertEquals(20, new Motorbike("BIKE-01").getMaxKg(), "A motorbike carries up to 20 kg.");
        assertEquals(1000, new Bakkie("BAKKIE-01").getMaxKg(), "A bakkie carries up to 1000 kg.");
        assertEquals(8000, new Truck("TRUCK-01").getMaxKg(), "A truck carries up to 8000 kg.");
        assertEquals("BIKE-01", new Motorbike("BIKE-01").getRegistration(), "Pass the registration to super(...) too.");
    }

    @Test
    @DisplayName("canCarry is inherited, and its boundaries hold for every vehicle")
    void canCarryBoundaries() {
        Motorbike bike = new Motorbike("BIKE-01");
        assertTrue(bike.canCarry(20), "Exactly 20 kg fits on a motorbike.");
        assertFalse(bike.canCarry(21), "21 kg is too much for a motorbike.");
        assertFalse(bike.canCarry(0), "A 0 kg parcel isn't a parcel.");
        assertTrue(new Truck("TRUCK-01").canCarry(8000), "Exactly 8000 kg fits on a truck.");
    }

    @Test
    @DisplayName("Motorbike and Bakkie override costCents with their own rates")
    void bikeAndBakkieCosts() {
        assertEquals(4500, new Motorbike("BIKE-01").costCents(5, 10), "Motorbike: 1500 + 300 x 10 km = 4500 cents.");
        assertEquals(11000, new Bakkie("BAKKIE-01").costCents(300, 10), "Bakkie: 5000 + 600 x 10 km = 11000 cents.");
        assertEquals(15000, new Vehicle("VAN-01", 500).costCents(300, 10),
                "Vehicle keeps its generic rate: 10000 + 500 x 10 km. Don't change Vehicle.");
    }

    @Test
    @DisplayName("Truck charges R1 for every started 100 kg")
    void truckCost() {
        Truck truck = new Truck("TRUCK-01");
        assertEquals(32200, truck.costCents(150, 10), "20000 + 1200 x 10 + 2 started hundreds x 100 = 32200.");
        assertEquals(32100, truck.costCents(100, 10), "100 kg is exactly 1 hundred: 20000 + 12000 + 100.");
        assertEquals(32200, truck.costCents(101, 10), "101 kg starts a second hundred.");
        assertEquals(32100, truck.costCents(1, 10), "Even 1 kg starts the first hundred.");
    }

    @Test
    @DisplayName("toString shows each vehicle's own type")
    void descriptions() {
        assertEquals("Motorbike BIKE-01 (max 20 kg)", new Motorbike("BIKE-01").toString(),
                "Vehicle's toString calls type(), so overriding type() is enough.");
        assertEquals("Bakkie BAKKIE-01 (max 1000 kg)", new Bakkie("BAKKIE-01").toString());
        assertEquals("Truck TRUCK-01 (max 8000 kg)", new Truck("TRUCK-01").toString());
    }

    @Test
    @DisplayName("A Vehicle variable holding a Truck still uses the Truck's rules")
    void overridingThroughVehicle() {
        Vehicle vehicle = new Truck("TRUCK-01");
        assertEquals(32200, vehicle.costCents(150, 10), "The object is a Truck, so the Truck's costCents runs.");
        assertEquals("Truck", vehicle.type());
    }

    @Test
    @DisplayName("Fleet.add refuses a registration that's already in the fleet")
    void addVehicles() {
        Fleet fleet = new Fleet();
        assertTrue(fleet.add(new Motorbike("BIKE-01")));
        assertTrue(fleet.add(new Bakkie("BAKKIE-01")));
        assertFalse(fleet.add(new Truck("BIKE-01")), "BIKE-01 is already in the fleet, even though this one's a truck.");
        assertEquals(2, fleet.size(), "The refused vehicle must not be added.");
    }

    @Test
    @DisplayName("vehiclesThatCanCarry lists the able vehicles in the order they were added")
    void vehiclesThatCanCarry() {
        Fleet fleet = new Fleet();
        Motorbike bike = new Motorbike("BIKE-01");
        Bakkie bakkie = new Bakkie("BAKKIE-01");
        Truck truck = new Truck("TRUCK-01");
        fleet.add(bike);
        fleet.add(bakkie);
        fleet.add(truck);
        assertEquals(List.of(bakkie, truck), fleet.vehiclesThatCanCarry(25), "25 kg is too heavy for the motorbike.");
        assertEquals(List.of(bike, bakkie, truck), fleet.vehiclesThatCanCarry(20), "Everyone can carry 20 kg.");
        assertEquals(List.of(), fleet.vehiclesThatCanCarry(9000), "Nothing carries 9000 kg.");
    }

    @Test
    @DisplayName("cheapestFor picks the cheapest vehicle that can carry the parcel")
    void cheapest() {
        Fleet fleet = new Fleet();
        Truck truck = new Truck("TRUCK-01");
        Bakkie bakkie = new Bakkie("BAKKIE-01");
        Motorbike bike = new Motorbike("BIKE-01");
        fleet.add(truck);
        fleet.add(bakkie);
        fleet.add(bike);
        assertSame(bike, fleet.cheapestFor(5, 10), "The motorbike is cheapest for a small parcel.");
        assertSame(bakkie, fleet.cheapestFor(25, 10), "25 kg rules out the motorbike, and the bakkie beats the truck.");
        assertSame(truck, fleet.cheapestFor(1500, 10), "Only the truck can carry 1500 kg.");
        assertNull(fleet.cheapestFor(9000, 10), "Nothing can carry 9000 kg, so the answer is null.");
        assertNull(fleet.cheapestFor(0, 10), "A 0 kg parcel can't be carried.");
    }

    @Test
    @DisplayName("cheapestFor compares costs, not vehicle types, and a tie goes to the first added")
    void cheapestComparesCosts() {
        Fleet fleet = new Fleet();
        Bakkie bakkie = new Bakkie("BAKKIE-01");
        Vehicle van = new Vehicle("VAN-01", 500);
        fleet.add(bakkie);
        fleet.add(van);
        assertSame(bakkie, fleet.cheapestFor(25, 10), "At 10 km the bakkie (11000) beats the van (15000).");
        assertSame(van, fleet.cheapestFor(25, 100), "At 100 km the van (60000) beats the bakkie (65000).");
        Motorbike first = new Motorbike("BIKE-01");
        Motorbike second = new Motorbike("BIKE-02");
        Fleet bikes = new Fleet();
        bikes.add(first);
        bikes.add(second);
        assertSame(first, bikes.cheapestFor(5, 10), "Two motorbikes cost the same; the first one added wins.");
    }
}
