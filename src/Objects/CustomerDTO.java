
package Objects;

import Objects.VisitorPattern.Visitable;
import Objects.VisitorPattern.Visitor;
import java.time.LocalDate;
import java.util.Objects;

public class CustomerDTO extends User implements Visitable{
    private int customerID;
    private String customerFirstName;
    private String customerLastName;
    private LocalDate customerDOB;
    private String customerAddress;
    private boolean blacklisted;
    private final Visitor visitor = new Visitor();
    
    //For database use
    public CustomerDTO(int customerID, String customerFirstName, String customerLastName, LocalDate customerDOB, String customerAddress, boolean blacklisted, String username, String userPassword) {
        super(username, userPassword);
        this.customerID = customerID;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerDOB = customerDOB;
        this.customerAddress = customerAddress;
        this.blacklisted = blacklisted;
    }
    
    //For environment use
    public CustomerDTO(String customerFirstName, String customerLastName, LocalDate customerDOB, String customerAddress, boolean blacklisted, String username, String userPassword) {
        super(username, userPassword);
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerDOB = customerDOB;
        this.customerAddress = customerAddress;
        this.blacklisted = blacklisted;
    }
    
    @Override
    public String toString()
    {
        return "CustomerID = " + customerID + "\n" +
               "Customer first name = " + customerFirstName + "\n" +
               "Customer last name = " + customerLastName + "\n" +
               "Customer DOB = " + customerDOB.toString() + "\n" +
               "Customer Address = " + customerAddress + "\n" +
               "Are they blacklisted? = " + acceptBooleanVisitor(visitor, blacklisted);
    }
    
    //Standard getters and setters
    public int getCustomerID() {
        return customerID;
    }
    
    //No need to set customerID because database takes care of that

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public LocalDate getCustomerDOB() {
        return customerDOB;
    }

    public void setCustomerDOB(LocalDate customerDOB) {
        this.customerDOB = customerDOB;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public boolean isBlacklisted() {
        return blacklisted;
    }

    public void setBlacklisted(boolean blacklisted) {
        this.blacklisted = blacklisted;
    }
    
    //Overriding equals and hashCode for testing
   @Override
    public boolean equals(Object o){
    if(getClass() == o.getClass()){
        return true;
    }
    else{
        return false;}
}

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.customerID;
        hash = 79 * hash + Objects.hashCode(this.customerFirstName);
        hash = 79 * hash + Objects.hashCode(this.customerLastName);
        hash = 79 * hash + Objects.hashCode(this.customerDOB);
        hash = 79 * hash + Objects.hashCode(this.customerAddress);
        hash = 79 * hash + (this.blacklisted ? 1 : 0);
        return hash;
    }
    
    //To make booleans more readable
    @Override
    public String acceptBooleanVisitor(Visitor v, boolean b) {
        return v.visitBoolean(b);
    }
}
