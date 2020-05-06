<template>
  <scroll class="content">
    <ul>
      <li>aaaaa</li>
      <li>aaaaa</li>
      <li>aaaaa</li>
      <li>aaaaa</li>
      <li>aaaaa</li>
      <li>aaaaa</li>
      <li>aaaaa</li>
      <li>aaaaa</li>
      <li>aaaaa</li>
    </ul>
  </scroll>
</template>

<script>
import Scroll from "components/common/scroll/Scroll";
  export default {
    name: "Profile",
    components: {
      Scroll
    }
  }
</script>

<style scoped>
.content {
  height: 300px;
  background-color: aqua;
  overflow: hidden;
}
</style>