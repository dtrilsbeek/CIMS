<template>
    <div class="wrapper">
            <h1 class="capitalize-block">Maak een <span class="capitalize-inline">team</span></h1>
                <form>
                    <input type="text" placeholder="naam" :class="{error: !form.name.isValid}" v-model="form.name.value" />
                </form>
            <button class="submit-form clickable" @click="submit">Voeg toe</button>
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
                name: new FormField(isFilledIn())
            },
            /**
             * @type {FormHelper}
             */
            formHelper: null,
            restConnector: new TeamRestConnector(this.$token)
        }
    },

    created(){
        this.formHelper = new FormHelper(this.form);
    },

    methods: {
        submit(){
            if(this.formHelper.validateForm()){
                const result = this.restConnector.addTeam(this.form.name.value);
                result.then(() => {
                    this.formHelper.clearForm();
                });
            }
        }
    }
}
</script>

<style src="@/assets/css/form.css" scoped></style>