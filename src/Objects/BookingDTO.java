package Objects;

import Objects.VisitorPattern.Visitable;
import Objects.VisitorPattern.Visitor;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class BookingDTO implements Visitable{

    private int bookingID;
    private VehicleDTO bookedVehicle;
    private CustomerDTO bookingCustomer;
    private LocalDateTime bookingStartDay;
    private LocalDateTime bookingEndDay;
    private double bookingPrice;
    private boolean satNavRequested;
    private boolean babySeatsRequested;
    private boolean wineCoolerRquested;
    private boolean lateReturn;
    private boolean bookingFinished;
    private final DecimalFormat df = new DecimalFormat("#,##");
    private final Visitor visitor = new Visitor();

    //For database use

    public BookingDTO(int bookingID, VehicleDTO bookedVehicle, CustomerDTO bookingCustomer, LocalDateTime bookingStartDay, LocalDateTime bookingEndDay, double bookingPrice, boolean satNavRequested, boolean babySeatsRequested, boolean wineCoolerRquested, boolean lateReturn, boolean bookingFinished) {
        this.bookingID = bookingID;
        this.bookedVehicle = bookedVehicle;
        this.bookingCustomer = bookingCustomer;
        this.bookingStartDay = bookingStartDay;
        this.bookingEndDay = bookingEndDay;
        this.bookingPrice = bookingPrice;
        this.satNavRequested = satNavRequested;
        this.babySeatsRequested = babySeatsRequested;
        this.wineCoolerRquested = wineCoolerRquested;
        this.lateReturn = lateReturn;
        this.bookingFinished = bookingFinished;
    }

    //For environment use
    public BookingDTO(VehicleDTO bookedVehicle, CustomerDTO bookingCustomer, LocalDateTime bookingStartDay, LocalDateTime bookingEndDay, double bookingPrice, boolean satNavRequested, boolean babySeatsRequested, boolean wineCoolerRquested, boolean lateReturn, boolean bookingFinished) {
        this.bookedVehicle = bookedVehicle;
        this.bookingCustomer = bookingCustomer;
        this.bookingStartDay = bookingStartDay;
        this.bookingEndDay = bookingEndDay;
        this.bookingPrice = bookingPrice;
        this.satNavRequested = satNavRequested;
        this.babySeatsRequested = babySeatsRequested;
        this.wineCoolerRquested = wineCoolerRquested;
        this.lateReturn = lateReturn;
        this.bookingFinished = bookingFinished;
    }
    

    @Override
    public String toString() {
        return "Booking ID = " + bookingID + "\n"
                + "Vehicle ID = " + bookedVehicle.getVehicleID() + "\n"
                + "Customer ID = " + bookingCustomer.getCustomerID() + "\n"
                + "Start time of booking = " + bookingStartDay.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "\n"
                + "End time of booking = " + bookingEndDay.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "\n"
                + "Price of booking = " + df.format(bookingPrice) + "£\n"
                + "Have they requested SatNav = " + acceptBooleanVisitor(visitor, satNavRequested) + "\n"
                + "Have they requested baby seats = " + acceptBooleanVisitor(visitor, babySeatsRequested) + "\n"
                + "Have they requested wine cooler = " + acceptBooleanVisitor(visitor, wineCoolerRquested) + "\n"
                + "Is the booking finished = " + acceptBooleanVisitor(visitor, bookingFinished);
    }
    
    public String forCustomerToString() {
        return "Booking ID = " + bookingID + "\n"
                + "Vehicle ID = " + bookedVehicle.getVehicleID() + "\n"
                + "Customer ID = " + bookingCustomer.getCustomerID() + "\n"
                + "Start time of booking = " + bookingStartDay.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "\n"
                + "End time of booking = " + bookingEndDay.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "\n"
                + "Price of booking = " + (bookingPrice) + "£\n"
                + "SatNav requested? " + acceptBooleanVisitor(visitor, satNavRequested) + "\n"
                + "Baby seats requested? " + acceptBooleanVisitor(visitor, babySeatsRequested) + "\n"
                + "Wine cooler requested? " + acceptBooleanVisitor(visitor, wineCoolerRquested) + "\n"
                + "Is the booking ongoing? " + acceptBooleanVisitor(visitor, bookingFinished);
    }

    public int getBookingID() {
        return bookingID;
    }

    //No need to set ID since database already does that
    public VehicleDTO getBookedVehicle() {
        return bookedVehicle;
    }

    public void setBookedVehicle(VehicleDTO bookedVehicle) {
        this.bookedVehicle = bookedVehicle;
    }

    public CustomerDTO getBookingCustomer() {
        return bookingCustomer;
    }

    public void setBookingCustomer(CustomerDTO bookingCustomer) {
        this.bookingCustomer = bookingCustomer;
    }

    public LocalDateTime getBookingStartDay() {
        return bookingStartDay;
    }

    public void setBookingStartDay(LocalDateTime bookingStartDay) {
        this.bookingStartDay = bookingStartDay;
    }

    public LocalDateTime getBookingEndDay() {
        return bookingEndDay;
    }

    public void setBookingEndDay(LocalDateTime bookingEndDay) {
        this.bookingEndDay = bookingEndDay;
    }

    public double getBookingPrice() {
        return bookingPrice;
    }

    public void setBookingPrice(double bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public boolean isSatNavRequested() {
        return satNavRequested;
    }

    public void setSatNavRequested(boolean satNavRequested) {
        this.satNavRequested = satNavRequested;
    }

    public boolean isBabySeatsRequested() {
        return babySeatsRequested;
    }

    public void setBabySeatsRequested(boolean babySeatsRequested) {
        this.babySeatsRequested = babySeatsRequested;
    }

    public boolean isWineCoolerRquested() {
        return wineCoolerRquested;
    }

    public void setWineCoolerRquested(boolean wineCoolerRquested) {
        this.wineCoolerRquested = wineCoolerRquested;
    }

    public boolean isLateReturn() {
        return lateReturn;
    }

    public void setLateReturn(boolean lateReturn) {
        this.lateReturn = lateReturn;
    }

    public boolean isBookingFinished() {
        return bookingFinished;
    }

    public void setBookingFinished(boolean bookingFinished) {
        this.bookingFinished = bookingFinished;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + this.bookingID;
        hash = 31 * hash + Objects.hashCode(this.bookedVehicle);
        hash = 31 * hash + Objects.hashCode(this.bookingCustomer);
        hash = 31 * hash + Objects.hashCode(this.bookingStartDay);
        hash = 31 * hash + Objects.hashCode(this.bookingEndDay);
        hash = 31 * hash + (int) (Double.doubleToLongBits(this.bookingPrice) ^ (Double.doubleToLongBits(this.bookingPrice) >>> 32));
        hash = 31 * hash + (this.satNavRequested ? 1 : 0);
        hash = 31 * hash + (this.babySeatsRequested ? 1 : 0);
        hash = 31 * hash + (this.wineCoolerRquested ? 1 : 0);
        hash = 31 * hash + (this.bookingFinished ? 1 : 0);
        return hash;
    }

    //To make booleans more readable
    @Override
    public String acceptBooleanVisitor(Visitor v, boolean b) {
        return v.visitBoolean(b);
    }

}
