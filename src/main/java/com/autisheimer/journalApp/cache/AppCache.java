package com.autisheimer.journalApp.cache;

import com.autisheimer.journalApp.entity.AppConfig;
import com.autisheimer.journalApp.repository.ConfigRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigRepository configRepository;

    public Map<String, String> AppCache = new HashMap<>();

    @PostConstruct
    public void init(){
        List<AppConfig> configs = configRepository.findAll();
        for(AppConfig config : configs){
            AppCache.put(config.getKey(), config.getValue());
        }
        //        appCache = null;
    }
}
