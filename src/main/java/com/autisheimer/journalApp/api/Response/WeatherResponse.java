package com.autisheimer.journalApp.api.Response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse{

    private Current current;

    @Getter
    @Setter
    public class Current{

        @JsonProperty("last_updated")
        private String lastUpdated;
        @JsonProperty("temp_c")
        private double tempC;
        @JsonProperty("feelslike_c")
        private double feelslikeC;
        private int humidity;
        @JsonProperty("precip_mm")
        private int precipMm;
    }

}





