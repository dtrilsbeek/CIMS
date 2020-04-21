<template>
  <div>

  <!-- <img alt="Vue logo" src="../assets/logo.png"> -->

    <ul>
      <li>
        <h1>
          Add information to Kafka Topic
        </h1>
      </li>
      <li>
        <input ref="latitude" type="number" min="0" placeholder="Input latitude"/>
      </li> 
      <li>
         <input ref="longitude" type="number" min="0" placeholder="Input longitude"/>
      </li>
      <li>
        <input ref="type" type="number" min="0" placeholder="Input type"/>
      </li>
      <li>
        <textarea ref="information" placeholder="Input information"/>
      </li>
      <li>
        <button v-on:click='createTopicInformation()'>Send</button>
      </li>
      <li>
         {{kafkaTopicInfo}}
      </li>
    </ul>

  </div>
</template>

<script>
import Coordinates from '../models/Coordinates.js';
import KafkaTopicInfo from '../models/KafkaTopicInfo.js';

export default {
  name: 'Home',
  props: {
    // msg: String
  },
  data() {
    return {
      kafkaTopicInfo: KafkaTopicInfo
    }
  },
  methods: {
    createTopicInformation() {
        let refs = this.$refs;
        
        this.kafkaTopicInfo = new KafkaTopicInfo(
          new Coordinates( 
            refs.latitude.value, 
            refs.longitude.value, 
          ),
          refs.type.value, 
          refs.information.value
        );        
    }
  }
}
</script>

<style src="@/assets/css/home.css" scoped></style>
