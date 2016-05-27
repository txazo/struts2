package test.struts2.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;

import javax.annotation.Resources;
import java.io.InputStream;

@ParentPackage("test")
@Namespace("/result")
@InterceptorRefs({
        @InterceptorRef(value = "defaultStack"),
        @InterceptorRef(value = "result")})
public class ResultAction extends ActionSupport {

    private static final long serialVersionUID = 7800644785992711779L;

    /**
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
     * @see org.apache.struts2.result.ServletDispatcherResult
     */
    @Action(value = "dispatcher", results = {
            @Result(name = SUCCESS, type = "dispatcher", location = "result-dispatcher.jsp")})
    public String dispatcher() {
        return SUCCESS;
    }

    /**
     * @see org.apache.struts2.views.freemarker.FreemarkerResult
     */
    @Action(value = "freemarker", results = {
            @Result(name = SUCCESS, type = "freemarker", location = "result-freemarker.ftl")})
    public String freemarker() {
        return SUCCESS;
    }

    /**
     * @see org.apache.struts2.result.HttpHeaderResult
     */
    @Action(value = "httpheader", results = {
            @Result(name = SUCCESS, type = "httpheader", params = {"status", "404"})})
    public String httpheader() {
        return SUCCESS;
    }

    /**
     * @see org.apache.struts2.result.ServletRedirectResult
     */
    @Action(value = "redirect", results = {
            @Result(name = SUCCESS, type = "redirect", location = "/result-redirect.html")})
    public String redirect() {
        return SUCCESS;
    }

    /**
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
     * @see org.apache.struts2.result.StreamResult
     */
    @Action(value = "stream", results = {
            @Result(name = SUCCESS, type = "stream", params = {
                    "inputName", "imageStream",
                    "contentType", "image/jpeg",
                    "contentDisposition", "attachment;filename=result-stream.jpg",
                    "bufferSize", "1024"
            })})
    public String stream() {
        return SUCCESS;
    }

    public InputStream getImageStream() {
        return getClass().getResourceAsStream("/result-stream.jpg");
    }

    /**
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
            @Result(name = SUCCESS, type = "xslt", location = "WEB-INF/pages/result/result-xslt.xsl")})
    public String xslt() {
        return SUCCESS;
    }

    /**
     * @see org.apache.struts2.result.PlainTextResult
     */
    @Action(value = "plainText", results = {
            @Result(name = SUCCESS, type = "plainText", location = "WEB-INF/pages/result/result-plainText.jsp", params = {"charSet", "UTF-8"})})
    public String plainText() {
        return SUCCESS;
    }

}
