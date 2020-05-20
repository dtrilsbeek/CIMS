/**
 * Seperate file for the REST configuration.
 * This allows usability in multiple classes 
 * and differentiates between development and production
 */
class RestConfig {
    /**
     * 
     * @param {String} protocol http / https
     * @param {String} host the host name
     * @param {String} port the port the host is running on
     */
    constructor(protocol, host, port){
        this.protocol = protocol;
        this.host = host;
        if(port !== ''){
            this.port = ':' + port;
        }
    }

    /**
     * @param {String} resource the resource of the endpoint 
     */
    getUrl(resource){
        return `${this.protocol}://${this.host}${this.port}/${resource}/`;
    }
}

let restConfig;


if (process.env.NODE_ENV === 'production'){
    // Put production values here if we do deployment
    restConfig = new RestConfig();

}
else{
    restConfig = new RestConfig(
        'http',
        'localhost',
        '8083');
}

export default restConfig;

