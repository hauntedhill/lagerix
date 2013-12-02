package de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto;
 
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.BaseDTO;
import java.util.ArrayList;
import java.util.List;

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
    
    private List<GroupDTO> groups;

    public UserDTO() {
        groups = new ArrayList<GroupDTO>();
    }
     
    
    
    
    
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
        return "User [email=" + getEmail() + ", fName=" + getFname()
                + ", lName=" + getLname() + ", password1=" + getPassword1() +", password2=" + getPassword2() + "]";
    }

    /**
     * @return the groups
     */
    public List<GroupDTO> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(List<GroupDTO> groups) {
        this.groups = groups;
    }
     
}