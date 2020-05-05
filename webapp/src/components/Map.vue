<template>
  <div class="grid">
      <div class="map-header">Eindhoven</div>
      <div id="map"></div>
      <ul>
          <li>Info</li>
          <li>Nieuws</li>
      </ul>
      <aside class="info">info
          <home/>
      </aside>
  </div>
</template>

<script>
import CimsMap from '@/components/leaflet/CimsMap'
import CimsMarker from '@/components/leaflet/CimsMarker'

import 'leaflet/dist/leaflet.css'
import EventStream from "./stream/EventStream"; // Css for loading the map smoothly
import Home from './Home.vue'


export default {
    components: {
        home: Home,
    },
    data(){
        return{
            eventStream: null,
            map: null,
            markers: [],
            fontys: [51.451069, 5.4772183],
        }
    },

    mounted(){
        this.eventStream = new EventStream();
        this.map = new CimsMap(this.fontys, 25);
        this.addMarker();

        this.map.on("click", (e) => {
            this.$root.$refs.home.show(e.latlng);
  
        });
        this.eventStream.readStream(this.addMarkerStream);
        console.log("test");
    },
    methods: {
        addMarker(){
            this.markers.push(new CimsMarker('ambulance', this.fontys).addTo(this.map));
        },

        addMarkerStream(marker){
            this.markers.push(marker.addTo(this.map));
        },
    }
}
</script>

<!--suppress HtmlUnknownTarget -->
<style src="@/assets/css/map.css" scoped>
</style>
