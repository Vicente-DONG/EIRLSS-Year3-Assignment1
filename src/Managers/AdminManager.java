/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import Database_managers.DatabaseHelper;
import Objects.AdminDTO;
import Objects.BookingDTO;
import Objects.CustomerDTO;
import java.util.ArrayList;

/**
 *
 * @author Vicen
 */
public class AdminManager {
    private static final AdminManager instance = new AdminManager();
    private final DatabaseHelper dBHelper = new DatabaseHelper();
    private AdminDTO adminUser;
    
    private AdminManager(){};

    public boolean authenticateAdmin(String username, String password)
    {
        boolean exists = dBHelper.logInAdmin(username, password);
        
        if(exists == true)
        {
            adminUser = dBHelper.getAdminFromLogIn(username, password);
            
            return true;
        } else {
            return false;
        }
    }
    
    public ArrayList<BookingDTO> getAllBookings()
    {
        return dBHelper.getAllBookings();
    }

    public AdminDTO getAdminUser() {
        return adminUser;
    }
    
    public CustomerDTO blackListCustomer(CustomerDTO blacklistedCustomer)
    {
        blacklistedCustomer.setBlacklisted(true);
        dBHelper.updateCustomer(blacklistedCustomer);
        return blacklistedCustomer;
    }
    
    public BookingDTO updateBooking(BookingDTO newBooking)
    {
        newBooking.setBookingFinished(true);
        dBHelper.updateBooking(newBooking);
        return newBooking;
    }
    
    
    public void logOff()
    {
        adminUser = null;
    }
    
    //Returns an instance of the singleton
    public static AdminManager getInstance() {
        return instance;
    }
    
    
}
