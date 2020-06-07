export default class Event {

    constructor(id, lat, lon, type, description) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.description = description;
    }

    /**
     * @param {Boolean} resetId id is set to undefined when true - default false
     * @returns {Event} a deepcopy of the event this method is called on
     */
    deepCopy(resetId = false){
        const id = (resetId) ? undefined : this.id;
        return new Event(id, this.lat, this.lon, this.type, this.description);
    }
}