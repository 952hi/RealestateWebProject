import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/chat",
    name: "chat",
    component: () => import("@/views/ChatView.vue"),
    children: [
      {
        path: "chatlist/:no/:receiver",
        name: "chatList",
        component: () => import("@/components/chat/chatList.vue"),
      },
    ],
  },
  {
    path: "/user",
    name: "user",
    component: () => import("@/views/MemberView.vue"),
    children: [
      {
        path: "signin",
        name: "signIn",
        component: () => import("@/components/user/MemberLogin.vue"),
      },
      {
        path: "singup",
        name: "signUp",
        component: () => import("@/components/user/MemberRegister.vue"),
      },
      {
        path: "success",
        name: "success",
        component: () => import("@/components/user/loginSuccess.vue"),
      },
      {
        path: "info",
        name: "info",
        component: () => import("@/components/user/MemberMyPage.vue"),
      },
      {
        path: "modify",
        name: "modify",
        component: () => import("@/components/user/MemberModify.vue"),
      },
    ],
  },
  {
    path: "/detail/:articleno",
    name: "detail",
    component: () => import("@/views/BoardDetail.vue"),
  },
  {
    path: "/board",
    name: "board",
    component: () => import("@/views/BoardView.vue"),
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "boardList",
        component: () => import("@/components/board/BoardList.vue"),
      },
      {
        path: "write",
        name: "boardRegister",
        component: () => import("@/components/board/BoardRegister.vue"),
      },
      {
        path: "modify/:articleno",
        name: "boardModify",
        component: () => import("@/components/board/BoardModify.vue"),
      },
      {
        path: "delete",
        name: "boardDelete",
        component: () => import("@/components/board/BoardDelete.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
  scrollBehavior() {},
});

export default router;
