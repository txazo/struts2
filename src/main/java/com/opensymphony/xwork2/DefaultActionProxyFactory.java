/*
 * Copyright 2002-2007,2009 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.opensymphony.xwork2;

import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;

import java.util.Map;


/**
 * Default factory for {@link com.opensymphony.xwork2.ActionProxyFactory}.
 *
 * @author Jason Carreira
 */
public class DefaultActionProxyFactory implements ActionProxyFactory {

    protected Container container;
    
    public DefaultActionProxyFactory() {
        super();
    }
    
    @Inject
    public void setContainer(Container container) {
        this.container = container;
    }
    
    public ActionProxy createActionProxy(String namespace, String actionName, Map<String, Object> extraContext) {
        return createActionProxy(namespace, actionName, null, extraContext, true, true);
    }

    public ActionProxy createActionProxy(String namespace, String actionName, String methodName, Map<String, Object> extraContext) {
        return createActionProxy(namespace, actionName, methodName, extraContext, true, true);
    }

    public ActionProxy createActionProxy(String namespace, String actionName, Map<String, Object> extraContext, boolean executeResult, boolean cleanupContext) {
        return createActionProxy(namespace, actionName, null, extraContext, executeResult, cleanupContext);
    }

    public ActionProxy createActionProxy(String namespace, String actionName, String methodName, Map<String, Object> extraContext, boolean executeResult, boolean cleanupContext) {

        // 源码解析: 创建ActionInvocation
        ActionInvocation inv = createActionInvocation(extraContext, true);

        // 源码解析: ActionInvocation依赖注入
        container.inject(inv);

        // 源码解析: 创建ActionProxy并返回
        return createActionProxy(inv, namespace, actionName, methodName, executeResult, cleanupContext);
    }
    
    protected ActionInvocation createActionInvocation(Map<String, Object> extraContext, boolean pushAction) {
        return new DefaultActionInvocation(extraContext, pushAction);
    }
    
    public ActionProxy createActionProxy(ActionInvocation inv, String namespace, String actionName, boolean executeResult, boolean cleanupContext) {
        
        return createActionProxy(inv, namespace, actionName, null, executeResult, cleanupContext);
    }

    public ActionProxy createActionProxy(ActionInvocation inv, String namespace, String actionName, String methodName, boolean executeResult, boolean cleanupContext) {

        DefaultActionProxy proxy = new DefaultActionProxy(inv, namespace, actionName, methodName, executeResult, cleanupContext);

        // 源码解析: DefaultActionProxy依赖注入
        container.inject(proxy);

        /**
         * 源码解析: ActionProxy的准备操作
         *
         * 1) 根据namespace和actionName从Configuration中查找ActionConfig
         */
        proxy.prepare();
        return proxy;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
