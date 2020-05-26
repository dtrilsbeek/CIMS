import L from 'leaflet'
// import MovingMarker from '@/components/leaflet/MovingMarker'
import { Icon } from 'leaflet';

import MapDao from '@/daos/MapDao.js';
import CimsMarkerLogic from '@/logic/CimsMarkerLogic.js';
import MovingMarker from "./MovingMarker";

const icons = {
    fireTruck : L.icon({
        iconUrl: 'https://i.imgur.com/ZJUWWnd.png', //Imgur for now, because can't find path to assets
        iconSize:     [50, 32], // size of the icon
        iconAnchor:   [32, 32], // point of the icon which will correspond to marker's location
        popupAnchor:  [10, -20] // point from which the popup should open relative to the iconAnchor
    }),
    police : L.icon({
        iconUrl: 'https://i.imgur.com/JRm5PFJ.png', 
        iconSize:     [60, 60], 
        iconAnchor:   [32, 32], 
        popupAnchor:  [10, -20] 
    }),
    ambulance : L.icon({
        iconUrl: 'https://i.imgur.com/8tmRG6j.png', 
        iconSize:     [60, 60], 
        iconAnchor:   [32, 32], 
        popupAnchor:  [10, -20] 
    }),
    fire : L.icon({
        iconUrl: 'https://i.imgur.com/kViNvxN.png', 
        iconSize:     [60, 55], 
        iconAnchor:   [32, 32], 
        popupAnchor:  [10, -20] 
    })
};



// Fix for webpack not showing markers on the map
delete Icon.Default.prototype._getIconUrl;
Icon.Default.mergeOptions({
iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
iconUrl: require('leaflet/dist/images/marker-icon.png'),
shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
});


/**
 * Marker that can be placed on the map to represent a vehicle or a crisis scenario.
 * This marker is based on the moving marker plugin.
 */
export default class CimsMarker extends MovingMarker {


    /**
     * @param map
     * @param {Number} id
     * @param {String} type
     * @param {String} description
     * @param {Array<Number>} start lat longs
     * @param {Array<Number>} [destination] lat longs
     */
    constructor(map, id, type, description, start, destination = start){
        super([start, start], 1000, {icon: icons[type]});

        // this.bindPopupInfo(start[0], start[1], icon, description);

        this.map = map;
        this.id = id;
        this.type = type;
        this.description = description;
        this.destination = destination;
        this.isActive = false;

        this.on('click', () => {
            this.bindPopupInfo(this.getLatLng());
            this.map.setSelectedMarker(this);
            this.getElement().classList.add('active');
        });

        this.addTo(map.map);
        // Rotate image to destination - (Vector rotation)
        // Remove from map
    }

    /**
     * @param latlng
     */
    bindPopupInfo(latlng) {

        let lat = latlng.lat;
        let lng = latlng.lng;
        MapDao.getMarkerPosition(lat, lng)
            .then((response) => {

                let address = response.address;
                this.bindPopup(CimsMarkerLogic.createPopup(address, this.type, this.description, lat, lng));
            });
    }

    /**
     * @param {Array<Number>} destination lat longs
     * @param {number} duration  milliseconds
     */
    moveTo(destination, duration = 10000){
        super.moveTo(destination, duration)
        //refresh popup
        //this.bindPopupInfo(destination);
    }

    /**
     * Replace the current on click function with a new one
     * @param {function} fn 
     */
    setOnClick(fn){
        this.off('click');
        this.on('click', fn);
    }

}
