<template>
  <div class="wrapper" ref="wrapper">
    <div class="content">
      <slot></slot>
    </div>
  </div>
</template>

<script>
import BScroll from "better-scroll";
export default {
  name: "Scroll",
  data() {
    return {
      scroll: null
    }
  },
  props: {
    probeType: {
      type: Number,
      default: 0
    },
    pullUpLoad: {
      type: Boolean,
      default: false
    }
  },
  mounted() {
    this.scroll = new BScroll(this.$refs.wrapper, {
      click: true,
      probeType: this.probeType,
      pullUpLoad: this.pullUpLoad
    }),

//监听滚动
    this.scroll.on('scroll', (position) => {
      // console.log(position);
      this.$emit('scroll', position);
    })

    //上拉加载更多
    this.scroll.on('pullingUp', () => {
      //console.log('上拉加载');
      this.$emit('pullingUp');
    })

    // this.scroll.scrollTo(0,0);
  },
  methods: {
    scrollTo(x,y,time=300) {
      this.scroll.scrollTo(x,y,time);
    },

    refresh() {
      console.log('0000');
      this.scroll.refresh();
    },

    finishPullUp() {
      this.scroll.finishPullUp();
    }
  }
};
</script>

<style scoped>
</style>