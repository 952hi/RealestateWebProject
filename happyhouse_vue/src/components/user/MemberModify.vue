<template>
  <b-container class="mt-4" v-if="loginUser.id">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>내정보</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-jumbotron>
          <template #header>My Page</template>

          <template #lead> 개인정보 수정 </template>

          <hr class="my-4" />

          <b-container class="mt-4">
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">아이디</b-col>
              <b-col cols="4" align-self="start">
                <b-form-input :value="this.loginUser.id" disabled>
                </b-form-input>
              </b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">비밀번호</b-col
              ><b-col cols="4" align-self="start">
                <b-form-input v-model="pw"> </b-form-input>
              </b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">이름</b-col
              ><b-col cols="4" align-self="start">
                <b-form-input :value="this.loginUser.name" disabled>
                </b-form-input>
              </b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">이메일</b-col>
              <b-col cols="4" align-self="start">
                <b-form-input v-model="email"> </b-form-input>
              </b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">나이</b-col>
              <b-col cols="4" align-self="start">
                <b-form-input v-model="age"> </b-form-input>
              </b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">가입일</b-col>
              <b-col cols="4" align-self="start"
                ><b-form-input :value="this.loginUser.time" disabled>
                </b-form-input
              ></b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">회원등급</b-col>
              <b-col cols="4" align-self="start"
                ><b-form-input :value="printGrade" disabled> </b-form-input
              ></b-col>
              <b-col cols="2"></b-col>
            </b-row>
          </b-container>
          <hr class="my-4" />

          <b-button
            variant="primary"
            href="#"
            class="mr-1"
            @click="SendModifingData"
          >
            수정 확인
          </b-button>
          <router-link :to="{ name: 'board' }">
            <b-button variant="danger"> 취소 </b-button>
          </router-link>
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
  name: "MemberModify",
  data() {
    return {
      pw: "",
      email: "",
      age: "",
    };
  },
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
    SendModifingData() {
      http
        .put(`user/${this.loginUser.id}`, {
          id: this.loginUser.id,
          pw: this.pw,
          email: this.email,
          age: Number(this.age),
        })
        .then(({ data }) => {
          console.log(data);
          if (data == "success") {
            alert("수정되었습니다.");
            this.logout();
            this.$router.push({ name: "home" });
          }
        });
    },
  },
  created() {
    this.pw = this.loginUser.pw;
    this.email = this.loginUser.email;
    this.age = this.loginUser.age;
  },
};
</script>

<style></style>
