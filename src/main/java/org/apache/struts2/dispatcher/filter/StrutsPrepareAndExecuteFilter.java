/*
 * $Id: DefaultActionSupport.java 651946 2008-04-27 13:41:38Z apetrelli $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.struts2.dispatcher.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.RequestUtils;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.ExecuteOperations;
import org.apache.struts2.dispatcher.InitOperations;
import org.apache.struts2.dispatcher.PrepareOperations;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Handles both the preparation and execution phases of the Struts dispatching process.  This filter is better to use
 * when you don't have another filter that needs access to action context information, such as Sitemesh.
 */

/**
 * 源码解析: Struts2核心过滤器
 *
 * 注: 如果有其它的过滤器要访问action上下文信息, 比如Sitemesh, 使用StrutsPrepareFilter和StrutsExecuteFilter代替StrutsPrepareAndExecuteFilter
 */
public class StrutsPrepareAndExecuteFilter implements StrutsStatics, Filter {

    private static final Logger LOG = LogManager.getLogger(StrutsPrepareAndExecuteFilter.class);

    // 源码解析: 负责请求的准备操作
    protected PrepareOperations prepare;

    // 源码解析: 负责请求的执行操作
    protected ExecuteOperations execute;

    // 源码解析: 过滤的请求
    protected List<Pattern> excludedPatterns = null;

    // 源码解析: 过滤器初始化
    public void init(FilterConfig filterConfig) throws ServletException {
        // 源码解析: 负责过滤器的初始化操作
        InitOperations init = new InitOperations();
        Dispatcher dispatcher = null;
        try {
            // 源码解析: 封装FilterConfig
            FilterHostConfig config = new FilterHostConfig(filterConfig);

            // 源码解析: 初始化Struts2内部日志, 2.5版本之后弃用, 改用Log4j 2日志
            init.initLogging(config);

            // 源码解析: 创建并初始化Dispatcher
            dispatcher = init.initDispatcher(config);

            // 源码解析: 初始化静态资源加载器
            init.initStaticContentLoader(config, dispatcher);

            prepare = new PrepareOperations(dispatcher);
            execute = new ExecuteOperations(dispatcher);

            // 源码解析: 初始化过滤的请求
            this.excludedPatterns = init.buildExcludedPatternsList(dispatcher);

            // 源码解析: 初始化回调, 空方法
            postInit(dispatcher, filterConfig);
        } finally {
            if (dispatcher != null) {
                // 源码解析: Dispatcher初始化后clean, 清除ThreadLocal中的容器
                dispatcher.cleanUpAfterInit();
            }
            // 源码解析: 清除ThreadLocal的ActionContext
            init.cleanup();
        }
    }

    /**
     * Callback for post initialization
     *
     * @param dispatcher the dispatcher
     * @param filterConfig the filter config
     */
    protected void postInit(Dispatcher dispatcher, FilterConfig filterConfig) {
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        try {
            // 源码解析: 解析请求的uri
            String uri = RequestUtils.getUri(request);

            // 源码解析: 判断是否过滤的请求
            if (excludedPatterns != null && prepare.isUrlExcluded(request, excludedPatterns)) {
                LOG.trace("Request {} is excluded from handling by Struts, passing request to other filters", uri);

                // 源码解析: 被过滤的请求, 不进入Struts2的执行流程, 传递给下一个过滤器处理
                chain.doFilter(request, response);
            } else {
                LOG.trace("Checking if {} is a static resource", uri);

                // 源码解析: 进入Struts2的执行流程

                /**
                 * 源码解析: 检查是否静态资源请求, 请求以/struts/或/static/开头, 静态资源在classpath目录下
                 *
                 * 默认开启静态资源拦截, 可设置struts.serve.static=false禁用
                 * 可以在核心过滤器的初始化参数中添加packages配置, 添加额外的静态资源目录, 默认为以下四个目录
                 *
                 * static/
                 * template/
                 * org/apache/struts2/static/
                 * org/apache/struts2/interceptor/debugging/
                 *
                 * 例如
                 * /struts/struts2.properties, static/目录
                 * /struts/utils.js, template/目录
                 * /struts/simple/a.ftl, org/apache/struts2/static/目录
                 * /struts/webconsole.js, org/apache/struts2/interceptor/debugging/目录
                 */
                boolean handled = execute.executeStaticResourceRequest(request, response);
                if (!handled) {
                    LOG.trace("Assuming uri {} as a normal action", uri);

                    // 源码解析: 设置Response的编码和国际化
                    prepare.setEncodingAndLocale(request, response);

                    // 源码解析: 创建action上下文
                    prepare.createActionContext(request, response);

                    // 源码解析: Dispatcher实例添加到ThreadLocal
                    prepare.assignDispatcherToThread();

                    // 源码解析: 封装请求Request
                    request = prepare.wrapRequest(request);

                    // 源码解析: 查找ActionMapping
                    ActionMapping mapping = prepare.findActionMapping(request, response, true);
                    if (mapping == null) {
                        LOG.trace("Cannot find mapping for {}, passing to other filters", uri);

                        // 源码解析: action映射不存在, 请求传递给下一个过滤链
                        chain.doFilter(request, response);
                    } else {
                        LOG.trace("Found mapping {} for {}", mapping, uri);

                        // 源码解析: 执行action, action执行入口
                        execute.executeAction(request, response, mapping);
                    }
                }
            }
        } finally {
            /**
             * 源码解析: 请求处理完成后的清理工作
             *
             * 清除ThreadLocal中的容器, action上下文, Dispatcher
             * 上传的临时文件清除
             */
            prepare.cleanupRequest(request);
        }
    }

    public void destroy() {
        prepare.cleanupDispatcher();
    }

}
