<template>
  <div>
      <ul>
        <li><h1>Add <span>Type</span></h1></li>
        <li><input class="as-text" v-model="type" placeholder="Input type" ></li>
        <li><button type="button" @click="addType()">Send</button></li>
      </ul>
  </div>
</template>

<script>
import ModalDao from '@/daos/ModalDao.js';

export default {

    data() {
        return {
            type: String
        }
    },

    mounted() {
        this.type = '';
    },

    methods: {
       addType() {
        ModalDao.addType(this.type)
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

<style src="@/assets/css/create-event.css" scoped></style>
<style src="@/assets/css/form.css" scoped></style>