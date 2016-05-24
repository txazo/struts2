package test.struts2.action;

import org.apache.struts2.convention.annotation.*;
import test.struts2.exception.InterceptorException;

@ParentPackage("struts-default")
@Namespace("/")
public class ExceptionMappingInterceptorAction {

    @Action(value = "exception",
            results = {
                    @Result(name = "success", location = "/index.jsp"),
                    @Result(name = "exception", location = "/exception.jsp")},
            exceptionMappings = {
                    @ExceptionMapping(result = "exception", exception = "test.struts2.exception.InterceptorException")})
    public String exception() {
        execute();
        return "success";
    }

    private void execute() {
        throw new InterceptorException();
    }

}
