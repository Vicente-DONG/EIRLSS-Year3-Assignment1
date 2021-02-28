
import Database_managers.DatabaseManager;
import Objects.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import Database_managers.DatabaseHelper;


public class DatabaseHelperTest {
    DatabaseHelper DH = new DatabaseHelper();
    DatabaseManager DM = new DatabaseManager();
    VehicleDTO v1 = new VehicleDTO(2, "Small town car", 40.00, "Manual", "Petrol");
    CustomerDTO c1 = new CustomerDTO(1, "John", "Johnson", LocalDate.of(1999, 10, 11), "Street street n1", false, "customer1", "customer1");
    BookingDTO b1 = new BookingDTO(1, v1, c1, LocalDateTime.of(2020, 12, 27, 12, 22, 11), LocalDateTime.of(2020, 12, 30, 23, 10), 120.00, false, false, true, false, false);
    AdminDTO a1 = new AdminDTO("admin1", "admin1");
    
    @Test
    public void testGetVehicles()
    {
        VehicleDTO v1 = new VehicleDTO(1, "Small town car", 40.00, "Manual", "Hybrid");
        VehicleDTO v2 = new VehicleDTO(2, "Small town car", 40.00, "Manual", "Petrol");
        VehicleDTO v3 = new VehicleDTO(3, "Small family hatchback", 55.00, "Automatic", "Diesel");
        VehicleDTO v4 = new VehicleDTO(4, "Small family hatchback", 55.00, "Manual", "Petrol");
        VehicleDTO v5 = new VehicleDTO(5, "Large family saloon", 60.00, "Manual", "Petrol");
        VehicleDTO v6 = new VehicleDTO(6, "Large family estate", 75.00, "Manual", "Petrol");
        VehicleDTO v7 = new VehicleDTO(7, "Medium van", 70.00, "Manual", "Petrol");
        VehicleDTO v8 = new VehicleDTO(8, "Medium van", 70.00, "Manual", "Petrol");
        
        ArrayList<VehicleDTO> expectedArray = new ArrayList<VehicleDTO>();
        
        expectedArray.add(v1);
        expectedArray.add(v2);
        expectedArray.add(v3);
        expectedArray.add(v4);
        expectedArray.add(v5);
        expectedArray.add(v6);
        expectedArray.add(v7);
        expectedArray.add(v8);
        
         ArrayList<VehicleDTO> actualArray = DH.getAllVehicles();
         
         assertEquals(expectedArray, actualArray);
    }
    
    @Test
    public void testGetAllBookings()
    {
        BookingDTO testBooking = new BookingDTO(1, v1, c1, LocalDateTime.of(2020, 12, 29, 11, 14, 00), LocalDateTime.of(2021, 1, 05, 11, 14, 0), 360.00, true, true, true, false, false);
        
        ArrayList<BookingDTO> resultArray = DH.getAllBookings();
        
        BookingDTO resultBooking = resultArray.get(0);
        
        assertEquals(testBooking, resultBooking);
    }
    
    @Test
    public void testLogInAdmin()
    {
        boolean resultAdmin = DH.logInAdmin(a1.getUsername(), a1.getUserPassword());
        
        assertEquals(true, resultAdmin);
    }
    
    @Test
    public void testQueryForLogIn()
    {
        AdminDTO resultAdmin = DH.getAdminFromLogIn(a1.getUsername(), a1.getUserPassword());
        
        assertEquals(a1, resultAdmin);
    }
}
