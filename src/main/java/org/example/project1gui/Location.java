package org.example.project1gui;

class Location {
    private String name;
    private double x;
    private double y;
    private boolean hasGasStation;

    public Location(String name, double x, double y, boolean hasGasStation) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.hasGasStation = hasGasStation;
    }

    // Getters and setters (Setters are not used because, for the purpose of this project, locations are treated as immutable.
    // When a user adds a location, we use the constructor to create a new instance with the specified attributes.)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean hasGasStation() {
        return hasGasStation;
    }

    public void setHasGasStation(boolean hasGasStation) {
        this.hasGasStation = hasGasStation;
    }

    // Method to calculate distance to another location
    public double distanceTo(Location other) {
        double deltaX = other.getX() - this.x;
        double deltaY = other.getY() - this.y;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY); // Euclidean distance
    }

    // If the equals() method is not overridden in the Location class,
    // the default implementation checks if two references point to the same object in memory.
    // So, two Location objects with the same attributes (e.g., name, coordinates)
    // will not be considered equal unless they are the exact same instance.

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Location other)) return false;
        return this.name.equals(other.name); // I used the name as unique identifier
    }

}
