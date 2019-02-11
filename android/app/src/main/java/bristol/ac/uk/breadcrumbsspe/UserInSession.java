package bristol.ac.uk.breadcrumbsspe;

import bristol.ac.uk.breadcrumbsspe.entity.User;

public class UserInSession {
    private static User user = null;

    public static User getInstance() {
        return user;
    }

    public UserInSession(User u) {
        if(user == null) user = u;
    }
}
