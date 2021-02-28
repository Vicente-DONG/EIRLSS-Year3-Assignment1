package Database_managers;

import Objects.*;
import java.sql.*;
import java.sql.Timestamp;
import java.util.ArrayList;

public class DatabaseManager {

    private final String url = "jdbc:derby://localhost:1527/assignmentDB";
    private Connection con = null;

    public boolean insertBooking(BookingDTO newBooking) {
        openConnection();

        PreparedStatement stmt;
        String sqlStr = "INSERT INTO Bookings(vehicleID, customerID, bookingStartDate, bookingEndDate, bookingPrice, satNavRequested, babySeatsRequested, wineCoolerRequested, bookingFinished, lateReturn) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int rowsCount;

        try {
            stmt = con.prepareStatement(sqlStr);
            stmt.setInt(1, newBooking.getBookedVehicle().getVehicleID());
            stmt.setInt(2, newBooking.getBookingCustomer().getCustomerID());
            stmt.setTimestamp(3, Timestamp.valueOf(newBooking.getBookingStartDay()));
            stmt.setTimestamp(4, Timestamp.valueOf(newBooking.getBookingEndDay()));
            stmt.setDouble(5, newBooking.getBookingPrice());
            stmt.setBoolean(6, newBooking.isSatNavRequested());
            stmt.setBoolean(7, newBooking.isBabySeatsRequested());
            stmt.setBoolean(8, newBooking.isWineCoolerRquested());
            stmt.setBoolean(9, newBooking.isBookingFinished());
            stmt.setBoolean(10, newBooking.isLateReturn());

            rowsCount = stmt.executeUpdate();
            System.out.println("Rows INSERTED into bookings: " + rowsCount);
            stmt.close();

            closeConnection();

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            closeConnection();

            return false;
        }
    }

    public boolean insertCustomer(CustomerDTO newCustomer) {
        openConnection();

        PreparedStatement stmt;
        String sqlStr = "INSERT INTO Customers(customerUsername, customerPassword, customerFirstName, customerLastName, customerDOB, customerAddress, blacklisted) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int rowsCount;

        try {
            stmt = con.prepareStatement(sqlStr);
            stmt.setString(1, newCustomer.getUsername());
            stmt.setString(2, newCustomer.getUserPassword());
            stmt.setString(3, newCustomer.getCustomerFirstName());
            stmt.setString(4, newCustomer.getCustomerLastName());
            stmt.setDate(5, java.sql.Date.valueOf(newCustomer.getCustomerDOB()));
            stmt.setString(6, newCustomer.getCustomerAddress());
            stmt.setBoolean(7, newCustomer.isBlacklisted());

            rowsCount = stmt.executeUpdate();
            System.out.println("Rows INSERTED in customers: " + rowsCount);
            stmt.close();

            closeConnection();

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            closeConnection();

            return false;
        }
    }

    public boolean updateCustomer(CustomerDTO newCustomer) {
        openConnection();

        PreparedStatement stmt;
        String sqlStr = "UPDATE Customers SET customerUsername = ?, customerPassword = ?, customerFirstName = ?, customerLastName = ?, customerDOB = ?, customerAddress = ?, blacklisted = ? WHERE CustomerID = ?";
        int rowsCount;

        try {
            stmt = con.prepareStatement(sqlStr);
            stmt.setString(1, newCustomer.getUsername());
            stmt.setString(2, newCustomer.getUserPassword());
            stmt.setString(3, newCustomer.getCustomerFirstName());
            stmt.setString(4, newCustomer.getCustomerLastName());
            stmt.setDate(5, java.sql.Date.valueOf(newCustomer.getCustomerDOB()));
            stmt.setString(6, newCustomer.getCustomerAddress());
            stmt.setBoolean(7, newCustomer.isBlacklisted());
            stmt.setInt(8, newCustomer.getCustomerID());

            rowsCount = stmt.executeUpdate();
            System.out.println("Rows UPDATED in customers: " + rowsCount);
            stmt.close();

            closeConnection();

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            closeConnection();

            return false;
        }
    }

    public boolean updateBooking(BookingDTO newBooking) {
        openConnection();

        PreparedStatement stmt;
        String sqlStr = "UPDATE Bookings SET vehicleID = ?, customerID = ?, bookingStartDate = ?, bookingEndDate = ?, bookingPrice = ?, satNavRequested = ?, babySeatsRequested = ?, wineCoolerRequested = ?, bookingFinished = ?, lateReturn = ? WHERE bookingID = ?";
        int rowsCount;
        try {
            stmt = con.prepareStatement(sqlStr);
            stmt.setInt(1, newBooking.getBookedVehicle().getVehicleID());
            stmt.setInt(2, newBooking.getBookingCustomer().getCustomerID());
            stmt.setTimestamp(3, Timestamp.valueOf(newBooking.getBookingStartDay()));
            stmt.setTimestamp(4, Timestamp.valueOf(newBooking.getBookingEndDay()));
            stmt.setDouble(5, newBooking.getBookingPrice());
            stmt.setBoolean(6, newBooking.isSatNavRequested());
            stmt.setBoolean(7, newBooking.isBabySeatsRequested());
            stmt.setBoolean(8, newBooking.isWineCoolerRquested());
            stmt.setBoolean(9, newBooking.isBookingFinished());
            stmt.setBoolean(10, newBooking.isLateReturn());
            stmt.setInt(11, newBooking.getBookingID());

            rowsCount = stmt.executeUpdate();
            System.out.println("Rows UPDATED in bookings: " + rowsCount);
            stmt.close();

            closeConnection();

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            closeConnection();

            return false;
        }
    }

    private CustomerDTO queryForCustomer(int CustomerID) {
        openConnection();

        PreparedStatement stmt;
        String sqlStr = "SELECT * FROM Customers WHERE CustomerID= ?";
        ResultSet rs;

        try {
            stmt = con.prepareStatement(sqlStr);
            stmt.setInt(1, CustomerID);
            rs = stmt.executeQuery();
            CustomerDTO c1 = null;
            while (rs.next()) {
                c1 = new CustomerDTO(rs.getInt("customerID"), rs.getString("customerFirstName"),
                        rs.getString("customerLastName"), rs.getDate("customerDOB").toLocalDate(),
                        rs.getString("customerAddress"), rs.getBoolean("blacklisted"),
                        rs.getString("customerUsername"), rs.getString("customerPassword"));
            }

            closeConnection();

            return c1;
        } catch (Exception e) {
            e.printStackTrace();

            closeConnection();

            return null;
        }
    }

    public CustomerDTO queryForCustomerWithLogIn(String username, String password) {
        openConnection();

        PreparedStatement stmt;
        String sqlStr = "SELECT * FROM Customers WHERE customerUsername = ? AND customerPassword = ?";
        ResultSet rs;

        try {
            stmt = con.prepareStatement(sqlStr);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            CustomerDTO c1 = null;
            while (rs.next()) {
                c1 = new CustomerDTO(rs.getInt("customerID"), rs.getString("customerFirstName"),
                        rs.getString("customerLastName"), rs.getDate("customerDOB").toLocalDate(),
                        rs.getString("customerAddress"), rs.getBoolean("blacklisted"),
                        rs.getString("customerUsername"), rs.getString("customerPassword"));
            }

            closeConnection();

            return c1;
        } catch (Exception e) {
            e.printStackTrace();

            closeConnection();

            return null;
        }
    }

    public AdminDTO queryForAdmin(String username, String password) {
        openConnection();

        PreparedStatement stmt;
        String sqlStr = "SELECT * FROM Admins WHERE adminUsername = ? AND adminPassword = ?";
        ResultSet rs;

        try {
            stmt = con.prepareStatement(sqlStr);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            AdminDTO a1 = null;
            while (rs.next()) {
                a1 = new AdminDTO(rs.getInt("adminID"), rs.getString("adminUsername"),
                        rs.getString("adminPassword"));
            }

            closeConnection();

            return a1;
        } catch (Exception e) {
            e.printStackTrace();

            closeConnection();

            return null;
        }
    }

    private VehicleDTO queryForVehicle(int VehicleID) {
        openConnection();

        PreparedStatement stmt;
        String sqlStr = "SELECT * FROM Vehicles WHERE VehicleID= ?";
        ResultSet rs;

        try {
            stmt = con.prepareStatement(sqlStr);
            stmt.setInt(1, VehicleID);
            rs = stmt.executeQuery();
            VehicleDTO v1 = null;
            while (rs.next()) {
                v1 = new VehicleDTO(rs.getInt("vehicleID"), rs.getString("vehicleName"), rs.getDouble("vehicleRentalPerDay"),
                        rs.getString("vehicleTransmissionType"), rs.getString("vehicleFuelType"));
            }

            closeConnection();

            return v1;
        } catch (Exception e) {
            e.printStackTrace();

            closeConnection();

            return null;
        }
    }

    public ArrayList<BookingDTO> queryAllBookings() {
        openConnection();

        PreparedStatement stmt;
        ResultSet rs;
        String sqlStr = "SELECT * FROM Bookings";
        ArrayList<BookingDTO> bookings = new ArrayList<>();

        try {
            stmt = con.prepareStatement(sqlStr);
            rs = stmt.executeQuery();
            while (rs.next()) {
                BookingDTO b1 = new BookingDTO(rs.getInt("bookingID"), queryForVehicle(rs.getInt("vehicleID")),
                        queryForCustomer(rs.getInt("customerID")), rs.getTimestamp("bookingStartDate").toLocalDateTime(),
                        rs.getTimestamp("bookingEndDate").toLocalDateTime(), rs.getDouble("bookingPrice"),
                        rs.getBoolean("satNavRequested"), rs.getBoolean("babySeatsRequested"),
                        rs.getBoolean("wineCoolerRequested"), rs.getBoolean("lateReturn"), rs.getBoolean("bookingFinished"));
                bookings.add(b1);
            }

            closeConnection();

            return bookings;
        } catch (Exception e) {
            e.printStackTrace();

            closeConnection();

            return null;
        }
    }

    public ArrayList<CustomerDTO> queryAllCustomers() {
        openConnection();

        PreparedStatement stmt;
        ResultSet rs;
        String sqlStr = "SELECT * FROM Customers";
        ArrayList<CustomerDTO> customers = new ArrayList<>();

        try {
            stmt = con.prepareStatement(sqlStr);
            rs = stmt.executeQuery();
            while (rs.next()) {
                CustomerDTO c1 = new CustomerDTO(rs.getInt("customerID"), rs.getString("customerFirstName"),
                        rs.getString("customerLastName"), rs.getDate("customerDOB").toLocalDate(),
                        rs.getString("customerAddress"), rs.getBoolean("blacklisted"),
                        rs.getString("customerUsername"), rs.getString("customerPassword"));
                customers.add(c1);
            }

            closeConnection();

            return customers;
        } catch (Exception e) {
            e.printStackTrace();

            closeConnection();

            return null;
        }
    }

    public boolean cancelBooking(int BookingID) {
        openConnection();

        PreparedStatement stmt;
        String sqlStr = "DELETE FROM Bookings WHERE BookingID = ?";

        try {
            stmt = con.prepareStatement(sqlStr);
            stmt.setInt(1, BookingID);
            stmt.executeUpdate();
            stmt.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            closeConnection();

            return false;
        }
    }

    public ArrayList<BookingDTO> queryForBookingsThroughCustomerID(int customerID) {
        openConnection();

        PreparedStatement stmt;
        ResultSet rs;
        String sqlStr = "SELECT * FROM Bookings WHERE CustomerID = ?";
        ArrayList<BookingDTO> bookings = new ArrayList<>();

        try {
            stmt = con.prepareStatement(sqlStr);
            stmt.setInt(1, customerID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                BookingDTO b1 = new BookingDTO(rs.getInt("bookingID"), queryForVehicle(rs.getInt("vehicleID")),
                        queryForCustomer(rs.getInt("customerID")), rs.getTimestamp("bookingStartDate").toLocalDateTime(),
                        rs.getTimestamp("bookingEndDate").toLocalDateTime(), rs.getDouble("bookingPrice"),
                        rs.getBoolean("satNavRequested"), rs.getBoolean("babySeatsRequested"),
                        rs.getBoolean("wineCoolerRequested"), rs.getBoolean("lateReturn"),
                        rs.getBoolean("bookingFinished"));
                bookings.add(b1);
            }

            return bookings;
        } catch (Exception e) {
            e.printStackTrace();

            closeConnection();

            return null;
        }
    }

    public ArrayList<BookingDTO> queryForBookingsThroughVehicleID(int vehicleID) {
        openConnection();

        PreparedStatement stmt;
        ResultSet rs;
        String sqlStr = "SELECT * FROM Bookings WHERE vehicleID = ?";
        ArrayList<BookingDTO> bookings = new ArrayList<>();

        try {
            stmt = con.prepareStatement(sqlStr);
            stmt.setInt(1, vehicleID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                BookingDTO b1 = new BookingDTO(rs.getInt("bookingID"), queryForVehicle(rs.getInt("vehicleID")),
                        queryForCustomer(rs.getInt("customerID")), rs.getTimestamp("bookingStartDate").toLocalDateTime(),
                        rs.getTimestamp("bookingEndDate").toLocalDateTime(), rs.getDouble("bookingPrice"),
                        rs.getBoolean("satNavRequested"), rs.getBoolean("babySeatsRequested"),
                        rs.getBoolean("wineCoolerRequested"), rs.getBoolean("lateReturn"),
                        rs.getBoolean("bookingFinished"));
                bookings.add(b1);
            }

            return bookings;
        } catch (Exception e) {
            e.printStackTrace();

            closeConnection();

            return null;
        }
    }

    public ArrayList<VehicleDTO> queryAllVehicles() {
        openConnection();

        PreparedStatement stmt;
        ResultSet rs;
        String sqlStr = "SELECT * FROM Vehicles";
        ArrayList<VehicleDTO> vehicles = new ArrayList<>();

        try {
            stmt = con.prepareStatement(sqlStr);
            rs = stmt.executeQuery();
            while (rs.next()) {
                VehicleDTO v1 = new VehicleDTO(rs.getInt("vehicleID"), rs.getString("vehicleName"),
                        rs.getDouble("vehicleRentalPerDay"), rs.getString("vehicleTransmissionType"),
                        rs.getString("vehicleFuelType"));
                vehicles.add(v1);
            }

            closeConnection();

            return vehicles;
        } catch (Exception e) {
            e.printStackTrace();

            closeConnection();

            return null;
        }
    }

    public boolean authenticateCustomer(String username, String password) {
        openConnection();

        PreparedStatement stmt;
        ResultSet rs;
        String sqlStr = "SELECT * FROM Customers WHERE customerUsername = ? AND customerPassword = ?";

        try {
            stmt = con.prepareStatement(sqlStr);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                rs.close();
                stmt.close();
                return true;
            } else {
                rs.close();
                stmt.close();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();

            closeConnection();

            return false;
        }
    }

    public boolean authenticateAdmin(String username, String password) {
        openConnection();

        PreparedStatement stmt;
        ResultSet rs;
        String sqlStr = "SELECT * FROM Admins WHERE adminUsername = ? AND adminPassword = ?";

        try {
            stmt = con.prepareStatement(sqlStr);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                rs.close();
                stmt.close();
                return true;
            } else {
                rs.close();
                stmt.close();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();

            closeConnection();

            return false;
        }
    }

    public boolean deleteBooking(int BookingID) {
        openConnection();

        PreparedStatement stmt;
        String sqlStr = "DELETE FROM Bookings WHERE BookingID = ?";
        int rowCount;

        try {
            stmt = con.prepareStatement(sqlStr);
            stmt.setInt(1, BookingID);
            rowCount = stmt.executeUpdate();
            System.out.println("Rows deleted: " + rowCount);
            stmt.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            closeConnection();

            return false;
        }
    }

    //Cleans up the database after testing to leave only the necessary dummy data
    public void cleanUpTests() {
        openConnection();

        PreparedStatement stmt;
        String sqlStr = "DELETE FROM BOOKINGS WHERE NOT BOOKINGID=1";
        PreparedStatement stmt1;
        String sqlStr1 = "DELETE FROM CUSTOMERS WHERE NOT CUSTOMERID=1 AND NOT CUSTOMERID=18";
        PreparedStatement stmt2;
        String sqlStr2 = "DELETE FROM VEHICLES WHERE"
                + " NOT VEHICLEID=1"
                + " AND NOT VEHICLEID=2"
                + " AND NOT VEHICLEID=3"
                + " AND NOT VEHICLEID=4"
                + " AND NOT VEHICLEID=5"
                + " AND NOT VEHICLEID=6"
                + " AND NOT VEHICLEID=7"
                + " AND NOT VEHICLEID=8";
        try {
            stmt = con.prepareStatement(sqlStr);
            stmt.executeUpdate();
            stmt1 = con.prepareStatement(sqlStr1);
            stmt1.executeUpdate();
            stmt2 = con.prepareStatement(sqlStr2);
            stmt2.executeUpdate();

            stmt.close();
            stmt1.close();
            stmt2.close();

            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();

            closeConnection();
        }
    }

    //Creates a connection to the database
    private void openConnection() {
        try {
            DriverManager.registerDriver(
                    new org.apache.derby.jdbc.ClientDriver());
            con = DriverManager.getConnection(url, "user1", "user1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Closes connection so not too many connections exist at once 
    private void closeConnection() {
        try {
            con = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
