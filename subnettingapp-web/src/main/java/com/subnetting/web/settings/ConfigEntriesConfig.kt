package com.subnetting.web.settings

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConfigEntriesConfig {
    @Bean("configEntries")
    fun getConfigEntries(): List<ConfigEntry> {
        val objectMapper = ObjectMapper()
        return objectMapper.readValue(
            this::class.java.getResourceAsStream("/config_entries.json"),
            Array<ConfigEntry>::class.java
        ).toList()
    }
}