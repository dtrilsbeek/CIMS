import Config from '@/components/rest/RestConfig'
import Axios from 'axios'
import AuthRestConnector from '@/components/rest/AuthRestConnector';


/**
 * Makes REST http-requests to the Units resource
 */
class UnitRestConnector extends AuthRestConnector  {

    constructor(token){
        super(token);
        this.baseUrl = Config.getUrl("units");
        this.teamUrl = Config.getUrl("teams");
        this.header = {'Content-Type': this.formUrlEncoded}
    }


    /**
     * Adds a new team
     * @param {string} name -> name of the new unit
     * @param {Number} teamId -> teamId of the team this new unit belongs to
     * @returns {Promise} promise of HTTP response
     */
    addUnit(name, teamId){
        const toPost = this.toFormUrlEncoded({
            name: name,
            teamId: teamId
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
    updateUnit(name, unitId, teamId){
        const data = this.toFormUrlEncoded({
            name: name,
            teamId: teamId,
            unitId: unitId,
        });

        const headers = {
            'Content-Type': this.formUrlEncoded,
            // 'Authorization': `Bearer ${this.token}`
        }

        const result = Axios.put(Config.getUrl('units'), data, {headers: headers});
        return result;
    }
}

export default UnitRestConnector