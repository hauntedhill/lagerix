package de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto;
 
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.BaseDTO;

/**
 * 
 * @author zuch1000
 */
public class UserDTO extends BaseDTO{
 
    private String email;
    private String fname;
    private String lname;
    private String password1;
    private String password2;
     
    public String getFname() {
        return fname;
    }
     
    public void setFname(String firstName) {
        this.fname = firstName;
    }
  
    public String getLname() {
        return lname;
    }
  
    public void setLname(String lastName) {
        this.lname = lastName;
    }
  
    public String getEmail() {
        return email;
    } 
 
    public void setEmail(String email) {
        this.email = email;
    }
  
    public String getPassword1() {
        return password1;
    }
     
    public void setPassword1(String password) {
        this.password1 = password;
    }
     
    public String getPassword2() {
        return password2;
    }
     
    public void setPassword2(String password) {
        this.password2 = password;
    }
      
    @Override
    public String toString() {
        return "User [email=" + email + ", fName=" + fname
                + ", lName=" + lname + ", password1=" + password1 +", password2=" + password2 + "]";
    }
     
}