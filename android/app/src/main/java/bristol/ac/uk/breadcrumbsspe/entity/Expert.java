package bristol.ac.uk.breadcrumbsspe.entity;

public class Expert {
    private String firstName;
    private String lastName;

    public Expert(String f, String l){
        firstName = f;
        lastName = l;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
