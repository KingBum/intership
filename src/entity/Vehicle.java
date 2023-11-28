package entity;

// getter/setter for encapsulated
public class Vehicle implements Movable{
    private String model;
    private int year;

    public Vehicle(String model, int year) {
        this.model = model;
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    @Override
    public void move() {
        System.out.println("Vehicle is moving");
    }

    public void displayInfo() {
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
    }
}