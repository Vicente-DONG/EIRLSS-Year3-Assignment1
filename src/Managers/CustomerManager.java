/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import Database_managers.DatabaseHelper;
import Objects.BookingDTO;
import Objects.CustomerDTO;
import Objects.VehicleDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Vicen
 */
public class CustomerManager {

    private static final CustomerManager instance = new CustomerManager();
    private final DatabaseHelper dBHelper = new DatabaseHelper();
    private CustomerDTO customerUser;

    private CustomerManager() {};
    
    public boolean authenticateCustomer(String username, String password) {
        boolean exists = dBHelper.logInCustomer(username, password);

        if (exists == true) {
            customerUser = dBHelper.getCustomerFromLogIn(username, password);

            return true;
        } else {
            return false;
        }
    }
    
    public boolean modifyBooking(BookingDTO modifiedBooking)
    {
        return dBHelper.modifyBooking(modifiedBooking);
    }

    public boolean signUpCustomer(String customerUsername, String customerPassword, String customerFirstName, String customerLastName, LocalDate customerDOB, String customerAddress, boolean blacklisted) {
        try {
            CustomerDTO newCustomer = new CustomerDTO(customerFirstName, customerLastName, customerDOB, customerAddress, blacklisted, customerUsername, customerPassword);
            return dBHelper.signUpUser(newCustomer);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUserBlacklisted() {
        return customerUser.isBlacklisted();
    }

    public boolean checkUsernameAvailability(String username) {
        ArrayList<CustomerDTO> listOfCustomers = dBHelper.getAllCustomers();
        boolean usernameFree = true;

        try {
            for (int i = 0; i < listOfCustomers.size(); i++) {
                if (listOfCustomers.get(i).getUsername().equals(username)) {
                    usernameFree = false;
                }
            }
        } catch (Exception e) {
            usernameFree = false;
        }
        
        return usernameFree;
    }
    
    public boolean allowBookingExtension(LocalDateTime endOfBooking)
    {
        if(LocalDateTime.now().isAfter(LocalDateTime.of(endOfBooking.getYear(), endOfBooking.getMonth(), endOfBooking.getDayOfMonth(), 16, 0)) )
        {
            return false;
        } else {
            return true;
        }
    }
    
    public ArrayList<VehicleDTO> vehiclesAvailable(LocalDateTime startDate, LocalDateTime endDate)
    {
        ArrayList<VehicleDTO> vehiclesAvailable = new ArrayList<>();
        ArrayList<VehicleDTO> allVehicles = dBHelper.getAllVehicles();
        for (int i = 0; i < allVehicles.size(); i++) {
            ArrayList<BookingDTO> allOfThisVehicleBookings = dBHelper.getVehicleBookings(allVehicles.get(i).getVehicleID());
            boolean vehicleAvailable = true;
            for (int ii = 0; ii < allOfThisVehicleBookings.size(); ii++) {
                if(allOfThisVehicleBookings.get(ii).getBookingEndDay().isBefore(startDate) || allOfThisVehicleBookings.get(ii).getBookingStartDay().isAfter(endDate)){}
                else {
                    vehicleAvailable=false;
                }
            }          
            if(vehicleAvailable)
            {
                vehiclesAvailable.add(allVehicles.get(i));
            }
        }
        
        return vehiclesAvailable;
    }
    
    public double calculateBookingPrice(double pricePerDay, long numberOfDays, boolean halfDay)
    {
        if(halfDay)
        {
            return (pricePerDay * numberOfDays + (pricePerDay/2));
        } else 
        {
            return (pricePerDay * numberOfDays);
        }
    }
    
    public ArrayList<BookingDTO> getBookingsByCustomerID(int customerID)
    {
        return dBHelper.getCustomerBookings(customerID);
    }
    
    public ArrayList<VehicleDTO> getAllVehiclesForCustomer()
    {
        return dBHelper.getAllVehicles();
    }
    
    public boolean cancelBooking(int BookingID)
    {
        return dBHelper.cancelCustomerBooking(BookingID);
    }
    
    public ArrayList<BookingDTO> customersBookings ()
    {
        return dBHelper.getCustomerBookings(customerUser.getCustomerID());
    }
    
    public boolean createBooking(BookingDTO newBooking)
    {
        return dBHelper.bookVehicle(newBooking);
    }

    public void logOff() {
        customerUser = null;
    }

    public CustomerDTO getCustomerUser() {
        return customerUser;
    }

    public static CustomerManager getInstance() {
        return instance;
    }

}
