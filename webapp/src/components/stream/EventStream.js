

export default class EventStream {

    constructor(){

    }

    readStream() {
        let path = "http://localhost:8080/events/stream/2";

        const source = new EventSource(path);

        source.onmessage = event => {
            const data = JSON.parse(event.data);

            console.log(data);
            // lat.textContent = data.lat;
            // lon.textContent = data.lon;
            // type.textContent = data.type;
            // description.textContent = data.description;
        };
    }

}