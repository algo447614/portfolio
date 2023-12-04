import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // Call automobile class with parameterized constructor
            Automobile car = new Automobile("Toyota", "Camry", "Silver", 2022, 15000);

            // Call the method to list the values
            String[] vehicleInfo = car.listVehicleInformation();
            System.out.println("Vehicle Information:");
            for (String info : vehicleInfo) {
                System.out.println(info);
            }

            // Call the remove vehicle method to clear the variables
            String removeResult = car.removeVehicle();
            System.out.println("Remove Vehicle Result: " + removeResult);

            // Add a new vehicle
            String addResult = car.addNewVehicle("Honda", "Accord", "Blue", 2021, 20000);
            System.out.println("Add Vehicle Result: " + addResult);

            // Call the list method and print the new vehicle information to the screen
            vehicleInfo = car.listVehicleInformation();
            System.out.println("Updated Vehicle Information:");
            for (String info : vehicleInfo) {
                System.out.println(info);
            }

            // Update the vehicle
            String updateResult = car.updateVehicleAttributes("Ford", "Mustang", "Red", 2023, 10000);
            System.out.println("Update Vehicle Result: " + updateResult);

            // Call the listing method and print the information to the screen
            vehicleInfo = car.listVehicleInformation();
            System.out.println("Final Vehicle Information:");
            for (String info : vehicleInfo) {
                System.out.println(info);
            }

            // Display a message asking if the user wants to print the information to a file (Y or N)
            Scanner scanner = new Scanner(System.in);
            System.out.print("Do you want to print the information to a file? (Y/N): ");
            String response = scanner.nextLine().trim().toUpperCase();

            if ("Y".equals(response)) {
                // Call a method to print the information to a file
                printToFile(vehicleInfo);
            } else if ("N".equals(response)) {
                System.out.println("A file will not be printed.");
            } else {
                System.out.println("Invalid response. Please enter Y or N.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // Method to print the information to a file (replace this with actual file writing logic)
    private static void printToFile(String[] vehicleInfo) {
        System.out.println("Information printed to file (simulated).");
        // Implement logic to write information to a file (e.g., C:\Temp\Autos.txt)
    }
}
