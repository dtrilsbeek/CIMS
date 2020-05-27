import Config from '@/components/rest/RestConfig'
import Axios from 'axios'
import Qs from 'qs' // For converting to form url encoded

/**
 * Makes REST http-reqeuests to the Units resource
 */
class UnitRestConnector {

    constructor(){
        this.baseUrl = Config.getUrl("units");
        this.teamUrl = Config.getUrl("teams");
    }


    /**
     * Adds a new team
     * @param {string} name -> name of the new unit
     * @param {long} teamId -> teamId of the team this new unit belongs to
     * @returns {Promise} promise of HTTP response
     */
    addUnit(name, teamId){
        const toPost = Qs.stringify({
            name: name,
            teamId: teamId
        });

        const headers = {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'}
        const promise = Axios.post(this.baseUrl, toPost, {headers: headers});
        return promise;
    }

    getTeams(){
        const headers = {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'}
        const promise = Axios.get(this.teamUrl, {headers: headers});
        return promise;
    }
}

export default UnitRestConnector