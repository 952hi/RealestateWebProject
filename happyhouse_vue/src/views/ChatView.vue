<template>
  <b-container class="bv-example-row mt-3 text-center">
    <b-row class="pt-3">
      <!-- 채팅 리스트 -->
      <b-col cols="3">
        <b-row>
          <b-card class="w-100">
            <b-row><br /></b-row>
            <b-row>
              <b-col> <b-icon icon="people-fill" /> 채팅 연결</b-col>
            </b-row>
            <b-row><br /></b-row>
          </b-card>
        </b-row>
        <b-row>
          <b-list-group class="w-100">
            <!-- 사용자 측면에서 불러오기 -->
            <template v-for="room in chatroomlist">
              <!-- <router-link
                :key="room.no"
                :to="{
                  name: 'chatList',
                  params: {
                    no: room.no,
                    receiver:
                      room.user_id == loginUser.id
                        ? room.seller_id
                        : room.user_id,
                  },
                }"
              > -->
              <b-list-group-item
                :key="room.no"
                button
                @click="
                  loadChatHistory(
                    room.no,
                    room.user_id == loginUser.id ? room.seller_id : room.user_id
                  ),
                    changeIconState(room),
                    forceRerender()
                "
              >
                <template v-if="room.seller_id != loginUser.id">
                  {{ room.seller_id }}
                </template>
                <template v-else>
                  {{ room.user_id }}
                </template>
                <template
                  v-if="
                    room.user_id == loginUser.id && room.userside_alert == 1
                  "
                >
                  <b-icon icon="messenger" variant="success"></b-icon>
                </template>
                <template
                  v-else-if="
                    room.seller_id == loginUser.id && room.sellerside_alert == 1
                  "
                >
                  <b-icon icon="messenger" variant="success"></b-icon>
                </template>
              </b-list-group-item>
              <!-- </router-link> -->
            </template>
          </b-list-group>
        </b-row>
      </b-col>
      <!-- 채팅창 -->
      <b-col cols="8">
        <b-jumbotron style="height: 800px">
          <div id="chartt">
            <b-row>
              <b-col>
                <router-view :key="ComputedComponentKey" />
              </b-col>
            </b-row>
          </div>
        </b-jumbotron>
      </b-col>
    </b-row>
  </b-container>
</template>
<script>
import { mapGetters, mapActions } from "vuex";
import http from "@/api/http.js";
export default {
  name: "BoardView",
  data() {
    return { chatroomlist: "", componentKey: 0 };
  },
  computed: {
    ...mapGetters(["loginUser"]),
    ComputedComponentKey() {
      return this.componentKey;
    },
  },
  methods: {
    ...mapActions(["getChatHistory"]),
    forceRerender() {
      this.componentKey++;
    },
    changeIconState(room) {
      if (room.seller_id == this.loginUser.id) room.sellerside_alert = 0;
      else room.userside_alert = 0;
    },

    async LoadChatRoomList() {
      await http
        .get(`/chat/${this.loginUser.id}/${this.loginUser.grade}`)
        .then((res) => {
          console.log("LoadChatRoomList : ", res.data);
          this.chatroomlist = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    async loadChatHistory(no, user_id) {
      console.log("loadChatHistory : ", no, " ", user_id);
      await http
        .get(`/chat/${no}`)
        .then((res) => {
          console.log("loadChatHistory");
          this.getChatHistory(res.data);
          // this.chatHistory = res.data;
          this.$router.push({
            name: "chatList",
            params: { no: no, receiver: user_id },
          });
        })
        .catch((err) => {
          console.log("loadChatHistory err : ", err);
        });
    },
  },

  created() {
    if (!this.loginUser.id) {
      alert("로그인이 필요합니다.");
      this.$router.push({ name: "signIn" });
    } else {
      this.LoadChatRoomList();
    }
  },
};
</script>
<style scoped>
.underline-hotpink {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(231, 27, 139, 0.3) 30%
  );
}
</style>
