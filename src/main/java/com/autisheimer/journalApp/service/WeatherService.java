package com.autisheimer.journalApp.service;

import com.autisheimer.journalApp.api.Response.WeatherResponse;
import com.autisheimer.journalApp.cache.AppCache;
import com.autisheimer.journalApp.entity.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apikey;
//    private static final String API = "http://api.weatherapi.com/v1/current.json?key=APIKEY&q=CITY";
    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city){
        String uri = appCache.AppCache.get("weather_api").replace("<city>", city).replace("<apikey>", apikey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(uri, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse responseBody = response.getBody();
        return responseBody;
    }
}
