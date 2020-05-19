import Config from '@/components/rest/RestConfig'
import Axios from 'axios'
import Qs from 'qs' // For converting to form url encoded

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
        const promise = Axios.post('http://localhost:8083/teams', toPost, {headers: headers});
        return promise;
    }

}

export default TeamRestConnector