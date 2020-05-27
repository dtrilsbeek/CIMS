import Home from './components/Home.vue'
import CimsMap from './components/Map.vue'
import Teams from './components/Teams.vue'
import Units from "@/components/Units";
import Login from './components/Login.vue'

export default [
    {path: '/', component: Home},
    {path: '/map', component: CimsMap},
    {path: '/teams', component: Teams},
    {path: '/units', component: Units},
    {path: '/login', component: Login},
];