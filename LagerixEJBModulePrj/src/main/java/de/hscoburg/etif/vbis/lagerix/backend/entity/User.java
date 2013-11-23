package de.hscoburg.etif.vbis.lagerix.backend.entity;
  
import de.hscoburg.etif.vbis.lagerix.backend.util.SHA512;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
 
import javax.persistence.Cacheable;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;
 


import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.UserDTO;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
 /**
  * 
  * @author zuch1000
  */
@Entity
@Table(name="USERS")
@Cacheable(false)
public class User implements Serializable {
          
    @Id
    @Column(length=128)
    private String email;
  
    @Column(nullable=false, length=128)
    private String firstName;
      
    @Column(nullable=false, length=128)
    private String lastName;
      
    /**
     * A sha512 is 512 bits long -- as its name indicates. If you are using an hexadecimal representation, 
     * each digit codes for 4 bits ; so you need 512 : 4 = 128 digits to represent 512 bits -- so, you need a varchar(128), 
     * or a char(128), as the length is always the same, not varying at all.
     */
    @Column(nullable=false, length=128) //sha-512 + hex
    private String password;
      
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable=false)
    private Date registeredOn;
          
    //@ElementCollection(targetClass = Group.class)
    //@CollectionTable(name = "USERS_GROUPS", 
    //                joinColumns       = @JoinColumn(name = "email", nullable=false), 
    //                uniqueConstraints = { @UniqueConstraint(columnNames={"email","groupname"}) } )
    //@Enumerated(EnumType.STRING)
    //@Column(name="groupname", length=64, nullable=false)
    
    @OneToMany(targetEntity = Groups.class,mappedBy = "user",cascade = CascadeType.ALL)
    private List<Groups> groups;
     
   
     
    
 
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    public String getEmail() {
        return email;
    }
  
    public void setEmail(String email) {
        this.email = email;
    }
  
    /**
     * @return the password in SHA512 HEX representation
     */
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public Date getRegisteredOn() {
        return registeredOn;
    }
 
    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }
 
    public List<Groups> getGroups() {
        return groups;
    }
 
    public void addGroup(Groups group) {
        group.setUser(this);
        if(groups == null)
        {
            groups = new ArrayList<Groups>();
        }
        groups.add(group);
    }
    
    
    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }
 
    @Override
    public String toString() {
        return "User [email=" + email + ", firstName=" + firstName
                + ", lastName=" + lastName + ", password=" + password
                + ", registeredOn=" + registeredOn + ", groups=" + groups + "]";
    }
}