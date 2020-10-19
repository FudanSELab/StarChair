<template>
  <div id="app">
    <v-app>
      <router-view />
    </v-app>
  </div>
</template>

<script>
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.min.js";
export default {
  name: "App"
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #c73548;
  font-size: 1.2rem;
  /*margin-top: 60px;*/
}
body {
  margin: 0px;
  padding: 0px;
}
</style>
