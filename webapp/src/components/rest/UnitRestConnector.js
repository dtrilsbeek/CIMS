import Config from '@/components/rest/RestConfig'
import Axios from 'axios'
import RestConnector from '@/components/rest/RestConnector';


/**
 * Makes REST http-requests to the Units resource
 */
class UnitRestConnector extends RestConnector  {

    constructor(token){
        super(token);
        this.baseUrl = Config.getUrl("units");
        this.teamUrl = Config.getUrl("teams");
        this.header = {'Content-Type': this.formUrlEncoded}
    }


    /**
     * Get units by teamId
     * @param {Number} teamId -> teamId of the units
     * @returns {Promise} promise of HTTP response
     */
    getUnitsByTeamId(teamId){

        const promise = Axios.get(`${this.baseUrl}?teamId=${teamId}`);
        
        return promise;
    }


    /**
     * Adds a new team
     * @param {string} name -> name of the new unit
     * @param {Number} teamId -> teamId of the team this new unit belongs to
     * @param {string} token the auth token
     * @returns {Promise} promise of HTTP response
     */
    addUnit(name, teamId, token){
        const toPost = this.toFormUrlEncoded({
            name: name,
            teamId: teamId
        });


        const headers = {
            'Content-Type': this.formUrlEncoded,
            'Authorization': `Bearer ${token}`
        }

        const promise = Axios.post(this.baseUrl, toPost, {headers: headers});
        return promise;
    }

    /**
     * Removes an existing unit
     * @param {number} id of the to be deleted unit
     * @param {string} token the auth token
     * @returns {Promise} promise of HTTP response
     */
    removeUnit(unitId, token) {
   
        const headers = {
            'Authorization': `Bearer ${token}`
        }
        const promise = Axios.delete(`${this.baseUrl}/${unitId}`, {headers: headers});
        return promise;
    }

    /**
     * 
     * @param {String} name 
     * @param {Number} unitId 
     * @param {Number} teamId
     * @param {string} token the auth token 
     */
    updateUnit(name, unitId, teamId, token){
        const data = this.toFormUrlEncoded({
            name: name,
            teamId: teamId,
            unitId: unitId,
        });

        const headers = {
            'Content-Type': this.formUrlEncoded,
            'Authorization': `Bearer ${token}`
        }

        const result = Axios.put(Config.getUrl('units'), data, {headers: headers});
        return result;
    }
}

export default UnitRestConnector