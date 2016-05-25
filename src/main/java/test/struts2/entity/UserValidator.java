package test.struts2.entity;

import java.util.Arrays;
import java.util.List;

public class UserValidator {

    private static final List<String> PERMISSION_USERS = Arrays.asList(new String[]{"root", "admin", "manager"});

    public boolean isValidateUser(String user) {
        return PERMISSION_USERS.contains(user);
    }

}
