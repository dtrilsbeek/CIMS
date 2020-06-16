<template>
  <div>
    <modal :width="400" :height="500" name="addTopic-modal" class="modal" @before-open='beforeOpen()' @before-close='beforeClose()'>
      <aside class="side-bar">
        <ul>
          <li @click="switchComponent(0)" :class="{active: componentIndex == 0}"><img src="@/assets/images/event-icon.png" alt="events"><div class="tooltip">Events</div></li>
          <li @click="switchComponent(1)" :class="{active: componentIndex == 1}"><img src="@/assets/images/unit-icon.png" alt="units"><div class="tooltip">Units</div></li>
          <li @click="switchComponent(2)" :class="{active: componentIndex == 2}"><img src="@/assets/images/team-icon.png" alt="teams"><div class="tooltip">Teams</div></li>
          <li @click="switchComponent(3)" :class="{active: componentIndex == 3}"><img src="@/assets/images/management-icon.png" alt="management"><div class="tooltip">Teams & Units</div></li>
          <li @click="switchComponent(4)" :class="{active: componentIndex == 4}"><img src="@/assets/images/management-icon.png" alt="type"><div class="tooltip">types</div></li>
        </ul>
      </aside>
      <span class="close" v-on:click="hide()">X</span>       
        <component :is="activeComponent" v-on:unit-edit="editUnit($event)" :selectedMarker="selectedMarker" :unit="unit" :event="event" @hide="hide()"> </component>
    </modal>
  </div>
</template>

<script>

import Event from '@/models/Event.js';
import SituationMenu from '@/components/SituationMenu.vue'
import UnitMenu from '@/components/UnitMenu.vue'
import Teams from '@/components/Teams.vue'
import TeamManage from '@/components/TeamsManagement.vue';
import CreateEvent from '@/components/CreateEvent.vue';
import AddEventType from '@/components/AddEventType.vue'

  export default {
    components: {
      SituationMenu: SituationMenu,
      unitMenu: UnitMenu,
      teams: Teams,
      teamManage: TeamManage,
      createEvent: CreateEvent,
      addEventType: AddEventType
    },
    data() {
      return {
        name: "name",
        selectedMarker: false,
        event: new Event(),
        unit: false,
        components: ['situation-menu', 'unit-menu', 'teams', 'team-manage', 'add-event-type'],
        componentIndex: 0
      }
    },
    
    methods: {
      switchComponent(index){
        this.componentIndex = index; 
      },

      show (selectedMarker, latlng) {
        if(selectedMarker != null){
          this.selectedMarker = selectedMarker;
          this.event.id = selectedMarker.id;
          this.event.type = selectedMarker.type;
          this.event.status = selectedMarker.status;
          this.event.description = selectedMarker.description;
        }
        this.event.lat = latlng.lat;
        this.event.lon = latlng.lng;

        this.$modal.show('addTopic-modal');
        //params can be added with ", { foo: 'bar' })"
      },

      editUnit(unit) {
        this.unit = unit;
        this.switchComponent(1); 
      },

      hide () {
        this.$modal.hide('addTopic-modal');
      },
      beforeOpen (/*event*/) {
        // console.log(event.params.foo);
      },
      beforeClose() {
        
      }
    },

    computed: {
      activeComponent() {
        return this.components[this.componentIndex];
      },
    }
  }
</script>

<style src="@/assets/css/home.css" scoped></style>



<!-- post request naar quarkus: localhost:xxxx/events -->