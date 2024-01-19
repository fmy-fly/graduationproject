import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import RegisterView from '@/views/user/RegisterView'
import LoginView from '@/views/user/LoginView'
import ReceiveDataView from '@/views/data/ReceiveDataView'
import DefenceSolutionView from '@/views/defence/DefenceSolutionView'
import NodesRegulateView from '@/views/Nodes/NodesRegulateView'
import RecordDataView from '@/views/record/RecordDataView'
import AnalysisSettingView from '@/views/settings/AnalysisSettingView'
import AddNewDefenceView from '../views/defence/AddNewDefenceView'
import store from '../store/index'
import DiyDefenceSolution from "../views/defence/DiyDefenceSolutionView"

const routes = [
  {
    path: "/",
    name: "login1",
    redirect: "/login",
    meta: {
      requestAuth: false,
    }
  },
  {

    path: '/login',
    name: 'login',
    component: LoginView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
    ,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: '/home',
    name: 'home',
    component: HomeView
    ,
    meta: {
      requestAuth: true,
    }
  },
  {

    path: "/:catchAll(.*)",
    redirect: "/login"
    ,
    meta: {
      requestAuth: false,
    }

  },

  {
    path: '/data',
    name: 'data',
    component: ReceiveDataView,
    meta: {
      requestAuth: true,
    }
  },

  {

    path: '/defence/defence',
    name: 'defence',
    component: DefenceSolutionView,
    meta: {
      requestAuth: true,
    }

  },
  {

    path: '/defence/diydefence',
    name: 'diydefence',
    component: DiyDefenceSolution,
    meta: {
      requestAuth: true,
    }

  },
  {

    path: '/defence/addnewdefence',
    name: 'addnewdefence',
    component: AddNewDefenceView,
    meta: {
      requestAuth: true,
    }

  },
  {

    path: '/node',
    name: 'node',
    component: NodesRegulateView,
    meta: {
      requestAuth: true,
    }

  },
  {

    path: '/record',
    name: 'record',
    component: RecordDataView,
    meta: {
      requestAuth: true,
    }

  },
  {

    path: '/setting',
    name: 'setting',
    component: AnalysisSettingView,
    meta: {
      requestAuth: true,
    }

  },
  {

    path: '/api/capture/start',
    name: 'start',
    component: NodesRegulateView,
    meta: {
      requestAuth: true,
    }

  },




]


const router = createRouter({
  history: createWebHistory(),
  routes
})
router.beforeEach((to, from, next) => {
  if (to.meta.requestAuth && !store.state.user.is_login) {
    next({ name: "login" });
  } else {
    next();
  }
})

export default router
