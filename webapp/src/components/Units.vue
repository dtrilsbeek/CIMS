<template>
    <div class="grid">
        <section class="form-container">
            <h1 class="capitalize-block">Maak een
                <span class="capitalize-inline">unit</span>
            </h1>
                <form>
                    <input type="text" placeholder="naam"
                           :class="{error: !form.name.isValid}"
                           v-model="form.name.value"
                    />
                    <select v-model="form.teamid.value" class="dropdown" :class="{error: !form.teamid.isValid}">
                        <option value="" disabled selected>Selecteer een team</option>
                        <option v-for="team in listOfTeams"
                                :key="team.id"
                                :value="team.id">
                            {{team.name}}
                        </option>
                    </select>
                </form>
            <button class="submit-form clickable" @click="submit">
                Voeg toe
            </button>
        </section>
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
            unitRestConnector: new UnitRestConnector(),
            teamRestConnector: new TeamRestConnector(),

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
                });
            }
        },

        getAllTeams(){
            this.teamRestConnector.getTeams().then((response) => {
                this.listOfTeams = response.data;
            }).catch((ex) => {
                console.log(ex);
            });
        }
    }
}
</script>

<style src="@/assets/css/form.css" scoped></style>