<template>
        <ul>
            <li><h1>update <span>Situation</span></h1></li>
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
                <select v-model="form.status.value" class="dropdown" :class="{error: !form.status.isValid}">
                    <option value="" disabled selected>Selecteer een status</option>
                    <option value="ACTIVE"  selected>actief</option>
                    <option value="FINISHED"  selected>afgerond</option>
                </select>
            </li>

            <li><textarea  v-model="form.description.value" placeholder="Input information" :class="{error: !form.description.isValid}" /></li>
            <li><button type="button" @click="putEvent()" :class="{disabled: !selectedMarker}" >
                <div class="disabled-tooltip">No marker selected</div>
                Move selected marker
                </button></li>
            <li> <h4 class="create-edit-toggle clickable" @click="toCreate()">create instead</h4></li>
      </ul>
</template>

<script>

import EventRestConnector from "./rest/EventConnector";
import FormField from '@/components/formvalidation/FormField';
import {isFilledIn} from '@/components/formvalidation/FormValidation';
import FormHelper from '@/components/formvalidation/FormHelper';


export default {
    data () {
      return {
        id: 0,
        typeId: 0,
        form: {
            lat: new FormField(isFilledIn()),
            lon: new FormField(isFilledIn()),
            status: new FormField(isFilledIn()),
            description: new FormField(isFilledIn())
        },
        formHelper: null,
        eventRestConnector: new EventRestConnector(this.$token),
      }
    },
    created() {
        if(this.event.type !== undefined) {
            this.formHelper     = new FormHelper(this.form);
            this.id             = this.event.id;
            this.typeId         = this.event.type.id;
            this.form.lat.value = this.event.lat;
            this.form.lon.value = this.event.lon;
        }
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
        putEvent() {
            if(this.formHelper.validateForm()) {
                const id          = this.id;
                const lat         = this.form.lat.value;
                const lon         = this.form.lon.value;
                const typeId      = this.typeId;
                const status      = this.form.status.value;
                const description = this.form.description.value;

                this.eventRestConnector.updateEvent(id, lat, lon, typeId, status, description);

                this.formHelper.clearForm();
                this.hide();
            }
        },

        hide(){
            this.$modal.hide('addTopic-modal');
        },

        toCreate(){
            this.$emit('toggle');
        }
    }
  }
</script>

<style src="@/assets/css/form.css" scoped></style>
<style src="@/assets/css/create-situation.css" scoped></style>