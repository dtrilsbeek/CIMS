/*
    File that holds all the methods for validating a form.
    Methods that are needed for the form should be imported
*/


/**
 * Adds an error if the value is empty
 * @param {String} customError custom error message, overrides default message
 */
export function isFilledIn(customError){
    return (input) => {
        const value = input.value;
        if(value == null ||  /^[^\s]+(\s+[^\s]+)*$/.test(value) == false){
            addToErrors(input, "This field is required", customError);
        }
    }
}

/**
 * Adds an error if the values is not an email
 * @param {String} customError custom error message, overrides default message
 */
export function isEmail(customError){
    return (input) => {
        const value = input.value;
        const regex = /\S+@\S+\.\S+/;

        if(! regex.test(value)){
            addToErrors(input, 'This needs to be a valid email', customError);
        }
    }
}

/**
 * Adds an error if the values are not the same
 * @param {String} sameAs value that the input will be compared to
 * @param {String} customError custom error message, overrides default message
 */
export function isSameAs(sameAs, customError){
    return (input) => {
        const value = input.value;
        const valueOfOther = input.otherValues[sameAs];
        if(value !== valueOfOther){
            addToErrors(input, `${value} is not the same is ${sameAs}`, customError)
        }
    }
}

/**
 * Adds an error if the value does not atleast have the minimum length
 * @param {Number} minLength the mininum length the input has to be 
 * @param {String} customError custom error message, overrides default message
 */
export function minLength(minLength, customError){
    return (input) => {
        const value = input.value;
        if(value.length < minLength){
            addToErrors(input, `Requires a minimum length of ${length}`, customError);
        }
    }
}

/**
 * Adds an error if the values is longer than the maximum length
 * @param {Number} maxLength the maximum length that the input should not exceed
 * @param {String} customError custom error message, overrides default message
 */
export function maxLength(maxLength, customError){
    return (input) => {
        const value = input.value;
        if(value.length > maxLength){
            addToErrors(input, `Exceeds the maximum length of ${length}`, customError);
        }
    }
}


/**
 * @private
 * Adds the error message to the formField
 * @param {Object} input the form field will get the error
 * @param {String} errorMsg the default error message
 * @param {String} customError custom error message, overrides default message
 */
function addToErrors(input, errorMsg, customError){
        const error = customError || errorMsg;
        input.errors.push(error);
}