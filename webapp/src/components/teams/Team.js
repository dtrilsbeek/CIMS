export default class Team {

    constructor(id, name) {
        this.id = id;
        this.units = [];
        this.name = name;
    }

    addUnit(unit) {
        this.units.push(unit);
    }

    removeUnit(unit) {
        let index = this.units.indexOf(unit);
        this.units.splice(index, 1);
    }
}