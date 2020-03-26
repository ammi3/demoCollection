<template>
  <div id="app">
    <h2>我是App组件</h2>
    <router-link to="/home">首页</router-link>
    <router-link to="/about">关于</router-link>
    <router-link :to="'/user/'+userId">用户</router-link>
    <router-link :to="{path: '/profile', query: {name: 'ammi3', age: 18, height: 1.88}}">我的</router-link>
    <keep-alive exclude="profile">
      <router-view></router-view>
    </keep-alive>
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      userId: "zhangsan"
    };
  }
};
</script>

<style scoped>
.active {
  color: red;
}
</style>
