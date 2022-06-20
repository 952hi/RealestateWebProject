<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>회원가입</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="isError">{{
              errorMessage
            }}</b-alert>
            <b-form-group label="아이디:" label-for="id">
              <b-form-input
                id="id"
                required
                placeholder="아이디 입력...."
                @keyup.enter="confirm"
                v-model="registerUserData.id"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="pw">
              <b-form-input
                type="password"
                id="pw"
                required
                placeholder="비밀번호 입력...."
                @keyup.enter="confirm"
                v-model="registerUserData.pw"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이름:" label-for="name">
              <b-form-input
                id="name"
                required
                placeholder="이름 입력...."
                @keyup.enter="confirm"
                v-model="registerUserData.name"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="나이:" label-for="age">
              <b-form-input
                id="age"
                required
                placeholder="나이 입력...."
                @keyup.enter="confirm"
                v-model="registerUserData.age"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이메일:" label-for="email">
              <b-form-input
                id="email"
                required
                placeholder="이메일 입력...."
                @keyup.enter="confirm"
                v-model="registerUserData.email"
              ></b-form-input>
            </b-form-group>
            <b-button
              type="button"
              variant="primary"
              class="m-1"
              @click="registUser"
              >가입하기</b-button
            >
            <b-button type="button" variant="success" class="m-1"
              >돌아가기</b-button
            >
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/api/http.js";
export default {
  name: "MemberRegister",
  data() {
    return {
      errorMessage: "",
      isError: null,
      registerUserData: {
        id: "",
        pw: "",
        email: "",
        age: "",
        name: "",
      },
    };
  },
  methods: {
    registUser() {
      if (this.registerUserData.id.trim() == "") {
        this.errorMessage = "아이디를 입력해주세요.";
        this.isError = true;
        return;
      } else if (this.registerUserData.pw.trim() == "") {
        this.errorMessage = "비밀번호를 입력해주세요.";
        this.isError = true;
        return;
      } else if (this.registerUserData.email.trim() == "") {
        this.errorMessage = "이메일을 입력해주세요.";
        this.isError = true;
        return;
      } else if (this.registerUserData.age.trim() == "") {
        this.errorMessage = "나이를 입력해주세요.";
        this.isError = true;
        return;
      } else if (this.registerUserData.name.trim() == "") {
        this.errorMessage = "이름을 입력해주세요.";
        this.isError = true;
        return;
      }
      let sendData = {};
      sendData.id = this.registerUserData.id;
      sendData.pw = this.registerUserData.pw;
      sendData.email = this.registerUserData.email;
      sendData.name = this.registerUserData.name;
      sendData.age = Number(this.registerUserData.age);
      http
        .post("user/regist", sendData)
        .then((res) => {
          if (res.data === "success") {
            this.$router.push({ name: "success" });
          } else {
            this.isError = true;
            this.errorMessage = "이미 존재하는 아이디입니다.";
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style></style>
