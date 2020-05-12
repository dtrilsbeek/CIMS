<template>
  <div class="grid">
      <region-menu v-on:move-to="moveTo($event)"></region-menu>
      <div id="map"></div>
      <ul class="map-info">
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

// Css for loading the map smoothly
import 'leaflet/dist/leaflet.css'
import EventStream from "./stream/EventStream"; 
import Home from './Home.vue'
import RegionMenu from '@/components/RegionMenu'

export default {
    components: {
        home: Home,
        regionMenu: RegionMenu
    },
    data(){
        return{
            eventStream: null,
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

        this.markers.push(new CimsMarker(0, 'ambulance', 'description', this.fontys).addTo(this.map));
        
        this.map.on('click', (e) => {
           this.$root.$refs.home.show(e.latlng);
        })

        this.eventStream = new EventStream();
        this.eventSource = new EventSource("http://localhost:8083/events/stream");
        this.eventSource.onmessage = (event) => {
            
            const data = JSON.parse(event.data);

            console.log(data);

            // if(true){
            //
            // }


            //data.id = Math.floor(Math.random() * 10);
                        // console.log(data);

//             const marker = this.markers[0];
// console.log(data.type);

            // const marker = this.markers.find(m => m.id === data.id);
                // marker.moveTo([data.lat, data.lon], 500);

            // if(data.type === 1) {
                //const marker = this.markers.find(m => m.id === data.id);
                //if(marker != undefined) {
                    //marker.moveTo([data.lat, data.lon], 500);
                //}
                // else {
                    const marker = new CimsMarker(data.id, 'ambulance', 'description', [data.lat, data.lon]);
                    // console.log('['+data.lat + ', ' + data.lon + ']');

                    this.addMarkerStream(marker);
                // }
            // console.log(marker);
        
            // }
          
        };

        // this.eventStream.readStream(this.addMarkerStream);
    },

    created() {
         this.$root.$refs.map = this;
    },

    methods: {
        addMarker(markerInfo){
            this.markers.push(new CimsMarker(this.markers.length, 'ambulance', markerInfo.description, [markerInfo.lat, markerInfo.lon]).addTo(this.map));
        },

        addMarkerStream(marker){
            this.markers.push(marker.addTo(this.map));
        },

        moveTo(bounds){
            console.log(bounds);
            this.map.flyToBounds(bounds);
        }

    }
}
</script>

<!--suppress HtmlUnknownTarget -->
<style src="@/assets/css/map.css" scoped>
</style>
