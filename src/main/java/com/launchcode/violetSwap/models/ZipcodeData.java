package com.launchcode.violetSwap.models;
//package com.externalapi.model;

import org.springframework.data.annotation.Version;

public class ZipcodeData {
        private String display_name;
        private Integer lat;
        private Integer lon;

//        public ZipcodeData(String display_name, Integer lat, Integer lon){
//                this.display_name = display_name;
//                this.lat = lat;
//                this.lon = lon;
//        }

        public String getDisplay_name() {
                return display_name;
        }

        public void setDisplay_name(String display_name) {
                this.display_name = display_name;
        }

        public Integer getLat() {
                return lat;
        }

        public void setLat(Integer lat) {
                this.lat = lat;
        }

        public Integer getLon() {
                return lon;
        }

        public void setLon(Integer lon) {
                this.lon = lon;
        }
}
