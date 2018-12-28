package io.github.firefang.power.login;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xinufo
 *
 */
@ConfigurationProperties(prefix = LoginProperties.PREFIX)
public class LoginProperties {
    public static final String PREFIX = "power.login";
    public static final String SWITCH = PREFIX + ".enabled";
    public static final String CONTROLLER_SWITCH = PREFIX + ".controller";
    public static final String DEFAULT_SESSION_KEY = "userinfo";
    public static final String DEFAULT_TOKEN_KEY = "x-auth-token";

    private boolean enabled;
    /**
     * 是否自动注册 LoginController
     */
    private boolean controller;
    /**
     * 认证类型：Session、Token
     */
    private AuthType type = AuthType.SESSION;
    /**
     * Session中存放用户信息或Header中存放Token的key
     */
    private String key;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isController() {
        return controller;
    }

    public void setController(boolean controller) {
        this.controller = controller;
    }

    public AuthType getType() {
        return type;
    }

    public void setType(AuthType type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static enum AuthType {
        SESSION, TOKEN;
    }

}
