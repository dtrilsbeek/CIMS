<template>
  <div class="full-size">
      <edit-unit v-if="isEditing" @toggle="toggleMode()"></edit-unit>
      <create-unit v-else @toggle="toggleMode()"></create-unit>
  </div>
</template>

<script>
import EditUnit from '@/components/EditUnit'
import CreateUnit from '@/components/Units'

export default {

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
            this.isEditing = ! this.isEditing;
        }
    }
}
</script>

<style>

</style>