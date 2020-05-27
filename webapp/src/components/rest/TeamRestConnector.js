import Config from '@/components/rest/RestConfig'
import Axios from 'axios'
import Qs from 'qs' // For converting to form url encoded
import restConfig from '@/components/rest/RestConfig';

/**
 * Makes REST http-reqeuests to the Teams resource
 */
class TeamRestConnector {

    constructor(){
        this.baseUrl = Config.getUrl("teams");
    }


    /**
     * Adds a new team
     * @param {string} name name of the new team
     * @returns {Promise} promise of HTTP response
     */
    addTeam(name){
        const toPost = Qs.stringify({
            name: name
        });

        const headers = {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'}
        const promise = Axios.post(this.baseUrl, toPost, {headers: headers});
        return promise;
    }

    /**
     * @returns {Array} return a list of all the available teams
     */
    getTeams(){
        const result = Axios.get(this.baseUrl);
        return result;
    }

    updateUnit(name, unitId, teamId){
        const data = Qs.stringify({
            name: name,
            teamId: teamId,
            unitId: unitId,
        });

        console.log({
            name: name,
            teamId: teamId,
            unitId: unitId,
        });
        const headers = {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'}

        const result = Axios.put(restConfig.getUrl('units'), data, {headers: headers});
        console.log(result);
        return result;
    }

}

export default TeamRestConnector