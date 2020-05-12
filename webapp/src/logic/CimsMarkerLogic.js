function createPopup(address, icon, description, lat, lng) {
    let popup = `Event: ${icon} <br>`;

    if(address.road != undefined) {
        popup += `Road: ${address.road} <br>`;
    }

    if(address.neighbourhood != undefined) {
        popup += `Neighbourhood: ${address.neighbourhood} <br>`;
    }
    else {
        popup += `Latitude: ${lat} <br>
                  Longitude: ${lng} <br>`;
    }

    if(address.city != undefined) {
        popup += `City: ${address.city} <br>`;
    }
    popup += `Description: ${description} <br>
              Country: ${address.country}`;

    return popup;
}

export default {
    createPopup
}