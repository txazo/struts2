package test.struts2.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ResultInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = -7548155469339415406L;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ActionContext actionContext = invocation.getInvocationContext();
        invocation.getInvocationContext().put("resultType", actionContext.get(ActionContext.ACTION_NAME));
        return invocation.invoke();
    }

}
