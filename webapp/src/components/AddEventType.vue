<template>
    <div class="wrapper">
            <h1 class="capitalize-block">Create a <span class="capitalize-inline">type</span></h1>
            <form>
                <input type="text" placeholder="name" :class="{error: !form.name.isValid}" v-model="form.name.value" />
                <textarea placeholder="description" :class="{error: !form.description.isValid}"  v-model="form.description.value" />
                <button class="submit-form clickable" type="button" @click="submit">Add</button>
            </form> 
    </div>
</template>

<script>
import FormField from '@/components/formvalidation/FormField'
import {isFilledIn} from '@/components/formvalidation/FormValidation'
import FormHelper from '@/components/formvalidation/FormHelper'
import TypeRestConnector from '@/components/rest/TypeRestConnector'

export default {
    data(){
        return {
            form: {
                name: new FormField(isFilledIn()),
                description: new FormField(isFilledIn())
            },
            /**
             * @type {FormHelper}
             */
            formHelper: null,
            restConnector: new TypeRestConnector(this.$token)
        }
    },

    created(){
        this.formHelper = new FormHelper(this.form);
    },

    methods: {
        submit(){
            if(this.formHelper.validateForm()){
                const result = this.restConnector.addType(this.form.name.value, this.form.description.value);
                result.then(() => {
                    this.formHelper.clearForm();
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