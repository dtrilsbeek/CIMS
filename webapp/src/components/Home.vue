<template>
  <div>
    <modal :width="400" :height="500" name="addTopic-modal" class="modal" @before-open='beforeOpen()' @before-close='beforeClose()'>
      <aside class="side-bar">
        <ul>
          <li @click="switchComponent(0)" :class="{active: componentIndex == 0}"><img src="@/assets/images/event-icon.png" alt="events"><div class="tooltip">Events</div></li>
          <li @click="switchComponent(1)" :class="{active: componentIndex == 1}"><img src="@/assets/images/unit-icon.png" alt="units"><div class="tooltip">Units</div></li>
          <li @click="switchComponent(2)" :class="{active: componentIndex == 2}"><img src="@/assets/images/team-icon.png" alt="teams"><div class="tooltip">Teams</div></li>
          <li class="relative"><router-link to="/manageteams" class="full-size-link" /><img src="@/assets/images/management-icon.png" alt="team & unit management"><div class="tooltip">Teams & Units</div></li>
        </ul>
      </aside>
      <span class="close" @click="$modal.hide('addTopic-modal')">X</span>       
        <component :is="activeComponent" :selectedMarker="selectedMarker" :event="event"> </component>
        <!-- <unit-menu ninjas="apple"> </unit-menu> -->
    </modal>
  </div>
</template>

<script>
import Event from '@/models/Event.js';
import ModalDao from '@/daos/ModalDao.js';
import CreateSituation from '@/components/CreateSituation.vue'
import UnitMenu from '@/components/UnitMenu.vue'
import Teams from '@/components/Teams.vue'


  export default {
    props: {
        bus: Object
    },
    components: {
      createSituation: CreateSituation,
      unitMenu: UnitMenu,
      teams: Teams
    },
    data() {
      return {
        name: "name",
        selectedMarker: false,
        event: new Event(),
        components: ['create-situation', 'unit-menu', 'teams'],
        componentIndex: 0
      }
    },
    
    methods: {
      switchComponent(index){
        this.componentIndex = index; 
      },

      addEvent() {

        ModalDao.addEvent(this.event)
          .catch(error => {
            console.log(error);
          });
        this.hide();
      },

      putEvent() {
        ModalDao.putEvent(this.event)
          .catch(error => {
            console.log(error);
          });
        this.hide();
      },

      show (selectedMarker, latlng) {
        console.log(selectedMarker);
        if(selectedMarker != null){
          this.selectedMarker = selectedMarker;
          this.event.id = selectedMarker.id;
        }
        this.event.lat = latlng.lat;
        this.event.lon = latlng.lng;

        this.$modal.show('addTopic-modal');
        //params can be added with ", { foo: 'bar' })"
      },
      hide () {
        this.$modal.hide('addTopic-modal');
      },
      beforeOpen (/*event*/) {
        // console.log(event.params.foo);
      },
      beforeClose() {
        console.log('this will be called before the modal closes');
      }
    },

    computed: {
      activeComponent() {
        return this.components[this.componentIndex];
      }
    }
  }
</script>

<style src="@/assets/css/home.css" scoped></style>



<!-- post request naar quarkus: localhost:xxxx/events -->