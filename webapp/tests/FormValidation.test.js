import * as Fn from '@/components/formvalidation/FormValidation'
import FormField from '@/components/formvalidation/FormField'

/**
 * @type {FormField}
 */
let input;

beforeEach(() => {
    input = new FormField();
});


describe('isFilledIn', () => {

    const fn = Fn.isFilledIn();

    test('adds error if white character only string', () => {
        input.value = '     ';
        fn(input);

        expect(input.errors).toHaveLength(1);
    });

    test('adds error if empty string', () => {
        input.value = '';
        fn(input);

        expect(input.errors).toHaveLength(1);
    });

    test('adds error if NULL', () => {
        input.value = null;
        fn(input);

        expect(input.errors).toHaveLength(1);
    });


    test('adds error if undefined', () => {
        input.value = undefined;
        fn(input);

        expect(input.errors).toHaveLength(1);
    });


    test('no error on valid number', () => {
        input.value = 10;
        fn(input);

        expect(input.errors).toHaveLength(0);
    });

    test('no error on filled in string', () => {
        input.value = "a";
        fn(input);

        expect(input.errors).toHaveLength(0);
    });


});

describe('isEmail', () => {
    const fn = Fn.isEmail();

    test('adds error if invalid email', () => {
        input.value = "thisIsNotaValidEmail.com";
        fn(input);

        expect(input.errors).toHaveLength(1);
    });

    test('no error when valid email', () => {
        input.value = "myValid@email.com";
        fn(input);
        expect(input.errors).toHaveLength(0);
    });

});

describe('isSameAs', () => {
    const fn = Fn.isSameAs('other');

    test('adds error if different capitalization', () => {
        input.value = "password";
        input.otherValues.other = "Password";
        fn(input);
        expect(input.errors).toHaveLength(1);
    });

    test('adds error if different value', () => {
        input.value = "password";
        input.otherValues.other = "nopassword";
        fn(input);
        expect(input.errors).toHaveLength(1);
    });

    test('checks name of other value', () => {
        const value = 'email@example.com';
        const fun = Fn.isSameAs('value');
        input.value = value;
        input.otherValues.value = value;
        fun(input);
        expect(input.errors).toHaveLength(0);
    });
});

describe('minLength', () => {

    test('adds error if less than minimum length', () => {
        const fn = Fn.minLength(3);
        input.value = 'ab'
        fn(input);
        expect(input.errors).toHaveLength(1);
    });

    test('no error if equal to minimum length', () => {
        const fn = Fn.minLength(2);
        input.value = ':)'
        fn(input);
        expect(input.errors).toHaveLength(0);
    });

    test('no error if longer than minimum length', () => {
        const fn = Fn.minLength(5);
        input.value = ':):):)';
        fn(input);
        expect(input.errors).toHaveLength(0);
    });
});

describe('maxLength', () => {

    test('adds error if more than maximum length', () => {
        const fn = Fn.maxLength(2);
        input.value = 'oof'
        fn(input);
        expect(input.errors).toHaveLength(1);
    });

    test('no error if equal to max length', () => {
        const fn = Fn.maxLength(3);
        input.value = 'XQC'
        fn(input);
        expect(input.errors).toHaveLength(0);
    });

    test('no error shorter than max length', () => {
        const fn = Fn.maxLength(5);
        input.value = ';):)';
        fn(input);
        expect(input.errors).toHaveLength(0);
    });
});