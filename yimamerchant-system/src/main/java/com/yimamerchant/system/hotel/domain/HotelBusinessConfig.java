package com.yimamerchant.system.hotel.domain;

import com.yimamerchant.common.core.domain.BaseEntity;

public class HotelBusinessConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;
    private String configKey;
    private String configName;
    private String configGroup;
    private String configValue;
    private String configType;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getConfigKey() { return configKey; }
    public void setConfigKey(String configKey) { this.configKey = configKey; }
    public String getConfigName() { return configName; }
    public void setConfigName(String configName) { this.configName = configName; }
    public String getConfigGroup() { return configGroup; }
    public void setConfigGroup(String configGroup) { this.configGroup = configGroup; }
    public String getConfigValue() { return configValue; }
    public void setConfigValue(String configValue) { this.configValue = configValue; }
    public String getConfigType() { return configType; }
    public void setConfigType(String configType) { this.configType = configType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
