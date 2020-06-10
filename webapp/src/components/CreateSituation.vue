<template>
  <ul>
      <li><h1>Add <span>Situation</span></h1></li>
      <li><input type="number" class="as-text" v-model="event.lat" step="any" placeholder="Input latitude" ></li>
      <li><input type="number" class="as-text" v-model="event.lon" step="any" placeholder="Input longitude" ></li>

      <li>
          <select  class="dropdown">
              <option v-for="number in 5" :key="number.number">{{number}}</option>
          </select>
      </li>

      <li><textarea  placeholder="Input information"/></li>

      <li><button type="button" @click="addEvent()" >Send</button></li>
      <li><button type="button" @click="putEvent()" :class="{disabled: !selectedMarker}" >
          <div class="disabled-tooltip">No marker selected</div>
          Move selected marker
          </button></li>
    </ul>
</template>

<script>
import ModalDao from '@/daos/ModalDao.js';

export default {
    props:{
        selectedMarker: {
            type: [Object,Boolean],
            required: true
        },

        event: {
            type: Object,
            required: true
        }
    },

    methods: {
      addEvent() {
        ModalDao.addEvent(this.event)
          .catch(error => {
            console.log(error);
          });
          this.hide();
      },

      hide(){
          this.$emit('hide');
      }
    }
}
</script>

<style src="@/assets/css/form.css" scoped></style>
<style src="@/assets/css/create-situation.css" scoped></style>