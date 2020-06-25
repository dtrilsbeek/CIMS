module.exports = class KafkaTopicInfo {

    constructor(coordinates, type, information) {
        this.coordinates = coordinates;
        this.type = type;
        this.information = information;
    }
}