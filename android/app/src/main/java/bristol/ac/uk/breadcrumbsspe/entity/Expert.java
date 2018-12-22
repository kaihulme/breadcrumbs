package bristol.ac.uk.breadcrumbsspe.entity;


import java.util.ArrayList;
import java.util.List;

public class Expert {
    private int id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Quiz> quizzes = new ArrayList<>();

    public Expert(String firstName,String lastName,String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email; //can use regex to pattern match
        this.password = password; // can also use regex to pattern match
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return firstName + ' ' + lastName + ' '+  email;
    }
}
