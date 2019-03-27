package bristol.ac.uk.breadcrumbsspe.entity;

public class Attempt {
    private User user;
    private Choice choice;
    public Attempt(User u, Choice c){
        this.user = u;
        this.choice = c;
    }

    public User getUser() {
        return user;
    }

    public Choice getChoice() {
        return choice;
    }
}
