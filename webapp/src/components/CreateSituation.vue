<template>
        <ul>
            <li><h1>Add <span>Situation</span></h1></li>
            <li>
                <input type="number" class="as-text"  step="any" 
                  placeholder="Input latitude"
                  :class="{error: !form.lat.isValid}"
                  v-model="form.lat.value"
              
              />
             </li>
            <li>
              <input type="number" class="as-text"  step="any" 
                placeholder="Input longitude"
                :class="{error: !form.lon.isValid}"
                v-model="form.lon.value"
              />
            </li>
            <li>
                <select v-model="form.typeId.value" class="dropdown" :class="{error: !form.typeId.isValid}">
                    <option value="" disabled selected>Selecteer een type</option>
                    <option v-for="type in types"
                            :key="type.id"
                            :value="type.id">
                        {{type.name}}
                    </option>
                </select>
            </li>

            <li><textarea  v-model="form.description.value" placeholder="Input information" :class="{error: !form.description.isValid}" /></li>

            <li><button type="button" @click="addEvent()" >Send</button></li>
    
            <li><h4 class="create-edit-toggle clickable" @click="toEdit()">Edit instead</h4></li>
      </ul>
</template>

<script>
import TypeRestConnector from "./rest/TypeRestConnector";
import EventRestConnector from "./rest/EventConnector";
import FormField from '@/components/formvalidation/FormField';
import {isFilledIn} from '@/components/formvalidation/FormValidation';
import FormHelper from '@/components/formvalidation/FormHelper';


export default {
    data () {
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
      this.formHelper = new FormHelper(this.form);
      this.id = this.event.id;
      this.form.lat.value = this.event.lat;
      this.form.lon.value = this.event.lon;
      this.getAllTypes();
    },
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
      toEdit(){
          this.$emit('toggle');
      },

      hide(){
          this.$modal.hide('addTopic-modal');
      }
    }
  }
</script>

<style src="@/assets/css/form.css" scoped></style>
<style src="@/assets/css/create-situation.css" scoped></style>