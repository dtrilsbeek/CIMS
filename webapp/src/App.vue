<template>
  <div id="app">
    <header class="header">
        <h1>cims</h1>
        <div id="nav">
      | <router-link to="/">Home</router-link> |
      <router-link to="/map">map</router-link> |
      <router-link to="/teams">teams</router-link> |
      <router-link to="/units">units</router-link> |
       <a href="http://localhost:8180/auth/realms/CIMS-users/protocol/openid-connect/auth?client_id=frontend&response_type=code&scope=openid&redirect_uri=http://localhost/map">login/register</a> |
    </div>  
    </header> 
    <main>
      <router-view></router-view>
    </main>
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {
  }
}
</script>


<style src="@/assets/css/css-reset.css"></style>
<style src="@/assets/css/app.css"></style>
