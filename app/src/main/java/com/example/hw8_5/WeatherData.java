package com.example.hw8_5;

public class WeatherData {
    private String city, temp, feelsLike, humidity, wind, clouds, pressure, sunrise, sunset, lat, lon, currentTime;

    public WeatherData(){}

    public void setCity(String c) { this.city = c; }
    public String getCity() { return city; }

    public void setTemp(String t) { this.temp = t; }
    public String getTemp() { return temp; }

    public void setFeelsLike(String f) { this.feelsLike = f; }
    public String getFeelsLike() { return feelsLike; }

    public void setHumidity(String h) { this.humidity = h; }
    public String getHumidity() { return humidity; }

    public void setWind(String w) { this.wind = w; }
    public String getWind() { return wind; }

    public void setClouds(String c) { this.clouds = c; }
    public String getClouds() { return clouds; }

    public void setPressure(String p) { this.pressure = p; }
    public String getPressure() { return pressure; }

    public void setSunrise(String sr) { this.sunrise = sr; }
    public String getSunrise() { return sunrise; }

    public void setSunset(String ss) { this.sunset = ss; }
    public String getSunset() { return sunset; }

    public void setLat(String lt) { this.lat = lt; }
    public String getLat() { return lat; }

    public void setLon(String ln) { this.lon = ln; }
    public String getLon() { return lon; }

    public void setCurrentTime(String ct) { this.currentTime = ct; }
    public String getCurrentTime() { return currentTime; }


}


