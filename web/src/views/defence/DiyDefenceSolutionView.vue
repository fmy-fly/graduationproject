<template>
  <div class="container">
    <DiyDefenceComponent></DiyDefenceComponent>
  </div>
</template>
<script>
import DiyDefenceComponent from '@/components/defence/DiyDefenceComponent.vue';;


export default {
  name: "DiyDefenceSolutionView",
  components: {
    DiyDefenceComponent,

  }
}
</script>

<style scoped>
body {
  background-image: url("../../assets/background.jpg");
  background-size: cover;
}
</style>