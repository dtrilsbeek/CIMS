<template>
    <div class="wrapper">
            <h1 class="capitalize-block">Create a <span class="capitalize-inline">team</span></h1>
            <form>
                <input type="text" placeholder="name" :class="{error: !form.name.isValid}" v-model="form.name.value" />
                <button class="submit-form clickable" type="button" @click="submit">Add</button>
            </form> 
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
                const name = this.form.name.value;
                const result = this.restConnector.addTeam(name);
                result.then(() => {
                    this.formHelper.clearForm();
                    this.$emit('alert', `Team ${name} added`);
                    this.$modal.hide('addTopic-modal');
                });
            }
        }
    }
}
</script>

<style src="@/assets/css/form.css" scoped></style>
<style scoped>
    form {
        width: 33%;
        min-width: 400px;
    }
</style>