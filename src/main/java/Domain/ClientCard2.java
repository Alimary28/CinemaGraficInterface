package Domain;

import java.time.LocalDate;

public class ClientCard2 extends Entity{
    private String name, firstname, cnp;
    LocalDate birthDate, registrationDate;
    private int points;

    public ClientCard2(String id, String name, String firstname, String cnp, LocalDate birthDate, LocalDate registrationDate, int points) {
        super(id);
        this.name = name;
        this.firstname = firstname;
        this.cnp = cnp;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
        this.points = points;
    }

    @Override
    public String toString() {
        return "ClientCard{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", firstname='" + firstname + '\'' +
                ", cnp='" + cnp + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                ", points='" + points + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getFirstname(){
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getPoints(){
        return points;
    }

    public  void setPoints(int points){
        this.points = points;
    }
}
