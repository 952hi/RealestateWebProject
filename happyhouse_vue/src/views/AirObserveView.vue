<template>
  <b-container class="bv-example-row mt-3 text-center">
    <h3 class="underline-steelblue">
      <b-icon icon="house"></b-icon> HappyHouse_vue_Gumi_04_이청_이현일
    </h3>
    <b-row>
      <b-col>
        <b-jumbotron
          bg-variant="muted"
          text-variant="dark"
          border-variant="dark"
        >
          <template #header>HaapyHouse with Vue</template>

          <template #lead> </template>

          <hr class="my-4" />
          <b-row align-h="center">
            <b-card no-body class="overflow-hidden" style="max-width: 600px">
              <b-row no-gutters>
                <b-col md="6">
                  <b-card-img
                    src="https://previews.123rf.com/images/nata7777/nata77771604/nata7777160400468/55859409-%ED%91%B8%EB%A5%B8-%ED%95%98%EB%8A%98%EA%B3%BC-%ED%83%9C%EC%96%91%EC%9E%85%EB%8B%88%EB%8B%A4-.jpg"
                    alt="blue sky"
                    class="rounded-0"
                  >
                  </b-card-img>
                </b-col>
                <b-col md="6"
                  ><b-card-body title="this is...">
                    <b-card-text>
                      미세먼지 관측소별로 측정하는 실시간 데이터를 확인할 수
                      있습니다.
                    </b-card-text>
                  </b-card-body>
                </b-col>
              </b-row>
            </b-card>
          </b-row>
          <br />
          <br />
          <b-row>
            <b-col>
              <b-row align-h="center">1. 지역 선택</b-row>
              <b-row align-h="center"
                ><select @change="onChangeSido($event)">
                  <option value="">선택</option>
                  <template v-if="allObserveSidos">
                    <option
                      :value="ObserveSido"
                      v-for="(ObserveSido, index) in allObserveSidos"
                      :key="index"
                    >
                      {{ ObserveSido }}
                    </option>
                  </template>
                </select>
              </b-row>
            </b-col>
            <b-col>
              <b-row align-h="center">2. 관측소 선택</b-row>
              <b-row align-h="center">
                <select @change="onChangeGuDong($event)">
                  <option value="">선택</option>
                  <template v-if="allObserveGuDongs">
                    <option
                      v-for="ObserveGuDong in allObserveGuDongs"
                      :key="ObserveGuDong"
                    >
                      {{ ObserveGuDong }}
                    </option>
                  </template>
                </select>
              </b-row>
            </b-col>
            <b-col align-v="center" align-h="center">
              <b-row align-h="center">3. 관측소 측정 값</b-row>
              <b-row>
                <b-table
                  caption-bottom
                  v-if="returnObserveData"
                  stacked
                  striped
                  hover
                  :items="returnObserveData"
                ></b-table>
              </b-row>
              <b-row align-h="center">출처 : 에어코리아 API</b-row>
            </b-col>
          </b-row>
        </b-jumbotron>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapGetters, mapState, mapActions } from "vuex";
import http from "@/api/http.js";
export default {
  name: "HomeView",
  props: {
    msg: String,
  },
  data() {
    return {
      ...mapState(["observesidos", "observegudongs"]),
      key: "",
      observeData: [],
    };
  },
  methods: {
    ...mapActions(["getObserveSidos", "getObserveGuDongs"]),
    onChangeSido(event) {
      this.getObserveGuDongs(event.target.value);
    },
    onChangeGuDong(event) {
      let stationName = encodeURI(event.target.value);
      let API =
        "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?returnType=json&numOfRows=100&pageNo=1&dataTerm=DAILY&ver=1.0&serviceKey=";
      API += process.env.VUE_APP_AIR_KEY;
      API += "&stationName=" + stationName;
      console.log(API);
      http
        .get(API)
        .then((res) => {
          let tmpData = res.data.response.body.items[0];
          let returnData = {};
          returnData.측정일 = tmpData.dataTime;
          returnData["10pm"] = tmpData.pm10Value;
          returnData["2.5pm"] = tmpData.pm25Value;
          returnData["통합대기환경수치"] = tmpData.khaiValue;
          returnData["통합대기환경지수"] = tmpData.khaiGrade;
          console.log(returnData);
          this.observeData.push(returnData);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  created() {
    this.getObserveSidos();
  },
  computed: {
    ...mapGetters(["allObserveSidos", "allObserveGuDongs"]),
    returnObserveData() {
      return this.observeData;
    },
  },
};
</script>

<style scoped>
.underline-steelblue {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(72, 190, 233, 0.3) 30%
  );
}
</style>
