<template>
  <div class="grid">
      <div class="map-header">Eindhoven</div>
      <div id="map"></div>
      <ul>
          <li>Info</li>
          <li>Nieuws</li>
      </ul>
      <aside class="info">Info</aside>
  </div>
</template>

<script>
import CimsMap from '@/components/leaflet/CimsMap'
import CimsMarker from '@/components/leaflet/CimsMarker'
import 'leaflet/dist/leaflet.css' // Css for loading the map smoothly

export default {
    data(){
        return{
            map: null,
            markers: [],
            fontys: [51.451069, 5.4772183],
            eventSource: null
        }
    },

    mounted(){
        this.map = new CimsMap(this.fontys, 25);
        this.addMarker();
        this.eventSource = new EventSource("http://localhost:8080/battery-levels/stream");
        this.eventSource.onmessage = (event) => {
            const data = JSON.parse(event.data);
            const marker = this.markers[0];
            marker.moveTo([data,data]);
        };
    },

    methods: {
        addMarker(){
            this.markers.push(new CimsMarker('fireTruck', this.fontys).addTo(this.map));
        }
    }
}
</script>

<style src="@/assets/css/map.css" scoped>
</style>
