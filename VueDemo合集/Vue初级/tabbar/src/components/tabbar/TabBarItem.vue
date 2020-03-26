<template>
  <div class="tab_bar_item" @click="itemClick">
    <div v-if="!isActive">
      <slot name="item-icon"></slot>
    </div>
    <div v-else>
      <slot name="item-icon-active"></slot>
    </div>
    <div :style="acticeStyle">
      <slot name="item-text"></slot>
    </div>
    <!-- <img src="../../assets/img/tabbar/home.svg" />
    <div>首页</div>-->
  </div>
</template>

<script>
export default {
  name: "TabBarItem",
  props: {
    path: String,
    activeColor: {
      type: String,
      default: 'red'
    }
  },
  data() {
    return {
      // isActive: true,
      // path: '/home'
    };
  },
  computed: {
    isActive() {
      return this.$route.path.indexOf(this.path) !== -1;
    },
    acticeStyle() {
      return this.isActive ? { color: this.activeColor } : {};
    }
  },
  methods: {
    itemClick() {
      this.$router.replace(this.path);
    }
  }
};
</script>

<style>
.tab_bar_item {
  flex: 1;
  text-align: center;
  height: 49px;
  font-size: 14;
}

.tab_bar_item img {
  margin-top: 3px;
  width: 24px;
  height: 24px;
  vertical-align: middle;
  margin-bottom: 2px;
}
</style>