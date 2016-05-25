package test.struts2.inject;

import com.opensymphony.xwork2.inject.Inject;

public abstract class StaticConstant {

    @Inject(value = "struts.devMode")
    private static String devMode;

    public static String getDevMode() {
        return devMode;
    }

}
