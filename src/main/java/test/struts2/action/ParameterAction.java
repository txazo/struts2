package test.struts2.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

@ParentPackage("struts-default")
@Namespace("/")
public class ParameterAction extends ActionSupport {

    private static final long serialVersionUID = -7766270740088598857L;

    private String name;

    // http://localhost:8080/parameter?name=root
    @Action(value = "parameter", results = {@Result(name = SUCCESS, location = "/parameter.jsp")})
    public String parameter() {
        return SUCCESS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
