<template>
    <div class="grid">
        <section class="form-container">
            <h1 class="capitalize-block">Maak een <span class="capitalize-inline">team</span></h1>
                <form>
                    <input type="text" placeholder="naam" :class="{error: !form.name.isValid}" v-model="form.name.value" />
                    <!-- <div class="flexbox-container">
                        <input type="number" class="as-text" placeholder="lat" />
                        <input type="number" class="as-text" placeholder="long" />
                    </div> -->
                    <!-- <select class="dropdown capitalize-block">
                        <option value="" >Politie bureau</option>
                        <option value="">Ziekenhuis</option>
                        <option value="">Brandweer kazerne</option>
                    </select> -->
                </form>
            <button class="submit-form clickable" @click="submit">Voeg toe</button>
        </section>
    </div>
</template>

<script>
import FormField from '@/components/formvalidation/FormField'
import {isFilledIn} from '@/components/formvalidation/FormValidation'
import FormHelper from '@/components/formvalidation/FormHelper'



export default {
    data(){
        return {
            form: {
                name: new FormField(isFilledIn())
            },
            formHelper: null
        }
    },

    created(){
        this.formHelper = new FormHelper(this.form);
    },

    methods: {
        submit(){
            if(this.formHelper.validateForm()){
                document.querySelector('form').submit();
            }
        }
    }
}
</script>

<style scoped>

input.error{
border-color:#D8000C;
}


button {
    background-color: #555555;
    color: white;
    border: none;
    width: 146px;
    padding: 10px 0;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin-top: 42px;
    font-weight: bolder;
    cursor: pointer;
    transition-duration: .3s;
}

.dropdown{
    margin-top: 8px; 
    border-radius: 8px;
    font-weight: bold;
    box-sizing: border-box;
    padding: 8px 0;
    font-size: 1em;
    border: 1.5px solid #555;
    width: 70%;
}

button:hover {
    background-color: #444444;
}


.grid{
    background-color: rgba(0, 0, 0, .8);
    width: 100%;
    height: 100%;
    position: relative;
}

.form-container{
    border: 1px solid silver;
    position: absolute;
    top: 12%;
    left: 50%;
    transform: translateX(-50%);
    height: 40%;
    background-color: #efefef;
    padding: 20px;
    width: 25%;
    border-radius: 20px;
}
 
form > *{
    display: block;
    margin: 0 auto;
}

/* form{
    border: 1px solid red;
} */

span{
    font-weight: bolder;
}

input{
    box-sizing: border-box;
    border: 1.5px solid #555;
    margin-top: 18px;
    display: block;
    width: 70%;
    height: 30px;
    padding: 18px 4px 18px 8px;
    border-radius: 5px;
    font-size: 1.5em;
}

.flexbox-container{
    display: flex;
    width: 70%;
    justify-content: space-between;
}

.flexbox-container > input{
    width: 48%;
}

h1{
    font-size: 2em;
    color: #333;
    margin-bottom: 42px;
}


input.as-text::-webkit-outer-spin-button,
input.as-text::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

input.as-text[type=number] {
  -moz-appearance: textfield;
}

</style>