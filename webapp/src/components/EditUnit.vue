<template>
    <div class="grid">
        <section class="form-container">
            <h1 class="capitalize-block">Pas een <span class="capitalize-inline">unit</span> aan</h1>
                <form>
                    <input type="number" placeholder="id" class="as-text" :class="{error: !form.name.isValid}" v-model="form.id.value" />     
                    <input type="text" placeholder="naam" :class="{error: !form.name.isValid}" v-model="form.name.value" />
                    <select class="dropdown" v-model="form.teamIndex.value">
                        <option v-for="(team,index) in teams" v-bind:key="team.name" v-bind:value="index">{{ team.name }}</option>
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

export default {
    data(){
        return {
            form: {
                id: new FormField(isFilledIn()),
                name: new FormField(isFilledIn()),
                teamIndex: new FormField(0)
            },
            teams: [],
            /**
             * @type {FormHelper}
             */
            formHelper: null,
            /**
             * @type {TeamRestConnector}
             */
            restConnector: new TeamRestConnector()
        }
    },

    created(){
        this.formHelper = new FormHelper(this.form);
        this.restConnector.getTeams().then((response) => {
            this.teams = response.data;
        });
    },

    methods: {
        submit(){
            if(this.formHelper.validateForm()){
                alert('submitting');
                this.restConnector.updateUnit(this.form.name.value, this.form.id.value, this.teams[this.form.teamIndex.value].id).then(() => {
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