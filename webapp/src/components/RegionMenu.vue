<template>
    <div class="map-header">{{ regionMenu.currentRegion }}
        <img src="@/assets/images/edit-icon.png" class="region-button"
            v-on:click="toggleMenu()"/>
        <section class="region-menu" v-if="regionMenu.isActive" style="z-index: 1000;">
            <input type="text" placeholder="Regio" v-model="regionMenu.text" v-on:blur="toggleMenu()">
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
export default {
    data(){
        return{
            regionMenu: {
                text: "",
                currentRegion: "eindhoven",
                isActive: false,
                regions:[
                {
                    name: "eindhoven",
                    bounds:[[51.4581471,5.4682512],[51.4161579,5.504405]]
                },
                {
                    name: "helmond",
                    bounds: [[51.4862591,5.5995564], [51.4542218,5.6847866]] 
                },
                {
                    name: "'s-hertogenbosch",
                    bounds: [[51.731733, 5.245634], [51.7092084,5.2897453]]
                }
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
            this.$emit("move-to", region.bounds);
            this.regionMenu.text = "";
        }
    },

    computed: {
        filteredRegions(){
            return this.regionMenu.regions.filter(
                (region) => {
                    return region.name.includes(this.regionMenu.text) &&
                           region.name !== this.regionMenu.currentRegion;
                }
            );
        }
    }

}
</script>

<style src="@/assets/css/region-menu.css" scoped>
</style>