import Vue from "vue";
import Vuex from "vuex";
import http from "@/api/http";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    sidos: [{ value: null, text: "선택" }],
    guguns: [{ value: null, text: "선택" }],
    dongs: [{ value: null, text: "선택" }],
    observesidos: [],
    observegudongs: [],
    loginUser: {
      id: null,
      pw: null,
      email: null,
      age: null,
      name: null,
      grade: null,
      time: null,
    },
    chatHistory: {},
  },
  getters: {
    gettersChatHistory(state) {
      return state.chatHistory;
    },
    loginUser(state) {
      return state.loginUser;
    },
    allObserveSidos(state) {
      return state.observesidos;
    },
    allObserveGuDongs(state) {
      return state.observegudongs;
    },
  },
  mutations: {
    SET_CHAT_HISTORY(state, chatdata) {
      state.chatHistory = chatdata;
    },
    //////////////////////  회원 관련 기능  //////////////////////
    SET_USER_DATA(state, userdata) {
      state.loginUser = userdata;
    },
    CLEAR_USER_DATA(state) {
      state.loginUser = {};
    },
    //////////////////////   observe start   //////////////////////
    SET_OBSV_SIDO_LIST(state, sidos) {
      state.observesidos = sidos;
    },
    SET_OBSV_GUDONG_LIST(state, gudongs) {
      state.observegudongs = gudongs;
    },
    /////////////////////////////// House start /////////////////////////////////////
    SET_SIDO_LIST(state, sidos) {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST(state, guguns) {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST(state, dongs) {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongCode, text: dong.dongName });
      });
    },
    CLEAR_SIDO_LIST(state) {
      state.sidos = [{ value: null, text: "선택" }];
    },
    CLEAR_GUGUN_LIST(state) {
      state.guguns = [{ value: null, text: "선택" }];
    },
    CLEAR_DONG_LIST(state) {
      state.dongs = [{ value: null, text: "선택" }];
    },
    /////////////////////////////// House end /////////////////////////////////////
  },
  actions: {
    getChatHistory({ commit }, chatData) {
      commit("SET_CHAT_HISTORY", chatData);
    },
    //////////////////////   User start   //////////////////////
    loginSuccess({ commit }, userInfo) {
      commit("SET_USER_DATA", userInfo);
    },
    logout({ commit }) {
      commit("CLEAR_USER_DATA");
    },
    //////////////////////   observe start   //////////////////////
    getObserveSidos({ commit }) {
      http
        .get(`observe/sido`)
        .then(({ data }) => {
          commit("SET_OBSV_SIDO_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },

    getObserveGuDongs({ commit }, Sido) {
      console.log(Sido);
      http
        .get(`observe/gudong/${Sido}`)
        .then(({ data }) => {
          commit("SET_OBSV_GUDONG_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    /////////////////////////////// House start /////////////////////////////////////
    getSido({ commit }) {
      http
        .get(`/Building/Si`)
        .then(({ data }) => {
          // console.log(data);
          commit("SET_SIDO_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getGugun({ commit }, sidoCode) {
      const params = { sido: sidoCode };
      http
        .get(`/Building/Gu`, { params })
        .then(({ data }) => {
          // console.log(commit, response);
          commit("SET_GUGUN_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getDong({ commit }, gugunCode) {
      const params = { gugun: gugunCode };
      http
        .get(`/Building/Dong`, { params })
        .then(({ data }) => {
          commit("SET_DONG_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    /////////////////////////////// House end /////////////////////////////////////

    //////////////////////////// Todo List start //////////////////////////////////

    // destructuring 활용
    createTodo({ commit }, todoItem) {
      commit("CREATE_TODO", todoItem);
    },
    actionDeleteTodo({ commit }, todoItem) {
      commit("DELETE_TODO", todoItem);
    },
    actionUpdateTodoStatus({ commit }, todoItem) {
      commit("UPDATE_TODO_STATUS", todoItem);
    },
    //////////////////////////// Todo List end //////////////////////////////////
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});
