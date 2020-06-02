import { shallowMount } from '@vue/test-utils';
import ActiveEvents from '@/components/ActiveEvents.vue';
import Vue from 'vue';
import testEvents from './testEvents.js';

const wrapper = shallowMount(ActiveEvents, {
    propsData: {
        bus: new Vue()
    }
});

describe('ActiveEvents', () => {

    // Inspect the raw component options
    test('has a created hook', () => {

      //isVueInstance is deprecated
      expect(wrapper.exists).toBeTruthy();
      // If it should pass with deep equality, replace "toBe" with "toStrictEqual"
      expect(typeof ActiveEvents.created).toBe('function');
    })

    test('sets the correct data values', () => {

        expect(typeof ActiveEvents.data).toBe('function');

        wrapper.setData({events: testEvents});

        const compEvents = wrapper.vm.events;
        
        // for single expect(array).toContain(1);
        expect(compEvents).toEqual(expect.arrayContaining(testEvents));
        expect(compEvents.length).toBe(3);
    })

    test('Template displays correct html', () => {

        const compEvents = wrapper.vm.events;
        let expectedHtml = `Total: ${compEvents.length}`;
 
        expect(wrapper.find('div').text()).toContain(expectedHtml);
        expect(wrapper.find('ul').exists).toBeTruthy();
    })
});