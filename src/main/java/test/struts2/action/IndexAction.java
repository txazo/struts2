package test.struts2.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

@ParentPackage("struts-default")
@Namespace("/")
public class IndexAction extends ActionSupport {

    private static final long serialVersionUID = 7800644785992711779L;

    @Action(value = "index", results = {@Result(name = SUCCESS, location = "/index.jsp")})
    public String index() {
        return SUCCESS;
    }

}
