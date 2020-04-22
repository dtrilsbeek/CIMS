<template>
  <div class="grid">
      <div class="map-header">Eindhoven</div>
      <div id="map"></div>
      <ul>
          <li>Info</li>
          <li>Nieuws</li>
      </ul>
      <aside class="info">info
          <button v-on:click='showAddTopicPopup()'>Add topic</button>
          <home/>
      </aside>
  </div>
</template>

<script>
import CimsMap from '@/components/leaflet/CimsMap'
import CimsMarker from '@/components/leaflet/CimsMarker'
import 'leaflet/dist/leaflet.css' // Css for loading the map smoothly
import Home from './Home.vue'

export default {
    components: {
        home: Home,
    },
    data(){
        return{
            map: null,
            markers: [],
            fontys: [51.451069, 5.4772183],
        }
    },

    mounted(){
        this.map = new CimsMap(this.fontys, 25);
        this.addMarker();

        this.map.on("click", (e) => {
            this.markers[0].moveTo(e.latlng, 2000);
        });
    },
    methods: {
        addMarker(){
            this.markers.push(new CimsMarker('ambulance', this.fontys).addTo(this.map));
        },
        showAddTopicPopup() {
            this.$root.$refs.home.show();
        }
    },
}
</script>

<style src="@/assets/css/map.css" scoped>
</style>
