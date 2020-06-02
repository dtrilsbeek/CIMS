import axios from 'axios';

async function addEvent(event) {
  
    try {
        let res = await axios.post('http://localhost:8083/events', event);

        return res.data;        
    }
    catch(error) {
        console.log(error);
    }
}

async function putEvent(event) {
  
    try {
        let res = await axios.put('http://localhost:8083/events', event);

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