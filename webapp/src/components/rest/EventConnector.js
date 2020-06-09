import Config from '@/components/rest/RestConfig'
import Axios from 'axios'
import AuthRestConnector from '@/components/rest/AuthRestConnector';


/**
 * Makes REST http-requests to the Units resource
 */
class EventRestConnector extends AuthRestConnector  {

    constructor(token){
        super(token);
        this.baseUrl = Config.getUrl("events");

        this.header = {'Content-Type': this.formUrlEncoded}
    }


    /**
     * Adds an event
     * @param {Number} lat 
     * @param {Number} lon 
     * @param {Number} typeId 
     * @param {String} description
     */
    addEvent(lat, lon, typeId, description){
        const toPost = this.toFormUrlEncoded({
            lat: lat,
            lon: lon,
            typeId: typeId,
            description: description
        });

        const headers = {
            'Content-Type': this.formUrlEncoded
        }

        const promise = Axios.post(this.baseUrl, toPost, {headers: headers});
        return promise;
    }

    /**
     * Updates an event
     * @param {Number} id 
     * @param {Number} lat 
     * @param {Number} lon 
     * @param {Number} typeId 
     * @param {String} description
     */
    updateEvent(id, lat, lon, typeId, status, description){
        const data = this.toFormUrlEncoded({
            id: id,
            lat: lat,
            lon: lon,
            typeId: typeId,
            status: status,
            description: description
        });

        const headers = {
            'Content-Type': this.formUrlEncoded
        }

        const result = Axios.put(this.baseUrl, data, {headers: headers});
        return result;
    }
}

export default EventRestConnector