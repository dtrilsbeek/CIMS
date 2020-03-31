import L from 'leaflet'
import MovingMarker from '@/components/leaflet/MovingMarker'
import { Icon } from 'leaflet';

const icons = {
    fireTruck : L.icon({
        iconUrl: 'https://i.imgur.com/ZJUWWnd.png', //Imgur for now, because can't find path to assets
        iconSize:     [50, 32], // size of the icon
        iconAnchor:   [32, 32], // point of the icon which will correspond to marker's location
        popupAnchor:  [10, -20] // point from which the popup should open relative to the iconAnchor
    })
};

// Fix for webpack not showing icon on map
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
     * @param {string} icon 
     * @param {Array<number>} start lat longs
     * @param {Array<number>} [destination] lat longs
     */
    constructor(icon, start, destination = start){
        super([start, start], 1000, {icon: icons[icon]});
        this.bindPopup('popup');
        this.destination = destination;
        
        // Rotate image to destination - (Vector rotation)
        // Remove from map
        // Vue integratie
    }

    /**
     * @param {Array<Number>} destination lat longs
     * @param {number} duration  milliseconds
     */
    moveTo(destination, duration = 10000){
        super.moveTo(destination, duration);
    }

    /**
     * Replace the current on click function with a new one
     * @param {function} fn 
     */
    setClick(fn){
        this.off('click');
        this.on('click', fn);
    }

}
