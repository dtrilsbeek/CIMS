import Config from '@/components/rest/RestConfig'
import Axios from 'axios'
import AuthRestConnector from '@/components/rest/AuthRestConnector';


/**
 * Makes REST http-requests to the Units resource
 */
class TypeRestConnector extends AuthRestConnector  {

    constructor(token){
        super(token);
        this.baseUrl = Config.getUrl("types");
        this.header = {'Content-Type': this.formUrlEncoded}
    }


    /**
     * Adds a new team
     * @param {string} name -> name of the new unit
     * @param {Number} teamId -> teamId of the team this new unit belongs to
     * @returns {Promise} promise of HTTP response
     */
    addType(name, description, icon){
        const toPost = this.toFormUrlEncoded({
            name: name,
            description: description,
            icon: icon
        });


        const headers = {
            'Content-Type': this.formUrlEncoded,
            // 'Authorization': `Bearer ${this.token}`
        }

        const promise = Axios.post(this.baseUrl, toPost, {headers: headers});
        return promise;
    }

    /**
     * 
     * @param {String} name 
     * @param {Number} unitId 
     * @param {Number} teamId 
     */
    updateType(name, typeId, description, icon){
        const data = this.toFormUrlEncoded({
            name: name,
            typeId: typeId,
            description: description,
            icon: icon
        });

        const headers = {
            'Content-Type': this.formUrlEncoded,
            // 'Authorization': `Bearer ${this.token}`
        }

        const result = Axios.put(Config.getUrl('units'), data, {headers: headers});
        return result;
    }


    /**
     * @returns {Promise} return a promise with all available types as data
     */
    getTypes(){
        const result = Axios.get(this.baseUrl);
        return result;
    }
}

export default TypeRestConnector