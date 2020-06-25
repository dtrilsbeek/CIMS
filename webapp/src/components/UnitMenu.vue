<template>
  <div class="full-size">
      <edit-unit 
            v-if="isEditing" 
            @toggle="toggleMode()" 
            :selected-marker="selectedMarker" 
            :unit="unit" 
            v-on:alert="alert($event)"
            ></edit-unit>
      <create-unit 
            v-else @toggle="toggleMode()"
            v-on:alert="alert($event)"></create-unit>
  </div>
</template>

<script>
import EditUnit from '@/components/EditUnit'
import CreateUnit from '@/components/Units'

export default {
    props: {
        selectedMarker: {
            type: [Object, Boolean],
            required: true
        },
        unit: {
            type: [Object, Boolean]
        },
    },

    components:{
        editUnit: EditUnit,
        createUnit: CreateUnit
    },

    data(){
        return {
            isEditing: true
        }
    },

    methods: {
        toggleMode(){
            this.isEditing =  ! this.isEditing;
        },

        alert(message){
            this.$emit('alert', message);
        }
    }
}
</script>