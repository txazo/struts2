package test.struts2.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import test.struts2.service.AutowireService;

/**
 * Spring的@Autowired自动注入
 * <p/>
 * struts2-spring-plugin插件struts-plugin.xml
 *
 * @see com.opensymphony.xwork2.spring.SpringObjectFactory
 */
@ParentPackage("struts-default")
@Namespace("/")
public class SpringAutowireAction extends ActionSupport {

    private static final long serialVersionUID = 2185456026503671701L;

    @Autowired
    private AutowireService autowireService;

    @Action(value = "autowire", results = {@Result(name = SUCCESS, location = "/index.jsp")})
    public String autowire() {
        return SUCCESS;
    }

}
