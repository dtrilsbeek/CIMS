const icons = {
    fireTruck : L.icon({
        iconUrl: 'fire-truck.png',
        iconSize:     [50, 32], // size of the icon
        iconAnchor:   [32, 32], // point of the icon which will correspond to marker's location
        popupAnchor:  [10, -20] // point from which the popup should open relative to the iconAnchor
    })
};

/**
 * Marker that can be placed on the map to represent a vehicle or a crisis scenario.
 * This marker is based on the moving marker plugin.
 */
class CimsMarker extends L.Marker.MovingMarker {

    /**
     * @param {string} icon 
     * @param {Array<number>} start lat longs
     * @param {Array<number>} [destination] lat longs
     */
    constructor(icon, start, destination = start){
        super([start, start], 1000, {icon: icons[icon]});
        this.bindPopup('popup');
        this.destination = destination;
        
        this.on('click', () => {
            this.showPopup();
            alert('clicked!');
            this.moveTo(this.destination);
        });


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
