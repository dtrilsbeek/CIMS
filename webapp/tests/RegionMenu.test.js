import {shallowMount} from '@vue/test-utils'
import RegionMenu from '@/components/RegionMenu.vue'
import Region from '@/components/leaflet/Region'

let wrapper;
let regions;

beforeEach( () => {
    wrapper = shallowMount(RegionMenu);
    regions = [
        new Region("eindhoven"),
        new Region("helmond"),
        new Region("'s-hertogenbosch")
    ]
    wrapper.vm.regions = regions
});

describe('RegionMenu component', () => {
    
    test('is off by default', () => {
        expect(wrapper.vm.regionMenu.isActive).toBe(false);
    });
    
    test('can toggle on', () => {
        wrapper.vm.toggleMenu();
        expect(wrapper.vm.regionMenu.isActive).toBe(true);
    });


    test('can toggle off', () => {
        wrapper.vm.toggleMenu();
        wrapper.vm.toggleMenu();
        expect(wrapper.vm.regionMenu.isActive).toBe(false);
    });

    test('toggles off after switching region', () => {
        wrapper.vm.moveToBounds = jest.fn();
        const region = new Region('myRegion');
        wrapper.vm.switchRegion(region);
        expect(wrapper.vm.regionMenu.isActive).toBe(false);
    });

    test('calls move to bounds, when switching region', () => {
        const mockFn = jest.fn();
        wrapper.vm.moveToBounds = mockFn;
        const region = new Region('myRegion');
        wrapper.vm.switchRegion(region);
        expect(mockFn).toBeCalledTimes(1);
    });


    test('clears search bar after switching region', () => {
        const region = new Region('myRegion');
        wrapper.vm.moveToBounds = jest.fn();
        wrapper.vm.switchRegion(region);
        expect(wrapper.vm.regionMenu.text).toMatch(/^$/);
    });

    test('can switch current region', () => {
        const expected = 'testRegion';
        const region = new Region(expected);
        wrapper.vm.moveToBounds = jest.fn();

        let actual = '';
        wrapper.vm.switchRegion(region);
        actual = wrapper.vm.regionMenu.currentRegion;
        expect(actual).toBe(expected);
    });

    test('ignores current region in filter', () => {
        const regionName = "helmond"
        wrapper.vm.regionMenu.currentRegion = regionName;
        wrapper.vm.regionMenu.text = regionName;
        expect(wrapper.vm.filteredRegions).toHaveLength(0);
    });

    test('can filter on name start', () => {
        const regionName = "eindhoven";
        const filter = regionName.substr(0,4);
        wrapper.vm.regionMenu.currentRegion = "helmond";
        wrapper.vm.regionMenu.text = filter;
        expect(wrapper.vm.filteredRegions).toHaveLength(1);
    });

    test('can filter on middle of name', () => {
        const regionName = "eindhoven";
        const filter = regionName.substr(2,6);
        wrapper.vm.regionMenu.currentRegion = "helmond";
        wrapper.vm.regionMenu.text = filter;
        expect(wrapper.vm.filteredRegions).toHaveLength(1);
    });



});