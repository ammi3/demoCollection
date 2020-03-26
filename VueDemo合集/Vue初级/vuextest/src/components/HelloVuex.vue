<template>
  <div>
    <h2>{{$store.state.counter}}</h2>
     
  </div>
</template>

<script>
export default {
  name: "HelloVuex",
  data() {
    return {
      message: "Vuex"
    };
  }
};
</script>