package main.java.com.ipsos.andreas.asignari.model;

/**
 * Created by Andreas on 6/14/16.
 */
public class Employee {

    private String firstName;
    private String lastName;
    private String position;
    private String username;
    private String password;

    public Employee() {}
    public Employee(String firstName,String lastName,String position,String username,String password) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.position=position;
        this.username=username;
        this.password=password;
    }

    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getPosition() {return position;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}

}
