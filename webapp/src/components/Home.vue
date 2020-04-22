<template>
  <div>

  <!-- <img alt="Vue logo" src="../assets/logo.png"> -->

    <modal :width="330" :height="260" name="addTopic-modal" class="modal" @before-open='beforeOpen()' @before-close='beforeClose()'>       
      <ul>
        <li>
          <h1>
            Add a topic
          </h1>
        </li>
        <li>
          <input type="number" min="0" placeholder="Input latitude"/>
        </li> 
        <li>
        <input type="number" min="0" placeholder="Input longitude"/>
        </li>
        <li>  
          <input type="number" min="0" placeholder="Input type"/>
        </li>
        <li>
          <textarea placeholder="Input information"/>
        </li>
        <li>
          <button v-on:click='sendKafkaTopic()'>Send</button>
          <button v-on:click='hide()'>Close</button>
        </li>
      </ul>
    </modal>
  </div>
</template>

<script>
export default {
  name: 'Home',
  props: {
    // msg: String
  },
  methods: {
    show () {
      this.$modal.show('addTopic-modal');
        //params can be added with ", { foo: 'bar' })"
    },
    hide () {
      this.$modal.hide('addTopic-modal');
    },
    sendKafkaTopic() {
      console.log('this will be called when the user submits');
    },
    beforeOpen (/*event*/) {
      // console.log(event.params.foo);
    },
    beforeClose() {
      console.log('this will be called before the modal closes');
    }
  },
  created() {
    this.$root.$refs.home = this;
  }

}
</script>

<style src="@/assets/css/home.css" scoped></style>
