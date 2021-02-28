
package Objects;

public class AdminDTO extends User{
    private int adminID;
    
    //For database use
    public AdminDTO(int adminID, String username, String userPassword) {
        super(username, userPassword);
        this.adminID = adminID;
    }

    //For environment use
    public AdminDTO(String username, String userPassword) {
        super(username, userPassword);
    }

    public int getAdminID() {
        return adminID;
    }
    
    @Override
    public boolean equals(Object o) {
        if (getClass() == o.getClass()) {
            return true;
        } else {
            return false;
        }
    }
    
    //No need to set adminID because database already does that for you

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.adminID;
        return hash;
    }
}
