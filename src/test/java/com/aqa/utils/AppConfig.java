package com.aqa.utils;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources({"classpath:config.properties"})
public interface AppConfig extends Config {

    public static AppConfig CONFIG = ConfigFactory.create(AppConfig.class);
    
    @Key("api.base.url")
    String apiBaseUrl();

    @Key("ui.base.url")
    String uiBaseUrl();

    @Key("remote.api.base.url")
    String remoteApiBaseUrl();
} 
