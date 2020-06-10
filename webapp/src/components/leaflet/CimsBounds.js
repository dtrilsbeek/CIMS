import L from 'leaflet'

class CimsBounds extends L.LatLngBounds{
    
    
    constructor(northEast, southWest, zoomLevel){
        super(northEast, southWest);
        this.zoomLevel = zoomLevel;
    }

    isRegionVisible(regionBounds){
        return (
            super.overlaps(regionBounds)
        );
    }

}

export default CimsBounds