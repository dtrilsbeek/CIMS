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
            fontys: [51.451069, 5.4772183]
        }
    },

    readStream() {
        let path = "/events/stream/1";

        const source = new EventSource(path);

        source.onmessage = event => {
            const data = JSON.parse(event.data);

            console.log(data);
            // lat.textContent = data.lat;
            // lon.textContent = data.lon;
            // type.textContent = data.type;
            // description.textContent = data.description;
        };
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
        readStream() {
            this.readStream();
        }
    }
}
</script>

<style src="@/assets/css/map.css" scoped>
</style>
