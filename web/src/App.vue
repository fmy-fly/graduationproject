<template>
  <NavBar v-show = "$route.name!=='login' && $route.name!=='register' "> </NavBar>
  
  <router-view></router-view>
  <FooterComponent v-show = "$route.name!=='login' && $route.name!=='register' " ></FooterComponent> 
</template>

<script>

import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
import NavBar from "./components/NavBar.vue";
import FooterComponent from "./components/FooterComponent";


export default {
  
  components: {
   NavBar,
   FooterComponent,
   
  },
  setup(){
    

  }
  
    }


  
  
  
 

 
 



 
  // }

</script>

<style>
 
</style>
