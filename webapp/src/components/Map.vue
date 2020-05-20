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
import CimsRectangle from '@/components/leaflet/CimsRectangle';

// Css for loading the map smoothly
import 'leaflet/dist/leaflet.css'
// import EventStream from "./stream/EventStream"; 
import Home from './Home.vue'
import RegionMenu from '@/components/RegionMenu'

export default {
    components: {
        home: Home,
        regionMenu: RegionMenu
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

        //this.markers.push(new CimsMarker(0, 'ambulance', 'description', this.fontys).addTo(this.map));
        
        this.map.on('click', (e) => {
           this.$root.$refs.home.show(e.latlng);
        })

        //this.eventStream = new EventStream();
        this.eventSource = new EventSource("http://localhost:8083/events/stream");
        this.eventSource.onmessage = (event) => {
            
            const data = JSON.parse(event.data);

            // console.log(data);

            const type = this.getIconTypeString(data.type);
            const marker = new CimsMarker(data.id, type, data.description, [data.lat, data.lon]);
                
            this.addMarkerStream(marker);
       
   
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

        getIconTypeString(type) {
            switch (type) {
                case 1:
                    return "fireTruck"
                case 2:
                    return "ambulance"
                case 3:
                    return "fire"
                case 4:
                    return "police"
                default:
                    return "ambulance"
            }
        },

        moveTo(bounds){

            this.map.flyToBounds(bounds);

            new CimsRectangle(bounds, 'blue', 1).addTo(this.map);

            this.retrieveEventsByRegionBounds(bounds);      
        },

        retrieveEventsByRegionBounds(bounds) {  

            const params = {
                sx: bounds[0][0],
                sy: bounds[0][1],
                ex: bounds[1][0],
                ey: bounds[1][1]
            };

            let urlParams = new URLSearchParams(params).toString();

            let eventSource = new EventSource(`http://localhost:8083/events/byBounds?${urlParams}`);
            eventSource.onmessage = (/*event*/) => {
                
                //events retrieved by region bounds
                // const data = JSON.parse(event.data);
            };
        }

    }
}
</script>

<!--suppress HtmlUnknownTarget -->
<style src="@/assets/css/map.css" scoped>
</style>
