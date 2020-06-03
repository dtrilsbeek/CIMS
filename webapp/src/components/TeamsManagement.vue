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
				<summary class="team-name">{{team.name}}</summary>
                 <ul>
                    <li v-for="unit in team.units" :key="unit.id" class="unit">{{unit.name}}
                        <span v-on:click="editUnit(unit)" class="unit-edit"></span>
                        <span v-on:click="removeUnit(unit)" class="unit-remove"></span>
                    </li>
                </ul>
			</details>
		</li>
    </ul>
  
  </div>
</template>

<script>
import Unit from '@/components/teams/Unit.js';
import Team from '@/components/teams/Team.js';

export default {

    data() {
        return {
            teams: Array
        }
    },

    mounted() {
        this.teams = [];

        let t1 = new Team('team1');
        t1.units = [            
            new Unit('unit1'),
            new Unit('unit2'),      
            new Unit('unit3')            
        ]
        this.teams.push(t1);

        let t2 = new Team('team2');
        t2.units = [            
            new Unit('unit45'),
            new Unit('unit46'),      
            new Unit('unit47')            
        ]
        this.teams.push(t2);    
    },

    methods: {
        editUnit(unit) {
            console.log('edit ' + unit.name);  
        },

        removeUnit(unit) {
            console.log('remove ' + unit.name);
        }
    }

}
</script>

<style src="@/assets/css/teams-management.css" scoped>

</style>