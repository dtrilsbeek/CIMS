<template>
    <div class="grid">
        <section class="form-container">
            <h1 class="capitalize-block">Pas een <span class="capitalize-inline">unit</span> aan</h1>
                <form>
                    <input type="number" placeholder="id" class="as-text" :class="{error: !form.name.isValid}" v-model="form.id.value" />     
                    <input type="text" placeholder="naam" :class="{error: !form.name.isValid}" v-model="form.name.value" />
                    <select class="dropdown" v-model="form.teamId.value" :class="{error: !form.teamIdd.isValid}">
                        <option value="" disabled selected>Selecteer een team</option>
                        <option v-for="team in teams" :key="team.name" :value="team.id">{{ team.name }}</option>
                    </select>
                </form>
            <button class="submit-form clickable capitalize-block" @click="submit">pas aan</button>
        </section>
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

    created(){
        this.formHelper = new FormHelper(this.form);
        this.teamRestConnector.getTeams().then((response) => {
            this.teams = response.data;
        });
    },

    methods: {
        submit(){
            if(this.formHelper.validateForm()){
                this.unitRestConnector.updateUnit(this.form.name.value, this.form.id.value, this.form.teamId.value).then(() => {
                    this.formHelper.clearForm();
                }).catch((ex) => {
                    console.log(ex);
                });
            }
        }
    }
}
</script>

<style src="@/assets/css/form.css" scoped></style>