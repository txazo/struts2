package test.struts2.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;

import java.io.InputStream;

@ParentPackage("test")
@Namespace("/result")
@InterceptorRefs({
        @InterceptorRef(value = "defaultStack"),
        @InterceptorRef(value = "result")})
public class ResultAction extends ActionSupport {

    private static final long serialVersionUID = 7800644785992711779L;

    private String name;

    /**
     * 请求传递给另一个action, 保留请求参数和值栈
     *
     * @see com.opensymphony.xwork2.ActionChainResult
     */
    @Action(value = "chain", results = {
            @Result(name = SUCCESS, type = "chain", location = "chainToAction", params = {"namespace", "/result"})})
    public String chain() {
        ServletActionContext.getRequest().setAttribute("resultType", "chain");
        return SUCCESS;
    }

    @Action(value = "chainToAction", results = {
            @Result(name = SUCCESS, location = "result-chain.jsp")})
    public String chainToAction() {
        return SUCCESS;
    }

    /**
     * 默认结果类型
     *
     * @see org.apache.struts2.result.ServletDispatcherResult
     */
    @Action(value = "dispatcher", results = {
            @Result(name = SUCCESS, type = "dispatcher", location = "result-dispatcher.jsp")})
    public String dispatcher() {
        return SUCCESS;
    }

    /**
     * FreeMarker视图渲染
     *
     * @see org.apache.struts2.views.freemarker.FreemarkerResult
     */
    @Action(value = "freemarker", results = {
            @Result(name = SUCCESS, type = "freemarker", location = "result-freemarker.ftl")})
    public String freemarker() {
        return SUCCESS;
    }

    /**
     * 返回Http响应头
     *
     * @see org.apache.struts2.result.HttpHeaderResult
     */
    @Action(value = "httpheader", results = {
            @Result(name = SUCCESS, type = "httpheader", params = {"status", "404"})})
    public String httpheader() {
        return SUCCESS;
    }

    /**
     * 重定向到新的url, 请求参数和值栈丢失
     *
     * @see org.apache.struts2.result.ServletRedirectResult
     */
    @Action(value = "redirect", results = {
            @Result(name = SUCCESS, type = "redirect", location = "/result-redirect.html")})
    public String redirect() {
        return SUCCESS;
    }

    /**
     * 重定向到新的action, 请求参数和值栈丢失
     *
     * @see org.apache.struts2.result.ServletActionRedirectResult
     */
    @Action(value = "redirectAction", results = {
            @Result(name = SUCCESS, type = "redirectAction", location = "redirectActionToAction", params = {"namespace", "/result"})})
    public String redirectAction() {
        return SUCCESS;
    }

    @Action(value = "redirectActionToAction", results = {
            @Result(name = SUCCESS, location = "result-redirectAction.jsp")})
    public String redirectActionToAction() {
        return SUCCESS;
    }

    /**
     * 返回流, 用于下载文件
     *
     * @see org.apache.struts2.result.StreamResult
     */
    @Action(value = "stream", results = {
            @Result(name = SUCCESS, type = "stream", params = {
                    "inputName", "imageStream",
                    "contentType", "image/jpeg",
                    "contentDisposition", "attachment;filename=result-stream.jpg",
                    "bufferSize", "1024"})})
    public String stream() {
        return SUCCESS;
    }

    public InputStream getImageStream() {
        return getClass().getResourceAsStream("/result-stream.jpg");
    }

    /**
     * Velocity视图渲染
     *
     * @see org.apache.struts2.result.VelocityResult
     */
    @Action(value = "velocity", results = {
            @Result(name = SUCCESS, type = "velocity", location = "result-velocity.vm")})
    public String velocity() {
        return SUCCESS;
    }

    /**
     * @see org.apache.struts2.views.xslt.XSLTResult
     */
    @Action(value = "xslt", results = {
            @Result(name = SUCCESS, type = "xslt", location = "WEB-INF/pages/result/result-xslt.xsl", params = {"exposedValue", "name"})})
    public String xslt() {
        return SUCCESS;
    }

    /**
     * 返回普通文本
     *
     * @see org.apache.struts2.result.PlainTextResult
     */
    @Action(value = "plainText", results = {
            @Result(name = SUCCESS, type = "plainText", location = "WEB-INF/pages/result/result-plainText.jsp", params = {"charSet", "UTF-8"})})
    public String plainText() {
        return SUCCESS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
