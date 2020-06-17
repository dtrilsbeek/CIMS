import L from 'leaflet'

export default class CimsImageOverlay extends L.imageOverlay {

    constructor(imageUrl, imageBounds) {
        super(imageUrl, imageBounds);
    }

}