import FormField from '@/components/formvalidation/FormField'
import * as Fv from '@/components/formvalidation/FormValidation'

describe('FormField default values', () => {
    test('can set default value', () => {
        const defaultValue = 'choose a username';
        const input = new FormField(defaultValue);
        expect(input.value).toBe(defaultValue);
    });

    test('can clear to empty string', () => {
        const input = new FormField('fill in password');
        input.clear(false);

        expect(input.value).toBe('');
    });

    test('can clear to default value', () => {
        const input = new FormField(10);
        input.clear();
        expect(input.value).toBe(10);
    });
});

describe('FormField validation', () => {

    test('can be invalid', () => {
        const input = new FormField(Fv.isFilledIn());
        input.validate();
        expect(input.isValid).toBe(false);
    });

    test('checks on all validations', () => {
        const input = new FormField('XQC', Fv.isFilledIn(), Fv.minLength(4));
        input.validate();
        expect(input.errors).toHaveLength(1);
    });

    test('can be valid after input change', () => {
        const input = new FormField('XQC', Fv.isFilledIn(), Fv.minLength(4));
        input.validate();
        input.value = "7777";
        input.validate();
        expect(input.isValid).toBe(true);
    });

});