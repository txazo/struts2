package test.struts2.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import test.struts2.entity.UserValidator;

@ParentPackage("struts-default")
@Namespace("/")
public class InjectAction extends ActionSupport {

    private static final long serialVersionUID = -5911391492256052566L;

    private String user;

    @Inject(value = "defaultUser")
    private String defaultUser;

    @Inject
    private UserValidator userValidator;

    @Action(value = "inject", results = {@Result(name = SUCCESS, location = "/inject.jsp")})
    public String inject() {
        if (!userValidator.isValidateUser(user)) {
            user = defaultUser;
        }
        return SUCCESS;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
