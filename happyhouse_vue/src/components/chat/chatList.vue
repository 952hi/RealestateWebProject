<template>
  <div>
    <b-jumbotron
      border-variant="dark"
      class="overflow-auto"
      style="height: 640px; background: #0099ef"
    >
      <b-row>
        <b-col>
          <!-- 각 채팅 기록만큼 반복 -->
          <template v-for="(chat, index) in gettersChatHistory">
            {{ DayDevider(index) }}
            <!-- 내 아이디인 경우 -->
            <b-row :key="chat.no" :id="index">
              <template v-if="chat.id == loginUser.id">
                <b-col>
                  <b-row align-h="end">
                    <b-col cols="7">
                      <b-row>
                        <b-col class="text-right" style="color: white">{{
                          loginUser.id
                        }}</b-col>
                      </b-row>
                      <b-row align-h="end">
                        <b-col class="text-right" style="color: white">
                          {{ DateFormatter(chat.date) }}
                        </b-col>
                        <b-col>
                          <div
                            style="
                              background-color: #ffbe49;
                              border-radius: 10px;
                              color: black;
                            "
                          >
                            {{ chat.message }}
                          </div>
                        </b-col>
                      </b-row>
                    </b-col>
                  </b-row>
                </b-col>
              </template>

              <template v-else>
                <b-col>
                  <b-row>
                    <b-col cols="7">
                      <b-row>
                        <b-col class="text-left" style="color: white">{{
                          chat.id
                        }}</b-col>
                      </b-row>
                      <b-row>
                        <b-col>
                          <div
                            style="
                              background-color: #ffbe49;
                              border-radius: 10px;
                              color: black;
                            "
                          >
                            {{ chat.message }}
                          </div>
                        </b-col>
                        <b-col class="text-left" style="color: white">
                          {{ DateFormatter(chat.date) }}
                        </b-col>
                      </b-row>
                    </b-col>
                  </b-row>
                </b-col>
              </template>
            </b-row>
          </template>
          <!-- 실시간으로 추가되는 메세지들 -->
          <template v-for="chat in computedChatList">
            <b-row :key="chat.crypto" :id="chat.crypto">
              <!-- 송신자가 본인일 경우 -->
              <template v-if="chat.sender == loginUser.id">
                <b-col>
                  <b-row align-h="end">
                    <b-col cols="7">
                      <b-row>
                        <b-col class="text-right" style="color: white">{{
                          loginUser.id
                        }}</b-col>
                      </b-row>
                      <b-row align-h="end">
                        <b-col class="text-right" style="color: white">
                          {{ DateFormatter(chat.date) }}
                        </b-col>
                        <b-col>
                          <div
                            style="
                              background-color: #ffbe49;
                              border-radius: 10px;
                              color: black;
                            "
                          >
                            {{ chat.message }}
                          </div>
                        </b-col>
                      </b-row>
                    </b-col>
                  </b-row>
                </b-col>
              </template>
              <!-- 송신자가 본인이 아닐 경우 -->
              <template v-else-if="chat.receiver == loginUser.id">
                <b-col>
                  <b-row>
                    <b-col cols="7">
                      <b-row>
                        <b-col class="text-left" style="color: white">{{
                          chat.sender
                        }}</b-col>
                      </b-row>
                      <b-row>
                        <b-col>
                          <div
                            style="
                              background-color: #ffbe49;
                              border-radius: 10px;
                              color: black;
                              border-radius: 10px;
                            "
                          >
                            {{ chat.message }}
                          </div>
                        </b-col>
                        <b-col class="text-left" style="color: white">
                          {{ DateFormatter(chat.date) }}
                        </b-col>
                      </b-row>
                    </b-col>
                  </b-row>
                </b-col>
              </template>
              <!-- 실시간 입력으로 받는 값들을 계속해서 추가 -->
            </b-row>
          </template>
        </b-col>
      </b-row>
    </b-jumbotron>
    <template
      v-if="
        this.$route.params.receiver != 'admin' || this.loginUser.id == 'admin'
      "
    >
      <b-row align-v="end">
        <b-col>
          <b-form-group>
            <b-row>
              <b-col cols="10">
                <b-form-input
                  id="input-default"
                  @keyup.enter="sendMessage"
                  v-model="crntInputMessage"
                  placeholder="메세지를 입력해주세요."
                />
              </b-col>
              <b-col>
                <a name="target"></a>
                <b-button
                  pill
                  block
                  variant="success"
                  @click="sendMessage"
                  ref="button"
                >
                  전송
                </b-button>
              </b-col>
            </b-row>
          </b-form-group>
        </b-col>
      </b-row>
    </template>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import http from "@/api/http.js";
export default {
  name: "chatList",
  data() {
    return {
      crnt_date: "",
      room_no: "",
      receiver: "",
      crntInputMessage: "",
      realtimeChat: [],
    };
  },
  computed: {
    ...mapGetters(["loginUser", "gettersChatHistory"]),
    computedGettersChatHistory() {
      if (this.gettersChatHistory == undefined) return {};
      return this.gettersChatHistory;
    },
    computedChatList() {
      return this.realtimeChat;
    },
  },
  methods: {
    ...mapActions(["getChatHistory"]),
    moveToDown() {
      console.log(Object.keys(this.chatHistory).length);

      window.location.href =
        "#" + String(Object.keys(this.chatHistory).length - 1);
    },
    realTimeChatMoveToDown() {
      if (this.computedChatList[this.computedChatList.length - 2] == undefined)
        return;
      window.location.href =
        "#" +
        String(this.computedChatList[this.computedChatList.length - 1].crypto);
      console.log(
        "realTimeChatMoveToDown ",
        String(this.computedChatList[this.computedChatList.length - 1].crypto)
      );
    },
    DayDevider(index) {
      if (index - 1 < 0) {
        let first = this.computedGettersChatHistory[0].date.split(" ");
        let firstSplit = first[0].split("-");
        return (
          firstSplit[0] + "년 " + firstSplit[1] + "월 " + firstSplit[2] + "일"
        );
      }

      let before = this.computedGettersChatHistory[index - 1].date.split(" ");
      let crnt = this.computedGettersChatHistory[index].date.split(" ");

      if (before[0] != crnt[0]) {
        let crntChatDaySplit = crnt[0].split("-");
        return (
          crntChatDaySplit[0] +
          "년 " +
          Number(crntChatDaySplit[1]) +
          "월 " +
          Number(crntChatDaySplit[2]) +
          "일"
        );
      }
    },
    DateFormatter(date) {
      if (date == undefined) {
        let date = new Date();
        let addMinuteZero = "";
        if (date.getMinutes() < 10) addMinuteZero = "0";
        return date.getHours() + ":" + addMinuteZero + date.getMinutes();
      }
      let splitDate = date.split(" ");
      let splitTIme = splitDate[1].split(":");
      return Number(splitTIme[0]) + ":" + splitTIme[1];
    },

    async UpdateAlertOff() {
      //유저라면
      let sendData = {};
      sendData.id = this.loginUser.id;
      sendData.grade = Number(this.loginUser.grade);
      sendData.no = Number(this.room_no);
      await http
        .put("/chat/off", sendData)
        .then((res) => {
          console.log("UpdateAlertOff res : ", res);
        })
        .catch((err) => {
          console.log("UpdateAlertOff err", err);
        });
    },

    async loadChatHistory() {
      this.room_no = this.$route.params.no;
      this.receiver = this.$route.params.receiver;
      await http
        .get(`/chat/${this.room_no}`)
        .then((res) => {
          this.getChatHistory = res.data;
          this.chatHistory = res.data;
          console.log("loadChatHistory");
        })
        .catch((err) => {
          console.log("loadChatHistory err : ", err);
        });
    },

    //메세지 전송
    sendMessage() {
      if (this.crntInputMessage.trim() != "") {
        let sendMessage = {};
        let random = new Uint32Array(1);
        window.crypto.getRandomValues(random);
        sendMessage.crypto = Number(random[0]);
        sendMessage.message = this.crntInputMessage;
        sendMessage.sender = this.loginUser.id;
        sendMessage.receiver = this.receiver;
        sendMessage.room_no = Number(this.room_no);
        sendMessage.loginUserGrade = Number(this.loginUser.grade);
        this.webSocket.send(JSON.stringify(sendMessage));
      }

      this.crntInputMessage = "";
    },
    //소켓 통신 종료
    closeWebSocket() {
      this.webSocket.close();
    },
    //소켓 통신 연결
    //0 : onopen, 1 : 데이터 송수신, 2 : onclose
    async openWebSocket() {
      this.webSocket = new WebSocket(`ws://localhost/vue/livechat`);

      this.webSocket.onopen = (event) => {
        console.log("openWebSocket open : ", event);
        this.webSocket.send(
          JSON.stringify({ setSession: 1, id: this.loginUser.id })
        );
      };

      //데이터 수신
      this.webSocket.onmessage = (event) => {
        const chatMessageDto = JSON.parse(event.data);
        console.log("onmessage : ", chatMessageDto);
        this.realtimeChat.push(chatMessageDto);
      };

      this.webSocket.onclose = (event) => {
        console.log("close : ", event);
      };
    },
  },
  async created() {
    await this.loadChatHistory();
    await this.openWebSocket();
    await this.UpdateAlertOff();
    await this.moveToDown();
  },
  destroyed() {
    this.closeWebSocket();
  },
};
</script>

<style scoped>
template * {
  color: white;
}
</style>
