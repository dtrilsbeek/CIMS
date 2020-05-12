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
//import EventStream from "./stream/EventStream"; // Css for loading the map smoothly
import Home from './Home.vue'

export default {
    components: {
        home: Home,
    },
    data(){
        return{
            // eventStream: null,
            map: null,
            markers: [],
            fontys: [51.451069, 5.4772183],
            eventSource: null
        }
    },

    mounted(){

        // this.eventStream = new EventStream();
        this.map = new CimsMap(this.fontys, 25);
        // this.addMarker();

        this.markers.push(new CimsMarker('ambulance', 'description', this.fontys).addTo(this.map));
        
        this.map.on('click', (e) => {
           this.$root.$refs.home.show(e.latlng);
        })

        // this.eventStream = new EventStream();
        // this.eventSource = new EventSource("http://localhost:8080/events/stream");
        // this.eventSource.onmessage = (event) => {
        // const data = JSON.parse(event.data);

        //     if(data.type === 1) {
        //         const marker = this.markers.find(m => m.id === data.id);
        //         marker.moveTo([data.lat, data.lon], 500);
        //     }
        //     else {
        //         const marker = new CimsMarker(data.id, 'ambulance', [data.lat, data.lon]);
        //         this.addMarkerStream(marker);
        //     }   
        // };
        
        // this.eventStream.readStream(this.addMarkerStream);
    },

    created() {
         this.$root.$refs.map = this;
    },

    methods: {
        addMarker(markerInfo){
            this.markers.push(new CimsMarker('ambulance', markerInfo.description, [markerInfo.lat, markerInfo.lon]).addTo(this.map));
        },
        addMarkerStream(marker){
            this.markers.push(marker.addTo(this.map));
        },
    },
}
</script>

<!--suppress HtmlUnknownTarget -->
<style src="@/assets/css/map.css" scoped>
</style>
