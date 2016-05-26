package test.struts2.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Action作用域拦截器
 */
public class ActionScopeInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = -7548155469339415406L;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        return invocation.invoke();
    }

}
