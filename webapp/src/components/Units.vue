<template>
    <div class="wrapper">
            <h1 class="capitalize-block">Create a
                <span class="capitalize-inline">unit</span>
            </h1>
                <form>
                    <input type="text" placeholder="name"
                           :class="{error: !form.name.isValid}"
                           v-model="form.name.value"
                    />
                    <select v-model="form.teamid.value" class="dropdown" :class="{error: !form.teamid.isValid}">
                        <option value="" disabled selected>Select a team</option>
                        <option v-for="team in listOfTeams"
                                :key="team.id"
                                :value="team.id">
                            {{team.name}}
                        </option>
                    </select>
                </form>
            <button class="submit-form clickable" @click="submit">
                Add
            </button>
            <h4 class="create-edit-toggle clickable" @click="toEdit()">Edit instead</h4>
    </div>
</template>

<script>
import FormField from '@/components/formvalidation/FormField'
import {isFilledIn} from '@/components/formvalidation/FormValidation'
import FormHelper from '@/components/formvalidation/FormHelper'
import UnitRestConnector from "./rest/UnitRestConnector";
import TeamRestConnector from "./rest/TeamRestConnector";

export default {
    data(){
        return {
            form: {
                name: new FormField(isFilledIn()),
                teamid: new FormField(isFilledIn())
            },
            /**
             * @type {FormHelper}
             */
            formHelper: null,
            unitRestConnector: new UnitRestConnector(this.$token),
            teamRestConnector: new TeamRestConnector(this.$token),

            listOfTeams: [],

            selectedOption: null
        }
    },

    created(){
        this.formHelper = new FormHelper(this.form);
    },

    async mounted() {
        this.getAllTeams();
    },

    methods: {
        submit(){
            if(this.formHelper.validateForm()){
                this.unitRestConnector.addUnit(this.form.name.value, this.form.teamid.value).then(() => {
                    this.formHelper.clearForm();
                    this.$modal.hide('addTopic-modal');
                });
            }
        },

        getAllTeams(){
            this.teamRestConnector.getTeams().then((response) => {
                this.listOfTeams = response.data;
            }).catch((ex) => {
                console.log(ex);
            });
        },

        toEdit(){
            this.$emit('toggle');
        }
    }
}
</script>

<style src="@/assets/css/form.css" scoped></style>