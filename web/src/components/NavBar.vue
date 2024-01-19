<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
      <router-link class="navbar-brand" :to="{ name: 'home' }">IOT System</router-link>
      <div class="collapse navbar-collapse" id="navbarText">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link :class="route_name == 'data' ? 'nav-link active' : 'nav-link'"
              :to="{ name: 'data' }">实时测量</router-link>
          </li>
          <li class="nav-item">
            <router-link :class="route_name == 'record' ? 'nav-link active' : 'nav-link'"
              :to="{ name: 'record' }">监测日志</router-link>
          </li>
          <li class="nav-item">
            <router-link :class="route_name == 'setting' ? 'nav-link active' : 'nav-link'"
              :to="{ name: 'setting' }">检测配置</router-link>
          </li>


          <ul class="navbar-nav">
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown"
                aria-expanded="false">
                安全策略管理
              </a>
              <ul class="dropdown-menu" aria-labelledby="navbarDropdown">

                <li>
                  <hr class="dropdown-divider">
                </li>
                <li>

                  <router-link class="dropdown-item" :to="{ name: 'defence' }">固定策略</router-link>
                </li>
                <li>

                  <router-link class="dropdown-item" :to="{ name: 'diydefence' }">自定义策略</router-link>
                </li>
              </ul>
            </li>
          </ul>



          <li class="nav-item">
            <router-link :class="route_name == 'node' ? 'nav-link active' : 'nav-link'"
              :to="{ name: 'node' }">设备管理与监测</router-link>
          </li>
        </ul>
        <ul class="navbar-nav">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown"
              aria-expanded="false">
              {{ $store.state.user.username }}
            </a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">

              <li>
                <hr class="dropdown-divider">
              </li>
              <li>

                <router-link class="dropdown-item" :to="{ name: 'login' }" @click="logout">退出</router-link>
              </li>
            </ul>
          </li>
        </ul>

      </div>
    </div>

  </nav>
</template>
    
<script>
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import { useStore } from 'vuex';

export default {
  setup() {

    const store = useStore();
    const route = useRoute();
    let route_name = computed(() => route.name)

    const logout = () => {
      store.dispatch("logout");
    }

    return {
      route_name,
      logout
    }
  }
}

</script>
    
<style scoped>li.nav-item {
  margin-left: 65px;
}</style>

    