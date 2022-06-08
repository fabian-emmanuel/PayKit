package com.codewithfibbee.paykit.services.system;


import com.codewithfibbee.paykit.payloads.system.SystemConfigurationDto;

import java.util.Collection;
import java.util.Map;

public interface SystemConfigurationService  {

    String findConfigValueByKey(String key);

    void createDefaultConfigurations();

    Collection<SystemConfigurationDto> fetchSystemConfigurations(String group);

    void updateSystemConfigurations(String group, Map<String,String> dto);
}
