<template>
    <div class="map-header capitalize-inline" :class="{'left-region': regionMenu.leftRegion }">{{ regionMenu.currentRegion.name }}
            <img src="@/assets/images/edit-icon.png" class="region-button"
                v-on:click="toggleMenu()"/>
            <section class="region-menu" v-if="regionMenu.isActive" style="z-index: 1000;">
                <div class="search-bar">
                    <img class="search-icon" src="@/assets/images/search-icon.png" />
                    <input type="text" placeholder="Zoek een regio" v-model="regionMenu.text">
                </div>
                <ul>
                    <li v-for="region in filteredRegions" :key="region.name"
                        v-on:click="switchRegion(region)">
                        {{ region.name }}
                    </li>
                </ul>
            </section>
    </div>
</template>

<script>
import MapDao from "@/daos/MapDao.js";
import Region from "@/components/leaflet/Region.js";

export default {
    props: {
        bus: Object
    },
    data(){
        return{
            regionMenu: {
                leftRegion: false,
                text: "",
                currentRegion: new Region("helmond"),
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
            this.getRegionBounds(this.regionMenu.currentRegion.name)
                .then((bounds) => {
                    this.regionMenu.currentRegion.bounds = bounds;
                    this.$emit("region-bounds", bounds);    
                });
        });

    },

    methods: {
        toggleMenu(){
            this.regionMenu.isActive = ! this.regionMenu.isActive;
        },

        switchRegion(region){
            this.regionMenu.leftRegion = false;
            this.regionMenu.currentRegion = region;
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
            if(region.bounds == null){
                this.getRegionBounds(region.name)
                    .then((bounds) => {
                        region.bounds = bounds;
                        this.$emit("move-to", bounds);         
                    });
            }
            else{
                this.$emit("move-to", region.bounds); 
            }
            
        },

        checkBounds(bounds){
            const isVisible = bounds.isRegionVisible(this.regionMenu.currentRegion.bounds)
            if(! isVisible && !this.regionMenu.leftRegion){
                this.$emit('alert', `Left ${this.regionMenu.currentRegion.name}`);
                this.regionMenu.leftRegion = true;
            }
            else if(isVisible && this.regionMenu.leftRegion){
                this.$emit('alert', `Entered ${this.regionMenu.currentRegion.name}`);
                this.regionMenu.leftRegion = false;
            }
        }
    },

    computed: {
        filteredRegions(){
            return this.regionMenu.regions.filter(
                (region) => {           
                    return region.name.includes(this.regionMenu.text.toLowerCase()) &&
                           region.name !== this.regionMenu.currentRegion.name;
                }
            );
        }
    }

}
</script>

<style src="@/assets/css/region-menu.css" scoped>
</style>