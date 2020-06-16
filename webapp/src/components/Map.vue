<template>
    <div class="grid">

        <div class="logo-nav">
            CIMS
        </div>
        <div class="tabs-nav">

        </div>

        <div class="region-menu-nav">
            <region-menu :bus="bus"
                         v-on:region-bounds="setInitialPosition($event)"
                         v-on:move-to="moveTo($event)"
                         v-on:alert="alert($event)"
                         ref="region">

            </region-menu>
        </div>

        <div id="map">
        </div>
        <div class="map-info">
            <h4>Info</h4>
        </div>

        <aside class="info">
            <menu-modal :bus="bus" ref="modal"/>
            <active-events :bus="bus" v-on:move-to-event="moveToEvent($event)"/>
        </aside>

        <alert-notifier ref="notifier"></alert-notifier>
    </div>
</template>

<script>

    import CimsMap from '@/components/leaflet/CimsMap'
    import CimsMarker from '@/components/leaflet/CimsMarker'
    import CimsRectangle from '@/components/leaflet/CimsRectangle'
    import AlertNotifier from '@/components/Notifier'
    // Css for loading the map smoothly
    import 'leaflet/dist/leaflet.css'
    import Home from './Home.vue'
    import RegionMenu from '@/components/RegionMenu'
    import ActiveEvents from './ActiveEvents'
    import Vue from 'vue';
    import config from '@/components/rest/RestConfig'
    import L from "leaflet";

    export default {
        components: {
            alertNotifier: AlertNotifier,
            menuModal: Home,
            regionMenu: RegionMenu,
            activeEvents: ActiveEvents
        },
        data() {
            return {
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

        mounted() {
            this.bus.$emit("retrieve-current-region-bounds");
            this.createEventSource();
        },

        methods: {

            setSelectedMarker(marker) {
                if (this.selectedMarker) {
                    this.selectedMarker.getElement().classList.remove('active');
                }
                this.selectedMarker = marker;
            },

            createEventSource() {
                this.eventSource = new EventSource(config.getUrl('events', 'stream'));
                this.eventSource.onmessage = (event) => {

                    const data = JSON.parse(event.data);

                    if (data.isUpdate) {
                        const marker = this.markers[data.id]
                        if (marker) {
                            marker.type = data.type;
                            marker.status = data.status;
                            marker.description = data.description;
                            marker.moveTo([data.lat, data.lon], 1500);
                        }
                    } else {
                        this.markers[data.id] = new CimsMarker(this, data.id, data.type, data.description, data.status, [data.lat, data.lon]);
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
                this.showUserInstruction(lat, lon);
                this.leafletMap.on('click', (e) => {
                    let selectedMarker = this.selectedMarker || null;
                    this.$refs.modal.show(selectedMarker, e.latlng);
                });


                this.leafletMap.on('dragend', () => {
                    this.$refs.region.checkBounds(this.leafletMap.getCimsBounds());
                });

            },

            showUserInstruction(lat, lon) {
                var imageUrl = '/userInstruction.png';
                var imageBounds = [[lat - 0.05, lon - 0.14], [lat + 0.05, lon + 0.14]];
                var overlay = L.imageOverlay(imageUrl, imageBounds);
                overlay.addTo(this.leafletMap);
                setTimeout(function () {
                    overlay.getElement().classList.add('fadeout')
                    setTimeout(function () {
                        overlay.remove(this.leafletMap);
                    }, 800);
                }, 3000)
            },

            moveTo(bounds) {
                this.leafletMap.flyToBounds(bounds);
                new CimsRectangle(bounds, 'blue', 1).addTo(this.leafletMap);
                this.bus.$emit("retrieve-by-bounds", bounds);
            },
            moveToEvent(event) {
                this.leafletMap.flyTo([event.lat, event.lon], 12);
                this.markers[event.id].fire('click');
            },

            alert(message) {
                this.$refs.notifier.addAlert(message);
            }
        }
    }
</script>

<!--suppress HtmlUnknownTarget -->
<style src="@/assets/css/map.css">
</style>
