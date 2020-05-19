// import CimsMarker from "../leaflet";


import CimsMarker from "../leaflet/CimsMarker";

export default class EventStream {

    constructor(){

    }

    readStream(addMarker) {
        let path = "http://localhost:8080/events/stream/2";

        const source = new EventSource(path);

        source.onmessage = event => {
            const data = JSON.parse(event.data);

            let test = [data.lat, data.lon];

            let marker = new CimsMarker('ambulance', test);

            addMarker(marker);
            console.log(data);
            // lat.textContent = data.lat;
            // lon.textContent = data.lon;
            // type.textContent = data.type;
            // description.textContent = data.description;
        };
    }

}