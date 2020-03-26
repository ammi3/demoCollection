import Vue from 'vue'
import Vuex from 'vuex'

//安装插件
Vue.use(Vuex)

//创建对象
const store = new Vuex.Store({
  state: {
    counter: 1000
  },
  mutations: {
    increment(state) {
      state.counter++;
    },
    decrement(state) {
      state.counter--;
    },
    // 如果传入的是对象则count改为payload
    incrementCount(state,count) {
      state.counter += count;//这边更改为payload.count取出数值
    }
  },
  actions: {

  },
  getters: {
    powerCounter(state) {
      return state.counter * state.counter;
    }
  },
  modules: {

  }
})

//导出
export default store