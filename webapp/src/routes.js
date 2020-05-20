import Home from './components/Home.vue'
import Teams from './components/Teams.vue'
import CimsMap from './components/Map.vue'
import Login from './components/Login.vue'

export default [
    {path: '/', component: Home},
    {path: '/map', component: CimsMap},
    {path: '/teams', component: Teams},
    {path: '/login', component: Login},
];