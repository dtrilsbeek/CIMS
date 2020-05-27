<template>
  <div class="grid">
      <region-menu v-on:move-to="moveTo($event)"></region-menu>
      <div id="map"></div>
      <ul class="map-info">
          <li>Info</li>
          <li>Nieuws</li>
      </ul>
  
      <aside class="info">
            <home/>
            info
          <active-events :bus="bus" />
     
      </aside>

  </div>
</template>

<script>
import CimsMap from '@/components/leaflet/CimsMap'
import CimsMarker from '@/components/leaflet/CimsMarker'
import CimsRectangle from '@/components/leaflet/CimsRectangle';
// Css for loading the map smoothly
import 'leaflet/dist/leaflet.css'
import Home from './Home.vue'
import RegionMenu from '@/components/RegionMenu'
import ActiveEvents from './ActiveEvents'
import Vue from 'vue';

    export default {
    components: {
        home: Home,
        regionMenu: RegionMenu,
        activeEvents: ActiveEvents
    },
    data(){
        return{
            // eventStream: null,
            bus: new Vue(),
            leafletMap: null,
            markers: [],
            fontys: [51.451069, 5.4772183],
            eventSource: null,
        }
    },

    mounted(){
        this.leafletMap = new CimsMap(this.fontys, 25);
        
        this.leafletMap.on('click', (e) => {
           this.$root.$refs.home.show(this, e.latlng);
        })

        this.eventSource = new EventSource("http://localhost:8083/events/stream");
        this.eventSource.onmessage = (event) => {

            const data = JSON.parse(event.data);
            const type = this.getIconTypeString(data.type);

            if (data.isUpdate) {
                const marker = this.markers[data.id]
                if(marker) {
                    marker.type = data.type;
                    marker.description = data.description;
                    marker.moveTo([data.lat, data.lon])
                }
            } else {
                this.markers[data.id] = new CimsMarker(this, data.id, type, data.description, [data.lat, data.lon]);
            }
        };
    },

    created() {
         this.$root.$refs.map = this;
    },

    methods: {

        setSelectedMarker(marker) {
            if (this.selectedMarker) {
                this.selectedMarker.getElement().classList.remove('active');
            }

            this.selectedMarker = marker;
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
            this.leafletMap.flyToBounds(bounds);
            new CimsRectangle(bounds, 'blue', 1).addTo(this.leafletMap);
            this.bus.$emit("retrieve-by-bounds", bounds);            
        },
    }
}
</script>

<!--suppress HtmlUnknownTarget -->
<style src="@/assets/css/map.css">
</style>
