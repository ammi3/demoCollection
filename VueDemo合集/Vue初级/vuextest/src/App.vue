<template>
  <div id="app">
    <h2>{{$store.state.counter}}</h2>
    <h2>------------</h2>
    <button @click="addition">+</button>
    <button @click="subtraction">-</button>
    <button @click="addCount(5)">+5</button>

    <h2>{{$store.getters.powerCounter}}</h2>
    <HelloVuex />
  </div>
</template>

<script>
import HelloVuex from "./components/HelloVuex";
import {
  INCRMENT
} from './store/mutation-types'

export default {
  name: "App",
  components: {
    HelloVuex
  },
  data() {
    return {
      message: "我是App组件",
      counter: 0
    };
  },
  methods: {
    addition() {
      this.$store.commit(INCRMENT);
    },
    subtraction() {
      this.$store.commit("decrement");
    },
    addCount(count) {
      //普通提交风格
      this.$store.commit('incrementCount',count);

    //特殊提交封装
      // this.$store.commit({
      //   type: 'increment'
      // });
    }
  }
};
</script>

<style>
</style>
