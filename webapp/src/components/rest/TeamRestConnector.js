import Config from '@/components/rest/RestConfig'
import Axios from 'axios'
import RestConnector from '@/components/rest/RestConnector'

/**
 * Makes REST http-requests to the Teams resource
 */
class TeamRestConnector extends RestConnector {

    constructor(){
        super();
        this.baseUrl = Config.getUrl("teams");
        this.header = {'Content-Type': this.formUrlEncoded}
    }


    /**
     * Adds a new team
     * @param {string} name name of the new team
     *  @param {string} token the auth token
     * @returns {Promise} promise of HTTP response
     */
    addTeam(name, token){
        const toPost = this.toFormUrlEncoded({
            name: name
        });

        const headers = {
            'Content-Type': this.formUrlEncoded,
            'Authorization': `Bearer ${token}`
        }
        const promise = Axios.post(this.baseUrl, toPost, {headers: headers});
        return promise;
    }

     /**
     * Removes an existing team
     * @param {number} id of the to be deleted team
     * @returns {Promise} promise of HTTP response
     */
    removeTeam(teamId, token) {
   
        const headers = {
            'Authorization': `Bearer ${token}`
        }
        const promise = Axios.delete(`${this.baseUrl}/${teamId}`, {headers: headers});
        return promise;
    }


    /**
     * @returns {Promise} return a promise with all available teams as data
     */
    getTeams(){
        const result = Axios.get(this.baseUrl);
        return result;
    }



}

export default TeamRestConnector