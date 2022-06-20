<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>로그인</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="isError"
              >아이디 또는 비밀번호를 확인하세요.</b-alert
            >
            <b-form-group label="아이디:" label-for="userid">
              <b-form-input
                id="userid"
                v-model="user.id"
                required
                placeholder="아이디 입력...."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userpwd">
              <b-form-input
                type="password"
                id="userpw"
                v-model="user.pw"
                required
                placeholder="비밀번호 입력...."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-button
              type="button"
              variant="primary"
              class="m-1"
              @click="confirm"
              >로그인
            </b-button>
            <router-link :to="{ name: 'signUp' }">
              <b-button type="button" variant="success" class="m-1">
                회원가입
              </b-button>
            </router-link>
            <b-button
              type="button"
              variant="warning "
              class="m-1"
              id="show-btn"
              @click="$bvModal.show('find_pw_modal')"
            >
              비밀번호 찾기
            </b-button>
            <b-modal id="find_pw_modal" hide-footer>
              <template #modal-title> 비밀번호 초기화 </template>
              <b-row class="my-3">
                <b-col cols="4">
                  <div class="d-block text-center">아이디</div>
                </b-col>
                <b-col>
                  <b-form-input
                    v-model="find_pw.id"
                    placeholder="아이디를 입력해주세요."
                    :state="idState"
                  ></b-form-input>
                </b-col>
              </b-row>
              <b-row class="my-3">
                <b-col cols="4">
                  <div class="d-block text-center">이메일</div>
                </b-col>
                <b-col>
                  <b-form-input
                    v-model="find_pw.email"
                    placeholder="이메일를 입력해주세요."
                    :state="emailState"
                  ></b-form-input>
                </b-col>
              </b-row>
              <b-row class="my-3">
                <b-col cols="4">
                  <div class="d-block text-center">이름</div>
                </b-col>
                <b-col>
                  <b-form-input
                    v-model="find_pw.name"
                    placeholder="이름를 입력해주세요."
                    :state="nameState"
                  ></b-form-input>
                </b-col>
              </b-row>
              <b-row class="text-right" align-h="end">
                <b-button
                  variant="outline-primary"
                  class="mt-3 mx-3"
                  @click="$bvModal.hide('find_pw_modal'), sendFindPw()"
                >
                  확인
                </b-button>
                <b-button
                  class="mt-3 mx-3"
                  variant="outline-danger"
                  @click="$bvModal.hide('find_pw_modal')"
                >
                  닫기
                </b-button>
              </b-row>
            </b-modal>
            <b-modal id="set_new_pw_modal" hide-footer>
              <template #modal-title> 비밀번호 찾기 </template>
              <b-row class="my-3">
                <b-col cols="4">
                  <div class="d-block text-center">새 비밀번호</div>
                </b-col>
                <b-col>
                  <b-form-input
                    v-model="new_pw"
                    placeholder="새 비밀번호"
                  ></b-form-input>
                </b-col>
              </b-row>
              <b-row>
                <b-button
                  variant="outline-primary"
                  class="mt-3 mx-3"
                  @click="$bvModal.hide('set_new_pw_modal'), set_new_Pw()"
                >
                  변경 완료
                </b-button>
              </b-row>
            </b-modal>
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapActions } from "vuex";
import http from "@/api/http";
export default {
  name: "MemberLogin",
  data() {
    return {
      isError: false,
      user: {
        id: "",
        pw: "",
      },
      find_pw: {
        id: "",
        email: "",
        name: "",
      },
      new_pw: "",
    };
  },
  computed: {
    nameState() {
      return this.find_pw.name.length < 1 ? false : true;
    },
    idState() {
      return this.find_pw.id.length < 1 ? false : true;
    },
    emailState() {
      return this.find_pw.email.length < 1 ? false : true;
    },
  },
  methods: {
    ...mapActions(["loginSuccess"]),
    // 아이디, 비밀번호 입력하면 db 조회 후 로그인 세팅하기
    confirm() {
      if (this.user.id.trim() == "" || this.user.pw.trim() == "") {
        this.isError = true;
        return;
      }
      this.RequestLogin();
    },
    RequestLogin() {
      http
        .post("/user/login", this.user)
        .then((res) => {
          if (res.data == "") {
            alert("아이디나 비밀번호가 틀렸습니다.");
          } else {
            this.loginSuccess(res.data);
            this.$router.push({ name: "home" });
          }
        })
        .catch();
    },
    sendFindPw() {
      http
        .post("user/findPw", this.find_pw)
        .then(({ data }) => {
          if (data == "success") {
            this.$bvModal.show("set_new_pw_modal");
          }
        })
        .catch();
    },
    set_new_Pw() {
      http
        .post("user/setPw", { pw: this.new_pw, id: this.find_pw.id })
        .then(({ data }) => {
          if (data == "success") alert("비밀번호가 변경되었습니다.");
          else alert("변경 중 오류가 발생했습니다.");
          this.$router.go();
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style></style>
