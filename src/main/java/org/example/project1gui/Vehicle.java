package org.example.project1gui;

class Vehicle {
    private Location currentLocation;
    private double maxFuelCapacity;
    private double currentFuel;
    private double milesPerGallon;

    public Vehicle(Location currentLocation, double maxFuelCapacity, double currentFuel, double milesPerGallon) {
        this.currentLocation = currentLocation;
        this.maxFuelCapacity = maxFuelCapacity;
        this.currentFuel = currentFuel;
        this.milesPerGallon = milesPerGallon;
    }

    // Getters and setters
    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public double getMaxFuelCapacity() {
        return maxFuelCapacity;
    }

    public void setMaxFuelCapacity(double maxFuelCapacity) {
        this.maxFuelCapacity = maxFuelCapacity;
    }

    public double getCurrentFuel() {
        return currentFuel;
    }

    public void setCurrentFuel(double currentFuel) {
        this.currentFuel = currentFuel;
    }

    public double getMilesPerGallon() {
        return milesPerGallon;
    }

    public void setMilesPerGallon(double milesPerGallon) {
        this.milesPerGallon = milesPerGallon;
    }

    // Method to fly to another location
    public String flyTo(Location destination) {
        // Check if the current location is the same as the destination
        if (currentLocation.equals(destination)) {
            return "You are already at " + destination.getName() + ".";
        }

        double distance = currentLocation.distanceTo(destination);
        double fuelNeeded = distance / milesPerGallon;

        // Calculate the maximum distance the vehicle can travel with a full tank
        double maxDistance = maxFuelCapacity * milesPerGallon;

        // Check if the distance exceeds the maximum distance the vehicle can travel
        if (distance > maxDistance) {
            return "This vehicle can't reach " + destination.getName() + ". Get one with a bigger tank!";
        }

        // Check if the fuel needed exceeds current fuel
        if (fuelNeeded <= currentFuel) {
            setCurrentLocation(destination);
            currentFuel -= fuelNeeded;
            return String.format("Flew to %s (%.2f miles).\nRemaining miles: %.2f miles.", destination.getName(), distance, currentFuel * milesPerGallon);

        } else {
            return "Not enough fuel to fly to " + destination.getName() + ". Please refuel.";
        }
    }


    // Method to refuel
    public String refuel(double fuel) {
        double availableSpace = maxFuelCapacity - currentFuel; // Calculate available space in the tank

        if (fuel < 0) {
            throw new IllegalArgumentException("Cannot refuel with a negative amount.");
        }

        if (currentFuel >= maxFuelCapacity) {
            return "Fuel is already at maximum capacity of " + maxFuelCapacity + " gallons.";
        }

        if (fuel > availableSpace) {
            // Adjust fuel to the maximum capacity
            currentFuel = maxFuelCapacity;
            return "Refuel cannot exceed maximum size of " + maxFuelCapacity + " gallons. Refueled to maximum capacity.";
        } else {
            currentFuel += fuel; // Add fuel
            return "Refueled: " + String.format("%.2f", fuel) + " gallons. Current fuel: " + String.format("%.2f", currentFuel) + " gallons.";
        }
    }


    // Display current vehicle status
    public void displayStatus() {
        System.out.println("org.example.project1gui.Vehicle is at " + currentLocation.getName() + ".");
        System.out.println("Current Fuel: " + String.format("%.2f", currentFuel) + " gallons.");
        System.out.println("Max Fuel Capacity: " + maxFuelCapacity + " gallons.");
        System.out.println("Range: " + getMilesPerGallon() * getMaxFuelCapacity() + "miles.");
    }
}