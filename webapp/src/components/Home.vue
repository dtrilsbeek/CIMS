<template>
  <div>

    <!-- <img alt="Vue logo" src="../assets/logo.png"> -->

    <modal :width="400" :height="500" name="addTopic-modal" class="modal" @before-open='beforeOpen()' @before-close='beforeClose()'>       
      <ul>
        <li><h1>Add situation</h1></li>
        <li><input type="number" v-model="id" placeholder="Input id"  /></li>
        <li><input type="number" v-model="lat" placeholder="Input latitude" readonly /></li>
        <li><input type="number" v-model="lon" placeholder="Input longitude" readonly /></li>
        <li><select v-model="type"><option v-for="number in 5" :key="number.number">{{number}}</option></select></li>

        <li><textarea v-model="description" placeholder="Input information"/></li>

        <li><button type="button" @click="JSONpost(getMessage())">Send</button></li>

      </ul>
    </modal>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'Home',
    props: {
    },

    data: function() {
      return {
        id: null,
        lat: 0,
        lon: 0,
        type: 0,
        description: "",
      }
    },

    mounted: function () {
    },

    created() {
      this.$root.$refs.home = this;
    },

    methods: {
      getMessage: function () {
        return {
          id: isNaN(parseInt(this.id)) ? null : parseInt(this.id),
          lat: parseFloat(this.lat),
          lon: parseFloat(this.lon),
          type: parseInt(this.type),
          description: this.description
        };
      },

      // Pushes posts to the server when called.
      JSONpost(message) {

        console.log(message);
        // console.log("test");

        // this should match the port in src/main/resources/application.properties
        axios.post(`http://localhost:8083/events`, message)
                .then(response => this.response = response.data)
                .catch(error => {
                  alert("error!");
                  console.log(error)
                  this.response = error
                });
        this.hide();
      },

      JSONput(message) {
        console.log(message);

        axios.put(`http://localhost:8083/events`, message)
                .then(response => this.response = response.data)
                .catch(error => {
                  alert("error!");
                  console.log(error)
                  this.response = error
                });
        this.hide();
      },

      show (latlng) {
        this.lat = latlng.lat;
        this.lon = latlng.lng;
        
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

<style src="@/assets/css/home.css" scoped></style>


<!-- post request naar quarkus: localhost:xxxx/events -->