<template>
  <div>
    <h2>我是用户</h2>
    <p>嘿嘿嘿</p>
    {{userId}}
  </div>
</template>

<script>
export default {
  name: "User",
  computed: {
    userId() {
      return this.$route.params.userId
    }
  }
};  
</script>

<style scoped>
</style>