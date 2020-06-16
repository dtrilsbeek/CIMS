<template>
    <div class="wrapper">
        <h1 class="capitalize-block">Edit a <span class="capitalize-inline">unit</span></h1>
            <form>
                <input type="hidden" placeholder="id" class="as-text" :class="{error: !form.name.isValid}" v-model="form.id.value" />     
                <input type="text" placeholder="name" :class="{error: !form.name.isValid}" v-model="form.name.value" />
                <select class="dropdown" v-model="form.teamId.value" :class="{error: !form.teamId.isValid}">
                    <option value="" disabled selected>Select a team</option>
                    <option v-for="team in teams" :key="team.name" :value="team.id">{{ team.name }}</option>
                </select>
            </form>
        <button class="clickable capitalize-block" @click="submit" :class="{disabled: !selectedMarker}">
            update
            <div class="disabled-tooltip">No marker selected</div>
        </button>
        <h4 class="create-edit-toggle clickable" @click="toCreate()">Create instead</h4>
    </div>
</template>

<script>
import FormField from '@/components/formvalidation/FormField'
import {isFilledIn} from '@/components/formvalidation/FormValidation'
import FormHelper from '@/components/formvalidation/FormHelper'
import TeamRestConnector from '@/components/rest/TeamRestConnector'
import UnitRestConnector from '@/components/rest/UnitRestConnector'

export default {
    data(){
        return {
            form: {
                id: new FormField(isFilledIn()),
                name: new FormField(isFilledIn()),
                teamId: new FormField(isFilledIn())
            },
            teams: [],
            /**
             * @type {FormHelper}
             */
            formHelper: null,
            /**
             * @type {TeamRestConnector}
             */
            teamRestConnector: new TeamRestConnector(),
            /**
             * @type {UnitRestConnector}
             */
            unitRestConnector: new UnitRestConnector(this.$token)
        }
    },

    props: {
        selectedMarker: {
            type: [Object,Boolean],
            required: true
        },
        unit: {
            type: [Object, Boolean]
        }

    },

    mounted(){

        this.formHelper = new FormHelper(this.form);
        this.teamRestConnector.getTeams().then((response) => {
            this.teams = response.data;
        });

        if(this.unit){
            this.form.id.value = this.unit.id;
            this.form.name.value = this.unit.name;
            this.form.teamId.value = this.unit.team.id; 
        }
        else if(this.selectedMarker){
            this.form.id.value = this.selectedMarker.id;
        }
        else this.formHelper.clearForm();
    },

    methods: {
        submit(){
            if(this.formHelper.validateForm()){
                this.unitRestConnector.updateUnit(this.form.name.value, this.form.id.value, this.form.teamId.value).then(() => {
                    this.formHelper.clearForm();
                    this.$modal.hide('addTopic-modal');
                }).catch((ex) => {
                    console.log(ex);
                });
            }
        },

        toCreate(){
            this.$emit('toggle');
        },
    },
}
</script>

<style src="@/assets/css/form.css" scoped></style>