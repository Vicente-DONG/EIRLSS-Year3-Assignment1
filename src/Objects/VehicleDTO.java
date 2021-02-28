package Objects;

public class VehicleDTO {

    private int vehicleID;
    private String vehicleName;
    private double vehicleRentalperDay;
    private String vehicleTransmissionType;
    private String vehicleFuelType;

    //For database use
    public VehicleDTO(int vehicleID, String vehicleName, double vehicleRentalperDay, String vehicleTransmissionType, String vehicleFuelType) {
        this.vehicleID = vehicleID;
        this.vehicleName = vehicleName;
        this.vehicleRentalperDay = vehicleRentalperDay;
        this.vehicleTransmissionType = vehicleTransmissionType;
        this.vehicleFuelType = vehicleFuelType;
    }

    //For environment use
    public VehicleDTO(String vehicleName, double vehicleRentalperDay, String vehicleTransmissionType, String vehicleFuelType) {
        this.vehicleName = vehicleName;
        this.vehicleRentalperDay = vehicleRentalperDay;
        this.vehicleTransmissionType = vehicleTransmissionType;
        this.vehicleFuelType = vehicleFuelType;
    }

    @Override
    public String toString() {
        return "Vehicle ID: " + vehicleID + "\n"
                + "Vehicle name: " + vehicleName + "\n"
                + "Vehicle price per day: " + String.format("%.2f", vehicleRentalperDay) + "£\n"
                + "Vehicle transmission type: " + vehicleTransmissionType + "\n"
                + "Vehicle fuel type: " + vehicleFuelType;
    }

    //Standard getters and setters
    public int getVehicleID() {
        return vehicleID;
    }

    //No need to set ID since database already does that
    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public double getVehicleRentalperDay() {
        return vehicleRentalperDay;
    }

    public void setVehicleRentalperDay(double vehicleRentalperDay) {
        this.vehicleRentalperDay = vehicleRentalperDay;
    }

    public String getVehicleTransmissionType() {
        return vehicleTransmissionType;
    }

    public void setVehicleTransmissionType(String vehicleTransmissionType) {
        this.vehicleTransmissionType = vehicleTransmissionType;
    }

    public String getVehicleFuelType() {
        return vehicleFuelType;
    }

    public void setVehicleFuelType(String vehicleFuelType) {
        this.vehicleFuelType = vehicleFuelType;
    }

    //Overriding equals and hashCode for testing
    @Override
    public boolean equals(Object o) {
        if (getClass() == o.getClass()) {
            return true;
        } else {
            return false;
        }
    }
}
