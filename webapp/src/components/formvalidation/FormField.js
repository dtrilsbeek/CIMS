/**
 * Represents a field in an html form that can be validated.
 * Initiating the object this way improves readability in the Vue data object.
 */
class FormField{

    /**
     * @param firstParam can be the default value or a method from the FormValidation.js
     * @param args methods from the FormValidation.js,
     * to show what to validate in the form.
     */
    constructor(){
        this.isValid = true;
        this.errors = [];
        this.otherValues = {};
        this.validators = Array.from(arguments);
        this.setDefaultValue();
        this.createValidateFunction();
    }


    /**
     * Sets the standard value of the form field
     * @param {Boolean} toDefault default true
     */
    clear(toDefault = true){
        this.value = (toDefault) ? this.defaultValue : "";
    }

    /**
     * @private
     * Set the default value for the form field
     */
    setDefaultValue(){
        if(this.validators.length > 0 && ! (this.validators[0] instanceof Function) ){
            this.defaultValue = this.validators[0];
            this.validators.shift();
        }
        else{
            this.defaultValue = "";
        }

        this.value = this.defaultValue;
    }

    /**
     * @private
     * Create the function that validates the field
     * - clears errors on every call
     * - runs all validators
     * - checks if valid
     */
    createValidateFunction(){
        this.validate = function(){
            this.errors = [];
            this.validators.forEach(validate => {
                validate(this);
            });
            this.isValid = this.errors.length === 0;
        };
    }
}

export default FormField