
package com.example.listviev;

    public class SensorsPojo {

        public SensorsPojo() {
        }

        private int id;
        private int stationId;
        private Param param;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStationId() {
            return stationId;
        }

        public void setStationId(int stationId) {
            this.stationId = stationId;
        }

        public Param getParam() {
            return param;
        }

        public void setParam(Param param) {
            this.param = param;
        }

        @Override
        public String toString() {
            return "SensorsPojo{" +
                    "id=" + id +
                    ", stationId=" + stationId +
                    ", param=" + param +
                    '}';
        }
    }