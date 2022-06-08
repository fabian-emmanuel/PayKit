package com.codewithfibbee.paykit.repositories.system;


import com.codewithfibbee.paykit.models.system.SystemConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Collection;

public interface SystemConfigurationRepository extends MongoRepository<SystemConfiguration,Long> {
    @Query("select s.value from SystemConfiguration s where s.configurationKey = ?1")
    String findByConfigurationKey(String key);

    Collection<SystemConfiguration> findByConfigurationGroupOrderBySortOrderAsc(String group);
}
