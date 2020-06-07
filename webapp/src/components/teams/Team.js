export default class Team {

    constructor(name) {
        this.units = [];
        this.name = name;
    }

    addUnit(newUnit) {
        this.units.push(newUnit);
    }
}