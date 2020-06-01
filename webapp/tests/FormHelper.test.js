import FormField from '@/components/formvalidation/FormField'
import * as Fv from '@/components/formvalidation/FormValidation'
import FormHelper from '@/components/formvalidation/FormHelper'


let form;

/**
 * @type {FormHelper}
 */
let helper;

beforeEach(() => {
    form = {
        input_1: new FormField('default_1', Fv.isFilledIn()),
        input_2: new FormField('default_2', Fv.isFilledIn(), Fv.minLength(5)),
        input_3: new FormField('default_2', Fv.maxLength(3)),
    }

    helper = new FormHelper(form);
});

describe('FormHelper', () => {

    test('clears to default value', () => {
        const expected = {
            input_1: 'default_1',
            input_2: 'default_2'
        };

        form.input_1 = new FormField('default_1'),
        form.input_2 = new FormField('default_2'),
        
        form.input_1.value = 'changed';
        form.input_2.value = 'changed';

        helper.clearForm();

        let actual = {
            input_1: form.input_1.value,
            input_2: form.input_2.value,
        }

        expect(actual).toEqual(expected);
    });

    test('is valid if all fields are valid', () => {
        form.input_3.value = 'XQC';
        const isFormValid = helper.validateForm();
        expect(isFormValid).toEqual(true);
    });

    test('is invalid if some fields are invalid', () => {
        const isFormValid = helper.validateForm();
        expect(isFormValid).toEqual(false);
    });


});