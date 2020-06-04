<template>
  <div>

    <!-- <img alt="Vue logo" src="../assets/logo.png"> -->

    <modal :width="400" :height="500" name="addTopic-modal" class="modal" @before-open='beforeOpen()' @before-close='beforeClose()'>
      <span class="close" @click="$modal.hide('addTopic-modal')">X</span>
 
            <h1 class="capitalize-block">Maak een
                <span class="capitalize-inline">unit</span>
            </h1>
                <form>
                    <input type="number" placeholder="Input latitude"
                           :class="{error: !form.lat.isValid}"
                           v-model="form.lat.value"
                    />
                    <input type="number" placeholder="Input longitude"
                           :class="{error: !form.lon.isValid}"
                           v-model="form.lon.value"
                    />
                    <select v-model="form.typeId.value" class="dropdown" :class="{error: !form.typeId.isValid}">
                      <option value="" disabled selected>Selecteer een type</option>
                      <option v-for="type in types"
                              :key="type.id"
                              :value="type.id">
                          {{type.name}}
                      </option>
                    </select>
                    <textarea v-model="form.description.value" placeholder="Input information" :class="{error: !form.description.isValid}" />
                </form>
            <button type="button" v-on:click="addEvent()">Send</button>
            <button type="button" v-on:click="putEvent()">Move selected marker</button>
      
    



  
    </modal>
  </div>
</template>

<script>
  import TypeRestConnector from "./rest/TypeRestConnector";
  import EventRestConnector from "./rest/EventConnector";
  import FormField from '@/components/formvalidation/FormField';
  import {isFilledIn} from '@/components/formvalidation/FormValidation';
  import FormHelper from '@/components/formvalidation/FormHelper';

  export default {
    name: 'Home',
    props: {
        bus: Object
    },
    data() {
      return {
        id: 0,
        form: {
          lat: new FormField(isFilledIn()),
          lon: new FormField(isFilledIn()),
          typeId: new FormField(isFilledIn()),
          description: new FormField(isFilledIn())
        },
        formHelper: null,
        typeRestConnector: new TypeRestConnector(this.$token),
        eventRestConnector: new EventRestConnector(this.$token),
        types: [],

      }
    },

    created() {
        this.$root.$refs.home = this;
        this.formHelper = new FormHelper(this.form);
    },

    methods: {
      getAllTypes() {
        this.typeRestConnector.getTypes().then(res => {
          this.types = res.data; 
        }).catch(e => console.error(e));
      },
      addEvent() {

        if(this.formHelper.validateForm()) {
          const lat = this.form.lat.value;
          const lon = this.form.lon.value;
          const typeId = this.form.typeId.value;
          const description = this.form.description.value;

          this.eventRestConnector.addEvent(lat, lon, typeId, description);
          this.formHelper.clearForm();
          this.hide();
        }

        
      },

      putEvent() {
        if(this.formHelper.validateForm()) {
          const id = this.id;
          const lat = this.form.lat.value;
          const lon = this.form.lon.value;
          const typeId = this.form.typeId.value;
          const status = "ACTIVE";
          const description = this.form.description.value;

          this.eventRestConnector.updateEvent(id, lat, lon, typeId, status, description);
          this.formHelper.clearForm();
          this.hide();
        }
      },

      show (selectedId, latlng) {
        this.id = selectedId;
        this.form.lat.value = latlng.lat;
        this.form.lon.value = latlng.lng;

        this.$modal.show('addTopic-modal');
        //params can be added with ", { foo: 'bar' })"
      },
      hide () {
        this.$modal.hide('addTopic-modal');
      },
      async beforeOpen (/*event*/) {
         this.getAllTypes();
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