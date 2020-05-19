/**
 * Class for validating and helping function for a form
 */
class FormHelper{

    /**
     * @param {Object} form - the form to handle
     */
    constructor(form){
        this.form = form;
    }

    /**
     * Validates the form and shares necessary form values
     * @returns true if there are no errors in the entire form - false otherwise
     */
    validateForm(){
        const values = {};
        let isFormValid = true;
        for(const prop in this.form){
            const formField = this.form[prop];
            formField.otherValues = values;
            values[prop] = formField.value; 
            formField.validate();

            if(formField.errors.length > 0){
                isFormValid = false;
            }
        }

        return isFormValid;
    }
    

    /**
     * Empties all form values
     */
    clearForm(){
        for(const prop in this.form){
            const formField = this.form[prop];
            formField.value = "";
        }
    }

}

export default FormHelper