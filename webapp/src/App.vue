<template>
  <div id="app">
    <header class="header">
        <h1>cims</h1>
        <div id="nav">
      | <router-link to="/">Home</router-link> |
      <router-link to="/map">map</router-link> |
      <router-link to="/teams">teams</router-link> |
      <router-link to="/units">units</router-link> |
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
