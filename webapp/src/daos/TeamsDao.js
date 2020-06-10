import axios from 'axios';
import config from '@/components/rest/RestConfig'

const url = config.getUrl('teams');

async function getAllTeams() {
    try {        
        let res = await axios.get(url);

        return res.data;        
    }
    catch(error) {
        console.log(error);
    }
}

async function addTeam(team) {

    try {
        let res = await axios.post(url, team);

        return res.data;        
    }
    catch(error) {
        console.log(error);
    }
}

async function deleteTeam(team) {
    try {
        let res = await axios.delete(url, team);

        return res.data;        
    }
    catch(error) {
        console.log(error);
    }
}

export default {
    getAllTeams,
    addTeam,
    deleteTeam
}