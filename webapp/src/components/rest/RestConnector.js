import Qs from 'qs' // For converting to form url encoded

class RestConnector {

    constructor(){
        this.formUrlEncoded = 'application/x-www-form-urlencoded;charset=UTF-8';
    }

    /**
     * @param {Object} data the data to send with the HTTP request
     * @returns {String} the stringified data that is valid for ContentType form url encoded
     */
    toFormUrlEncoded(data){
        return Qs.stringify(data);
    }

}

export default RestConnector