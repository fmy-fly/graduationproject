<template>
    <LoginComponent>

    </LoginComponent>
   
  </template>

  <script>
  import LoginComponent from '@/components/LoginComponent.vue';
  export default {
      name:"LoginView",
      components:{
          LoginComponent
      }
  }
  </script>
  
  <style scoped>
  body {
  background-image: url("../../assets/background.jpg");
  background-size: cover;
}
  </style>