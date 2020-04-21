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
import 'leaflet/dist/leaflet.css'
import EventStream from "./stream/EventStream"; // Css for loading the map smoothly

export default {
    data(){
        return{
            eventStream: null,
            map: null,
            markers: [],
            fontys: [51.451069, 5.4772183]
        }
    },

    mounted(){
        this.eventStream = new EventStream();
        this.map = new CimsMap(this.fontys, 25);
        this.addMarker();
        this.map.on("click", (e) => {
            this.markers[0].moveTo(e.latlng, 2000);
        });
        this.eventStream.readStream();
        console.log("test");
    },

    methods: {
        addMarker(){
            this.markers.push(new CimsMarker('ambulance', this.fontys).addTo(this.map));
        },
        // readStream() {
        //     this.readStream();
        // }
    }
}
</script>

<style src="@/assets/css/map.css" scoped>
</style>
