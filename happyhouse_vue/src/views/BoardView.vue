<template>
  <b-container class="bv-example-row mt-3 text-center">
    <router-view></router-view>
  </b-container>
</template>
<script>
import { mapGetters } from "vuex";
export default {
  name: "BoardView",
  computed: {
    ...mapGetters(["loginUser"]),
  },
  created() {
    if (this.loginUser.id == undefined) {
      alert("로그인이 필요합니다.");
      this.$router.push({ name: "signIn" });
    } else if (this.loginUser.grade == 1) {
      alert("접근할 수 없습니다.");
      this.$router.push({ name: "home" });
    }
  },
};
</script>
<style scoped></style>
