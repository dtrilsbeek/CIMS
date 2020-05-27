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
                    <select v-model="form.teamid.value">
                        <option value="0" disabled selected>Select a team</option>
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

export default {
    data(){
        return {
            form: {
                name: new FormField(isFilledIn()),
                teamid: new FormField(0) // moet nog gefixed
            },
            /**
             * @type {FormHelper}
             */
            formHelper: null,
            restConnector: new UnitRestConnector(),

            listOfTeams: null,

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
                // console.log("Name: " + this.form.name.value);
                // console.log("TeamId: " + this.form.teamid.value);
                this.restConnector.addUnit(this.form.name.value, this.form.teamid.value);
            }
        },

        async getAllTeams(){
            try {
                const res = await this.restConnector.getTeams();
                this.listOfTeams = res.data;
            }
            catch (e) {console.error(e);}
        }
    }
}
</script>

<style src="@/assets/css/form.css" scoped></style>