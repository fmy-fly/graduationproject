import { createStore } from 'vuex'
import ModuleUser from './user'
import ModuleData from './data'
export default createStore({
  state: {

  },

  getters: {
  },
  mutations: {

  },
  actions: {
  },
  modules: {
    user: ModuleUser,
    data: ModuleData,
  }
})
