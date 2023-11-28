package entity;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class VehicleDemo {

	private static void writeToFile(String fileName, String content) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			writer.write(content);
			System.out.println("File '" + fileName + "' written successfully.");
		} catch (IOException e) {
			System.err.println("Error writing to file: " + e.getMessage());
		}
	}

	public static void main(String[] args) {

		Vehicle[] vehicles = new Vehicle[3];
		// Polymorphism
		vehicles[0] = new Car("Toyota Camry", 2022, 4);
		vehicles[1] = new Car("Toyota Vios", 2021, 4);
		vehicles[2] = new Vehicle("Motorcycle", 2021);

		String combinedVehicleInfo = "";
		// For Loop with handle string
		for (Vehicle vehicle : vehicles) {
			combinedVehicleInfo += vehicle.toString().split("\\.") + "\n";
			vehicle.displayInfo();
			vehicle.move();

			if (vehicle instanceof Car) {
				((Car) vehicle).honk();
			}

            System.out.println();
		}
		
		// Handle File 
		writeToFile("src/entity/output.txt", combinedVehicleInfo);

	}
}
