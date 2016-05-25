package test.struts2.config;

import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.ConfigurationProvider;
import com.opensymphony.xwork2.inject.ContainerBuilder;
import com.opensymphony.xwork2.inject.Scope;
import com.opensymphony.xwork2.util.location.LocatableProperties;
import test.struts2.entity.UserValidator;
import test.struts2.factory.UserValidatorFactory;

/**
 * 自定义ContainerProvider
 */
public class CustomContainerProvider implements ConfigurationProvider {

    @Override
    public void destroy() {
    }

    @Override
    public void init(Configuration configuration) throws ConfigurationException {
    }

    @Override
    public boolean needsReload() {
        return false;
    }

    @Override
    public void loadPackages() throws ConfigurationException {
    }

    @Override
    public void register(ContainerBuilder builder, LocatableProperties props) throws ConfigurationException {
        if (!builder.contains(UserValidator.class)) {
            // 添加自定义工厂到容器
            builder.factory(UserValidator.class, new UserValidatorFactory(), Scope.SINGLETON);
        }
        if (!props.containsKey("defaultUser")) {
            // 添加key-value到容器
            props.setProperty("defaultUser", "guest");
        }
    }

}
