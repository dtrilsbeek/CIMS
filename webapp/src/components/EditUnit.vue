<template>
    <div class="wrapper">
            <h1 class="capitalize-block">Edit a <span class="capitalize-inline">unit</span></h1>
                <form>
                    <input ref="id" type="hidden" placeholder="id" class="as-text" :class="{error: !form.name.isValid}" v-model="form.id.value" />     
                    <input ref="name" type="text" placeholder="name" :class="{error: !form.name.isValid}" v-model="form.name.value" />
                    <select class="dropdown" v-model="form.teamId.value" :class="{error: !form.teamId.isValid}">
                        <option value="" disabled selected>Select a team</option>
                        <option v-for="team in teams" :key="team.name" ref="teamId" :value="team.id">{{ team.name }}</option>
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
        }
    },

    mounted(){

        if(this.selectedMarker != null){
            this.form.id.value = this.selectedMarker.id;
        }
        this.formHelper = new FormHelper(this.form);
        this.teamRestConnector.getTeams().then((response) => {
            this.teams = response.data;
        });

        this.$root.$on('edit-unit', unit => {
            this.modifyExistingUnit(unit);
        });
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

        modifyExistingUnit(unit) {
            let refs = this.$refs;
            
            refs.id.value = unit.id;
            refs.name.value = unit.name;
            refs.teamId = unit.team.id;
        }
    },
}
</script>

<style src="@/assets/css/form.css" scoped></style>