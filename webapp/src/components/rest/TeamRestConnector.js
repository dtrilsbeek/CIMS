import Config from '@/components/rest/RestConfig'
import Axios from 'axios'
import AuthRestConnector from '@/components/rest/AuthRestConnector'

/**
 * Makes REST http-requests to the Teams resource
 */
class TeamRestConnector extends AuthRestConnector {

    constructor(token){
        super(token);
        this.baseUrl = Config.getUrl("teams");
        this.header = {'Content-Type': this.formUrlEncoded}
    }


    /**
     * Adds a new team
     * @param {string} name name of the new team
     * @returns {Promise} promise of HTTP response
     */
    addTeam(name){
        const toPost = this.toFormUrlEncoded({
            name: name
        });

        const headers = {
            'Content-Type': this.formUrlEncoded,
            'Authorization': `Bearer ${this.token}`
        }
        const promise = Axios.post(this.baseUrl, toPost, {headers: headers});
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