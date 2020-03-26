<template>
  <div id="tab_bar">
    <slot></slot>
  </div>
</template>

<script>

export default {
  name: "TabBar"
};
</script>

<style>

#tab_bar {
  display: flex;
  background-color: #f6f6f6;
  position: fixed;
  right: 0;
  left: 0;
  bottom: 0;

  box-shadow: 0 -1px 1px rgba(100, 100, 100, 0.1);
}

</style>