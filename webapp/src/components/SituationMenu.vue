<template>
  <div class="full-size">
      <update-situation v-if="isEditing" @toggle="toggleMode()" :selected-marker="selectedMarker" :event="event"></update-situation>
      <create-situation v-else @toggle="toggleMode()" :selected-marker="selectedMarker" :event="event"></create-situation>
  </div>
</template>

<script>
import UpdateSituation from '@/components/UpdateSituation'
import CreateSituation from '@/components/CreateSituation'

export default {
    props: {
        selectedMarker: {
            type: [Object, Boolean],
            required: true
        },
        event: {
            type: Object,
            required: true
        }
    },

    components:{
        UpdateSituation: UpdateSituation,
        CreateSituation: CreateSituation
    },

    data(){
        // Set default to updating, when no marker is selected
        return {
            isEditing: (this.selectedMarker != false)
        }
    },

    methods: {
        toggleMode(){
            this.isEditing =  ! this.isEditing;
        }
    }
}
</script>