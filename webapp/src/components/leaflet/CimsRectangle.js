import L from 'leaflet';

export default class CimsRectangle extends L.rectangle {

    constructor(bounds, color, weight) {
        super(bounds, {color, weight})
    }

    // event is event object
    // e.type === 'click'
    // e.lanlng === L.LatLng on map
    // e.target.getLatLngs() - your rectangle coordinates
    // but e.target !== rect      
    /**
     * Replace the current on click function with a new one
     * @param {function} fn 
     */
    setOnClick(fn){
        this.off('click');
        this.on('click', fn);
    }
}
