package bristol.ac.uk.breadcrumbsspe;

import android.icu.lang.UScript;

import bristol.ac.uk.breadcrumbsspe.entity.User;

public class UserInSession {
    private static User user = null;
    private static UserInSession userInSession;

    public static UserInSession getInstance(User u){
        if(user == null){
            userInSession = new UserInSession(u);
        }
        return userInSession;
    }

    public static UserInSession getInstance(){
        return userInSession;
    }

    private UserInSession(User u) {
        user = u;
    }

    public static User getUser() {
        return user;
    }
}
