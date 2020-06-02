<template>
    <div class="map-header">{{ regionMenu.currentRegion }}
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
</template>

<script>
import MapDao from "@/daos/MapDao.js";
import Region from "@/components/leaflet/Region.js";

export default {
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

        moveToBounds(region) {   
            MapDao.getRegionBounds(region.name)
                .then(regionresults => 
                {
                    let regionbox = regionresults[0].boundingbox;

                    //api returns box coordinates in the wrong order
                    let bounds = [[regionbox[0],regionbox[2]],[regionbox[1],regionbox[3]]];
                    region.bounds = bounds;
                    
                    this.$emit("move-to", region.bounds);            
                })
                .catch((error) => {
                    console.log(error);          
                })
        },
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