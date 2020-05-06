<template>
  <div>

    <!-- <img alt="Vue logo" src="../assets/logo.png"> -->

    <modal :width="400" :height="500" name="addTopic-modal" class="modal" @before-open='beforeOpen()' @before-close='beforeClose()'>       
      <ul>
        <li><h1>Testing Input, Get en Post</h1></li>

        <li><input type="number" v-model="lat" placeholder="Input latitude" readonly /></li>
        <li><input type="number" v-model="lon" placeholder="Input longitude" readonly /></li>
        <li><select v-model="type"><option v-for="number in 5" :key="number.number">{{number}}</option></select></li>

        <li><textarea v-model="description" placeholder="Input information"/></li>

        <li><button type="button" @click="JSONpost(getMessage())">Send</button></li>
        <li>
          <p v-text="object_to_send"></p>
          <h3>--------------------------------------------------</h3>
          <p v-text="response"></p>
        </li>

        <li><button type="button" @click="JSONget()">Get</button></li>
        <li v-for="obj in messagesgot" v-bind:key="obj.lat">
          <p v-text="obj"></p>
        </li>

        <li>
          <h3>--------------------------------------------------</h3>
          <p v-if="messagesgot.length >= 1">So the first lon recieved is: {{messagesgot[0].lon}}</p>
          <p v-if="messagesgot.length >= 2">And the second description recieved is: {{messagesgot[1].description}}</p>
        </li>
      </ul>
    </modal>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'Home',
    props: {
      // msg: String
    },

    data: function() {
      return {
        lat: 0,
        lon: 0,
        type: 0,
        description: "",

        voorbeeld: null,

        object_to_send: null,
        response: null,

        messagesgot: []
      }
    },

    mounted: function () {
      //this.JSONget();
    },

    created() {
      this.$root.$refs.home = this;
    },

    methods: {
      getMessage: function () {
        return {
          lat: parseFloat(this.lat),
          lon: parseFloat(this.lon),
          type: parseInt(this.type),
          description: this.description
        };
      },

      JSONget(){
        // this should match the port in src/main/resources/application.properties
        axios.get('http://localhost:8080/events/testing')
                // .then(response => (this.messagesgot = response.data.results))
                // .catch(error => this.messagesgot = error)
                .then(response => (
                        alert("OK!"),
                        console.log(response),
                        this.messagesgot = response.data
                ))
                .catch(error => (
                        alert("error!"),
                        console.log(error)
                ))
                .finally(() => console.log('Data loading complete'));
      },

      // Pushes posts to the server when called.
      JSONpost(message) {

        // this should match the port in src/main/resources/application.properties
        axios.post(`http://localhost:8080/events/testing`, message)
                .then(response => this.response = response.data)
                .catch(error => { 
                  alert("error!"),
                  console.log(error)
                  this.response = error
                });

        // async / await version (postPost() becomes async postPost())
        //
        // try {
        //   await axios.post(`http://jsonplaceholder.typicode.com/posts`, {
        //     body: this.postBody
        //   })
        // } catch (e) {
        //   this.errors.push(e)
        // }
      },
      show (latlng) {
        console.log(latlng);
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