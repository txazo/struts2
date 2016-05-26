package test.struts2.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;

@ParentPackage("test")
@Namespace("/")
public class LoadConfigAction extends ActionSupport {

    private static final long serialVersionUID = -7648400326518053995L;

    @Action(value = "loadConfig",
            results = {@Result(name = "success", location = "/index.jsp")},
            interceptorRefs = {@InterceptorRef(value = "actionScope")},
            exceptionMappings = {@ExceptionMapping(result = "exception", exception = "test.struts2.exception.InterceptorException")})
    public String loadConfig() {
        return "success";
    }

}
