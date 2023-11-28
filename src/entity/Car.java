package entity;

// Inherits from Vehicle and Movable
public class Car extends Vehicle implements Movable{
	private int numberOfDoors;

	public Car(String model, int year, int numberOfDoors) {
		super(model, year);
		this.numberOfDoors = numberOfDoors;
	}

	public void honk() {
		System.out.println("Honk! Honk!");
	}
	
	@Override
    public void move() {
        System.out.println("Car is moving");
    }

	@Override
	public void displayInfo() {
		super.displayInfo();
		System.out.println("Number of Doors: " + numberOfDoors);
	}
}
