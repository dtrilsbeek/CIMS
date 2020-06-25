<template>
  <div class="teams-management-container">
      <div class="teams-management-options">
        <div class="team-unit-btns-container">
            <button>Teams</button>
            <button>Units</button>
        </div>
        <div class="input-container">
            <input placeholder="Search">
            <img src="@/assets/images/search-icon.png" class="search-img"/>
        </div>
      </div>
       

    <ul v-if="teams.length" class="teams">
        <li v-for="team in teams" :key="team.id" class="team">
			<details class="dropdown details-reset details-overlay d-inline-block">
				<summary class="team-name">{{team.name}}
                    <span class="team-remove" v-on:click="removeTeam(team)"/>
                </summary>
                 <ul>  
                    <li v-for="unit in team.units" :key="unit.id" class="unit">
                        <div>
                            {{unit.name}}
                            <span v-on:click="editUnit(unit)" class="unit-edit"></span> 
                            <span v-on:click="removeUnit(unit)" class="unit-remove"></span>
                        </div>
                    </li>
                </ul>
			</details>
		</li>
    </ul>
  </div>
</template>

<script>
import TeamRestConnector from "./rest/TeamRestConnector";
import UnitRestConnector from "./rest/UnitRestConnector";

export default {

    data() {
        return {
            teams: Array,
            teamRestConnector: new TeamRestConnector(),
            unitRestConnector: new UnitRestConnector()
        }
    },

    mounted() {

        this.loadTeams()
            .then(teams => {
                this.teams = teams;

                for (let i = 0; i < this.teams.length; i++) {

                    let team = this.teams[i];

                    this.loadTeamUnits(team.id)
                        .then(units => {
                            team.units = units;  
                            this.$forceUpdate();
                        })                            
                }
            });
    },  

    methods: {

        async loadTeams() {
            let response = await this.teamRestConnector.getTeams();
            let teams = response.data;
            return teams;             
        },

        async loadTeamUnits(teamId) {
            let response = await this.unitRestConnector.getUnitsByTeamId(teamId);
            let units = response.data;
            return units;
        },

        editUnit(unit) {  
            this.$emit("unit-edit", unit);        
        },

        removeUnit(unit) { 
            this.unitRestConnector.removeUnit(unit.id, this.$token)
            .then(() => {

                let team = this.teams.find(x => x.id = unit.team.id);
                let index = team.units.indexOf(unit);
                team.units.splice(index, 1);

                this.$forceUpdate();
            })
            .catch(ex => {
                console.log(ex);    
            });
        },

        removeTeam(team) {
            
            this.teamRestConnector.removeTeam(team.id, this.$token)
            .then(() => {

                let index = this.teams.indexOf(team);
                this.teams.splice(index, 1);
            })
            .catch(ex => {
                console.log(ex);    
            });
        },
    }

}
</script>

<style src="@/assets/css/teams-management.css" scoped>

</style>