package io.github.firefang.power.permission;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xinufo
 *
 */
@ConfigurationProperties(prefix = PermissionProperties.PREFIX)
public class PermissionProperties {
    public static final String PREFIX = "power.permission";
    public static final String SWITCH = PREFIX + ".enabled";
    public static final String SERIALIZE_SWITCH = PREFIX + ".serialize";

    private boolean enabled;
    private boolean serialize;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isSerialize() {
        return serialize;
    }

    public void setSerialize(boolean serialize) {
        this.serialize = serialize;
    }

}
