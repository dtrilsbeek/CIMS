/**
 * Class based on the leaflet map
 * Made it easier to innitialise with the needs for the CIMS project.
 */
class CimsMap extends L.Map {

    constructor(view, zoom = 25){
        super('map', {
            center: view,
            zoom: zoom + 1,
            preferCanvas: true
        });
        this.initLayer();
        this.setZoom(zoom); // Changing zoom immediately to prevent map bug loading with gray screen
    }

    initLayer(){
        const tileUrl = 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
        const layer = new L.TileLayer(tileUrl,
        {
            attribution: 'Maps © <a href=\"www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors'
        });

        this.addLayer(layer);
    }

}