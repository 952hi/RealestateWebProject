<template>
  <div id="app">
    <!-- 
      style="position: fixed; z-index: 11; width: 100%; top: 0px" -->
    <navi-bar></navi-bar>
    <!-- style="position: absolute; top: 70px" -->
    <router-view></router-view>
  </div>
</template>

<script>
import NaviBar from "@/components/layout/HeaderNaviBar.vue";

export default {
  name: "App",
  components: {
    NaviBar,
  },
};
</script>

<style>
a {
  text-decoration: none;
}
#id {
  background-image: url("http://www.daeguview.com/DATA/photo/thumb_b_20190930102559.jpg");
}
</style>
