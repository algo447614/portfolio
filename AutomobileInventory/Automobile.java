public class Automobile {
    private String make;
    private String model;
    private String color;
    private int year;
    private int mileage;

    // Default Constructor
    public Automobile() {
    }

    // Parameterized Constructor
    public Automobile(String make, String model, String color, int year, int mileage) {
        this.make = make;
        this.model = model;
        this.color = color;
        this.year = year;
        this.mileage = mileage;
    }

    // Add a new vehicle method
    public String addNewVehicle(String make, String model, String color, int year, int mileage) {
        try {
            // Implement logic to add a new vehicle
            this.make = make;
            this.model = model;
            this.color = color;
            this.year = year;
            this.mileage = mileage;
            return "Vehicle added successfully.";
        } catch (Exception e) {
            return "Failed to add a new vehicle. " + e.getMessage();
        }
    }

    // List vehicle information method
    public String[] listVehicleInformation() {
        try {
            // Implement logic to list vehicle information
            String[] vehicleInfo = {make, model, color, String.valueOf(year), String.valueOf(mileage)};
            return vehicleInfo;
        } catch (Exception e) {
            return new String[]{"Failed to list vehicle information. " + e.getMessage()};
        }
    }

    // Remove a vehicle method
    public String removeVehicle() {
        try {
            // Implement logic to remove a vehicle
            make = "";
            model = "";
            color = "";
            year = 0;
            mileage = 0;
            return "Vehicle removed successfully.";
        } catch (Exception e) {
            return "Failed to remove the vehicle. " + e.getMessage();
        }
    }

    // Update vehicle attributes method
    public String updateVehicleAttributes(String make, String model, String color, int year, int mileage) {
        try {
            // Implement logic to update vehicle attributes
            this.make = make;
            this.model = model;
            this.color = color;
            this.year = year;
            this.mileage = mileage;
            return "Vehicle attributes updated successfully.";
        } catch (Exception e) {
            return "Failed to update vehicle attributes. " + e.getMessage();
        }
    }
}
