<template>
    <div class="map-header" style="position: static">{{ regionMenu.currentRegion }}
        <div style="position: relative; height: 50px; top: -100%">
            <img src="@/assets/images/edit-icon.png" class="region-button"
                v-on:click="toggleMenu()"/>
            <section class="region-menu" v-if="regionMenu.isActive" style="z-index: 1000;">
                <div class="search-bar">
                    <img class="search-icon" src="@/assets/images/search-icon.png" />
                    <input type="text" placeholder="Zoek een regio" v-model="regionMenu.text">
                </div>
                <ul>
                    <li v-for="region in filteredRegions" :key=region.name
                        v-on:click="switchRegion(region)">
                        {{ region.name }}
                    </li>
                </ul>
            </section>
        </div>
        <alert-notifier ref="notifier"></alert-notifier>
    </div>
</template>

<script>
import MapDao from "@/daos/MapDao.js";
import Region from "@/components/leaflet/Region.js";
import AlertNotifier from '@/components/Notifier'

export default {
    components: {
        alertNotifier: AlertNotifier
    },

    props: {
        bus: Object
    },
    data(){
        return{
            regionMenu: {
                text: "",
                currentRegion: "helmond",
                isActive: false,
                regions: [     
                    new Region("eindhoven"),
                    new Region("helmond"),
                    new Region("'s-hertogenbosch"),
                ]
            },
        }
    },

    created() {
        this.bus.$on('retrieve-current-region-bounds', () => {
            this.getRegionBounds(this.regionMenu.currentRegion)
                .then((bounds) => {
                    this.$emit("region-bounds", bounds);    
                });
        });
    },

    mounted() {
        this.$refs.notifier.addAlert('Left Helmond');
        this.$refs.notifier.addAlert('Left Helmond');
        this.$refs.notifier.addAlert('Left Helmond');
        this.$refs.notifier.addAlert('Left Helmond');
    },

    methods: {
        toggleMenu(){
            this.regionMenu.isActive = ! this.regionMenu.isActive;
        },

        switchRegion(region){
            this.regionMenu.currentRegion = region.name;
            this.regionMenu.isActive = false;
            this.moveToBounds(region);
            
            this.regionMenu.text = "";
        },

        async getRegionBounds(regionName) {
            
            let regionresults = await MapDao.getRegionBounds(regionName);
            let regionbox = regionresults[0].boundingbox;

            //api returns box coordinates in the wrong order
            let bounds = [[regionbox[0],regionbox[2]],[regionbox[1],regionbox[3]]];

            return bounds;
        },

        moveToBounds(region) {   
            this.getRegionBounds(region.name)
                .then((bounds) => {
                    this.$emit("move-to", bounds);            
                });
        },

        getRegion(){
            return  this.currentRegion;
        }
    },

    computed: {
        filteredRegions(){
            return this.regionMenu.regions.filter(
                (region) => {           
                    return region.name.includes(this.regionMenu.text.toLowerCase()) &&
                           region.name !== this.regionMenu.currentRegion;
                }
            );
        }
    }

}
</script>

<style src="@/assets/css/region-menu.css" scoped>
</style>