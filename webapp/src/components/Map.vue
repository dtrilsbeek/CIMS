<template>
  <div class="grid">
      <region-menu :bus="bus" v-on:region-bounds="setInitialPosition($event)" v-on:move-to="moveTo($event)"></region-menu>
      <div id="map"></div>
      <div class="map-info">
            <h4>Info</h4>
      </div> 
  
      <aside class="info">
            <home/>        
          <active-events :bus="bus" v-on:move-to-event="moveToEvent($event)" />
     
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
import config from '@/components/rest/RestConfig'

    export default {
    components: {
        home: Home,
        regionMenu: RegionMenu,
        activeEvents: ActiveEvents
    },
    data(){
        return{
            // eventStream: null,
            streamUrl: config.getUrl('events', 'stream'),
            bus: new Vue(),
            leafletMap: null,
            markers: [],
            initialPosition: null,
            // fontys: [51.451069, 5.4772183],
            eventSource: null,
        }
    },

    mounted(){
        this.bus.$emit("retrieve-current-region-bounds");            
        this.createEventSource();
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

        createEventSource() {
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

        setInitialPosition(bounds) {
            //convert to number
            let numberBounds = [
                Array.from(bounds[0], x => +x),
                Array.from(bounds[1], x => +x)
            ]

            //avg middle position of region bounds
            let lat = (numberBounds[0][0] + numberBounds[1][0]) / 2;
            let lon = (numberBounds[0][1] + numberBounds[1][1]) / 2;

            this.initialPosition = [lat, lon];
            
            this.leafletMap = new CimsMap(this.initialPosition, 13);
            this.leafletMap.on('click', (e) => {
                this.$root.$refs.home.show(this, e.latlng);
            });      
        },

        moveTo(bounds){
            this.leafletMap.flyToBounds(bounds);
            new CimsRectangle(bounds, 'blue', 1).addTo(this.leafletMap);
            this.bus.$emit("retrieve-by-bounds", bounds);            
        },
        moveToEvent(event) {
            this.leafletMap.flyTo([event.lat, event.lon], 12);
            this.markers[event.id].fire('click');
        }
    }
}
</script>

<!--suppress HtmlUnknownTarget -->
<style src="@/assets/css/map.css">
</style>
