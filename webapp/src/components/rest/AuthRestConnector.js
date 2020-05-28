import RestConnector from '@/components/rest/RestConnector'

class AuthRestConnector extends RestConnector {

    constructor(token){
        super();
        this.token = token;
    }

}

export default AuthRestConnector