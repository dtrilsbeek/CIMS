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
       

    <ul class="teams">
        <li v-for="team in teams" :key="team.id" class="team">
			<details class="dropdown details-reset details-overlay d-inline-block">
				<summary class="team-name">{{team.name}}
                    <span class="team-remove" v-on:click="removeTeam(team.id)"/>
                </summary>
                 <ul>  
                    <li v-for="unit in team.units" :key="unit.id" class="unit">
                        <div>
                            {{unit.name}}
                            <span v-on:click="editUnit(unit)" class="unit-edit"></span> 
                            <span v-on:click="removeUnit(unit.id)" class="unit-remove"></span>
                        </div>
                    </li>
                </ul>
			</details>
		</li>
    </ul>
  
  </div>
</template>

<script>
// import Unit from '@/components/teams/Unit.js';
// import Team from '@/components/teams/Team.js';
// import TeamsDao from '@/daos/TeamsDao.js';
import TeamRestConnector from "./rest/TeamRestConnector";
import UnitRestConnector from "./rest/UnitRestConnector";


export default {

    data() {
        return {
            teams: Array,
            teamRestConnector: new TeamRestConnector(this.$token),
            unitRestConnector: new UnitRestConnector(this.$token)
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

            this.$emit("existing-unit-edit", unit);
            //goto edit unit page
            // this.unitRestConnector.updateUnit(unit.name, unit.id, unit.team.id)
            // .catch(ex => {
            //     console.log(ex);    
            // });            
        },

        removeUnit(unitId) {
            this.unitRestConnector.removeUnit(unitId)
            .catch(ex => {
                console.log(ex);    
            });
        },

        removeTeam(teamId) {
            this.teamRestConnector.removeTeam(teamId)
            .catch(ex => {
                console.log(ex);    
            });
        },
    }

}
</script>

<style src="@/assets/css/teams-management.css" scoped>

</style>