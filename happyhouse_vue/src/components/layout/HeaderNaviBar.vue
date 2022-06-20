<template>
  <div id="navbarr">
    <b-navbar toggleable="lg" type="light" variant="info">
      <b-navbar-brand href="#">
        <router-link :to="{ name: 'home' }">
          <img
            :src="image"
            class="d-inline-block align-top"
            style="width: 50px; height: 50px; border-radius: 10px"
          />
        </router-link>
      </b-navbar-brand>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="#">
            <router-link
              :to="{ name: 'home' }"
              style="text-decoration: none; color: black"
            >
              지도
            </router-link>
          </b-nav-item>
          <b-nav-item href="#">
            <template v-if="this.loginUser.grade >= 2">
              <router-link
                :to="{ name: 'board' }"
                style="text-decoration: none; color: black"
              >
                매물 등록 게시판
              </router-link>
            </template>
          </b-nav-item>
          <b-nav-item href="#">
            <router-link
              :to="{ name: 'chat' }"
              style="text-decoration: none; color: black"
            >
              상담 관리
            </router-link>
          </b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav class="ml-auto">
          <template v-if="!this.loginUser.id">
            <b-nav-item right>
              <router-link
                :to="{ name: 'signIn' }"
                style="text-decoration: none; color: black"
              >
                로그인
              </router-link>
            </b-nav-item>
            <b-nav-item right>
              <router-link
                :to="{ name: 'signUp' }"
                style="text-decoration: none; color: black"
              >
                회원가입
              </router-link>
            </b-nav-item>
          </template>

          <template v-if="this.loginUser.id">
            <b-nav-item right>
              <div style="text-decoration: none; color: black; cursor: default">
                {{ this.loginUser.name }}님
              </div>
            </b-nav-item>

            <b-nav-item right>
              <router-link
                @click.native="logout"
                :to="{ name: 'home' }"
                style="text-decoration: none; color: black"
              >
                로그아웃
              </router-link>
            </b-nav-item>
            <b-nav-item right>
              <router-link
                :to="{ name: 'info' }"
                style="text-decoration: none; color: black"
                >회원정보</router-link
              >
            </b-nav-item>
          </template>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import image from "@/assets/logo.jpg";
import { mapGetters, mapActions } from "vuex";
// import http from "@/api/http";
export default {
  name: "HeaderNaviBar",
  data() {
    return {
      image,
    };
  },
  computed: {
    ...mapGetters(["loginUser"]),
  },
  // created() {
  //   http.get("board/root").then(({ res }) => {
  //     console.log(res);
  //   });
  // },
  methods: {
    ...mapActions(["logout"]),
  },
};
</script>

<style>
a {
  text-decoration: none;
}
#navbarr {
  text-decoration: none;
  font-weight: bold;
  font-size: 15pt;
  color: black;
}
a:link {
  text-decoration: none;
}

a:visited {
  text-decoration: none;
}

a:hover {
  color: gold;
}
</style>
