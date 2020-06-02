<template>
  <div>

    <!-- <img alt="Vue logo" src="../assets/logo.png"> -->

    <modal :width="400" :height="500" name="addTopic-modal" class="modal" @before-open='beforeOpen()' @before-close='beforeClose()'>
      <span class="close" @click="$modal.hide('addTopic-modal')">X</span>       
      <ul>
        <li><h1>Add situation</h1></li>

        <li><input type="number" v-model="event.lat" placeholder="Input latitude" ></li>
        <li><input type="number" v-model="event.lon" placeholder="Input longitude" ></li>

        <li><select v-model="event.type"><option v-for="number in 5" :key="number.number">{{number}}</option></select></li>

        <li><textarea v-model="event.description" placeholder="Input information"/></li>

        <li><button type="button" v-on:click="addEvent()">Send</button></li>
        <li><button type="button" v-on:click="putEvent()">Move selected marker</button></li>

      </ul>
    </modal>
  </div>
</template>

<script>
import Event from '@/models/Event.js';
import ModalDao from '@/daos/ModalDao.js';

  export default {
    name: 'Home',
    props: {
        bus: Object
    },
    data() {
      return {
        event: new Event()
      }
    },

    created() {
        this.$root.$refs.home = this;
    },

    methods: {
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

      show (selectedId, latlng) {
        this.event.id = selectedId;
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
    }
  }
</script>

<style src="@/assets/css/form.css" scoped></style>
<style src="@/assets/css/home.css" scoped></style>



<!-- post request naar quarkus: localhost:xxxx/events -->