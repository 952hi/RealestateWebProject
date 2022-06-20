<template>
  <b-container class="mt-4" v-if="loginUser.id">
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-jumbotron>
          <template #header>My Page</template>

          <template #lead> 내 정보 확인페이지입니다. </template>

          <hr class="my-4" />

          <b-container class="mt-4">
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">아이디</b-col
              ><b-col cols="4" align-self="start">{{ loginUser.id }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">이름</b-col
              ><b-col cols="4" align-self="start">{{ loginUser.name }} </b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">이메일</b-col>
              <b-col cols="4" align-self="start">{{ loginUser.email }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">나이</b-col>
              <b-col cols="4" align-self="start">{{ loginUser.age }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">가입일</b-col>
              <b-col cols="4" align-self="start">{{ loginUser.time }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">회원등급</b-col>
              <b-col cols="4" align-self="start">{{ printGrade }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
          </b-container>
          <hr class="my-4" />

          <b-button variant="primary" href="#" class="mr-1" @click="mvModify">
            정보수정
          </b-button>
          <b-button variant="danger" href="#" @click="Withdrawal"
            >회원탈퇴</b-button
          >
        </b-jumbotron>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import http from "@/api/http";
export default {
  name: "MemberMyPage",
  components: {},
  computed: {
    ...mapGetters(["loginUser"]),
    printGrade() {
      return this.loginUser.grade == 1
        ? "일반회원"
        : this.loginUser.grade == 2
        ? "전문회원"
        : "관리자";
    },
  },
  methods: {
    ...mapActions(["logout"]),
    mvModify() {
      this.$router.push({ name: "modify" });
    },
    Withdrawal() {
      if (confirm("탈퇴하시겠습니까?")) {
        http.delete(`user/${this.loginUser.id}`).then(() => {
          this.logout();
          this.$router.push({ name: "home" });
        });
      }
    },
  },
};
</script>

<style></style>
