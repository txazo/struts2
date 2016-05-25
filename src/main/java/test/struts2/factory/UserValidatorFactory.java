package test.struts2.factory;

import com.opensymphony.xwork2.inject.Context;
import com.opensymphony.xwork2.inject.Factory;
import test.struts2.entity.UserValidator;

/**
 * 自定义对象工厂
 */
public class UserValidatorFactory implements Factory {

    @Override
    public UserValidator create(Context context) throws Exception {
        return new UserValidator();
    }

}
