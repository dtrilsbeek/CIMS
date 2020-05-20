import axios from 'axios';

const key = '78b890ebc61fda';
let format = 'json';

async function getMarkerPosition(lat, lon) {

    const params = {
        key: key,
        lat: lat,
        lon: lon,
        format: format
    };   
    
    try {
   
        let res = await axios.get('https://us1.locationiq.com/v1/reverse.php?', { params });

        return res.data;        
    }
    catch(error) {
        console.log(error);
    }
}

async function getRegionBounds(region) {

    const params = {
        key: key,
        q: region,
        format: format
    };   
    
    try {

        let res = await axios.get('https://us1.locationiq.com/v1/search.php?', { params });
        
        return res.data;        
    }
    catch(error) {
        console.log(error);
    }
}

export default {
    getMarkerPosition,
    getRegionBounds,
}