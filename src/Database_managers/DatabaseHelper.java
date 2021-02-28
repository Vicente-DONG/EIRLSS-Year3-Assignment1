package Database_managers;

import Objects.*;
import java.util.ArrayList;

//The database helper is to not give classes direct access to the database whilst. It also performs other necessary logical steps that are not relevant to the database
public class DatabaseHelper {

    private final DatabaseManager dbMgr = new DatabaseManager();

    public boolean signUpUser(CustomerDTO newCustomer) {

        boolean worked = dbMgr.insertCustomer(newCustomer);

        return worked;
    }

    public boolean logInCustomer(String username, String password) {

        return dbMgr.authenticateCustomer(username, password);
    }

    public ArrayList<VehicleDTO> getAllVehicles() {
        ArrayList<VehicleDTO> vehiclesToReturn = dbMgr.queryAllVehicles();

        return vehiclesToReturn;
    }
    
    public ArrayList<CustomerDTO> getAllCustomers() {
        ArrayList<CustomerDTO> customersToReturn = dbMgr.queryAllCustomers();

        return customersToReturn;
    }
    
    public boolean cancelCustomerBooking(int BookingID)
    {
        return dbMgr.cancelBooking(BookingID);
    }
    
    public boolean modifyBooking(BookingDTO modifiedBooking)
    {
        return dbMgr.updateBooking(modifiedBooking);
    }

    public ArrayList<BookingDTO> getCustomerBookings(int customerID) {
        return dbMgr.queryForBookingsThroughCustomerID(customerID);
    }

    public boolean extendCustomerBooking(BookingDTO newBooking) {
        return dbMgr.updateBooking(newBooking);
    }

    public boolean bookVehicle(BookingDTO newBooking) {
        return dbMgr.insertBooking(newBooking);
    }

    public boolean logInAdmin(String username, String password) {
        return dbMgr.authenticateAdmin(username, password);
    }

    public CustomerDTO getCustomerFromLogIn(String username, String password) {
        return dbMgr.queryForCustomerWithLogIn(username, password);
    }

    public AdminDTO getAdminFromLogIn(String ussername, String password) {
        return dbMgr.queryForAdmin(ussername, password);
    }

    public ArrayList<BookingDTO> getAllBookings() {
        ArrayList<BookingDTO> bookings = dbMgr.queryAllBookings();

        return bookings;
    }

    public boolean updateCustomer(CustomerDTO blacklistedCustomer) {
        return dbMgr.updateCustomer(blacklistedCustomer);
    }
    
    public boolean updateBooking(BookingDTO updateBooking) {
        return dbMgr.updateBooking(updateBooking);
    }

    public boolean finishBooking(BookingDTO bookingToFinish) {
        return dbMgr.updateBooking(bookingToFinish);
    }

    public boolean deleteBooking(int BookingID) {
        return dbMgr.deleteBooking(BookingID);
    }

    public ArrayList<BookingDTO> getVehicleBookings(int vehicleID) {
        ArrayList<BookingDTO> bookings = dbMgr.queryForBookingsThroughVehicleID(vehicleID);

        return bookings;
    }
}
