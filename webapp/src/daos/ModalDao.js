import axios from 'axios';
import config from '@/components/rest/RestConfig'

const url = config.getUrl('events');

async function addEvent(event) {
  
    try {
        let res = await axios.post(url, event);

        return res.data;        
    }
    catch(error) {
        console.log(error);
    }
}

async function putEvent(event) {
  
    try {
        let res = await axios.put(url, event);

        return res.data;        
    }
    catch(error) {
        console.log(error);
    }
}

export default {
    addEvent,
    putEvent
}