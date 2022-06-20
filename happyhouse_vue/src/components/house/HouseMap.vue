<template>
  <div id="wrap">
    <div id="map"></div>
    <div id="board">
      <b-row class="mt-1 mb-1 ml-1 mr-1 text-center dark"> </b-row>

      <div>
        <b-tabs content-class="mt-3">
          <b-tab title="시세" active @click="linkClass(0)"
            ><div>
              <b-row
                ><b-col class="mx-auto">
                  <b-button block variant="primary" @click="openfilter"
                    >필터설정</b-button
                  ></b-col
                ><b-col class="mx-auto">
                  <b-button block variant="danger" @click="clear"
                    >초기화</b-button
                  ></b-col
                ></b-row
              >

              <div id="filter" v-if="filtercheck">
                <b-row class="mt-1 mb-1 ml-1 mr-1 text-center mx-auto">
                  <b-col class="sm-3">
                    시<b-form-select
                      id="Sival"
                      v-model="sidoCode"
                      :options="sidos"
                      @change="gugunList"
                      size="sm"
                    ></b-form-select>
                  </b-col>
                  <b-col class="sm-3">
                    구<b-form-select
                      v-model="gugunCode"
                      :options="guguns"
                      @change="dongList"
                      size="sm"
                    ></b-form-select>
                  </b-col>
                  <b-col class="sm-3">
                    동<b-form-select
                      v-model="dongCode"
                      :options="dongs"
                      size="sm"
                      @change="getDeal(0)"
                    ></b-form-select>
                  </b-col>
                </b-row>
                <b-row class="text-center mt-5 mb-3">
                  <b-col cols="3"><div>가격(만)</div></b-col>
                  <b-col class="mt-1 mb-1 center" cols="8"
                    ><Slider
                      v-model="worth.value"
                      v-bind="worth"
                      @change="changeWorth"
                    ></Slider
                  ></b-col>
                </b-row>
                <b-row class="text-center mt-5 mb-3">
                  <b-col cols="3"><div>건축년도</div></b-col>
                  <b-col class="mt-1 mb-1" cols="8"
                    ><Slider
                      v-model="term.value"
                      v-bind="term"
                      @change="changeTerm"
                      >기간</Slider
                    ></b-col
                  >
                </b-row>
                <b-row class="text-center mt-5 mb-3">
                  <b-col cols="3"><div>거래년도</div></b-col>
                  <b-col class="mt-1 mb-1" cols="8"
                    ><Slider
                      v-model="trade.value"
                      v-bind="trade"
                      @change="changeTrade"
                      >기간</Slider
                    ></b-col
                  >
                </b-row>
              </div>
              <div id="value" v-if="check">
                <b-table
                  class="text-center"
                  hover
                  sticky-header="710px"
                  responsive
                  :items="housedeal"
                  :fields="fields"
                  selectable
                  select-mode="single"
                  @row-selected="checkvalue"
                ></b-table>
              </div>
              <div id="deal" v-if="detail">
                <b-button block variant="primary" @click="dealToggle"
                  >뒤로가기</b-button
                >
                <b-table
                  hover
                  sticky-header="710px"
                  responsive
                  :fields="field2"
                  :items="dealhistory"
                  selectable
                  select-mode="single"
                  class="text-center"
                ></b-table>
              </div></div
          ></b-tab>
          <b-tab title="매물" @click="linkClass(1)">
            <div id="cardBoard">
              <b-card
                no-body
                class="overflow-auto center"
                style="max-width: 500px"
                v-for="(article, index) in houseboards"
                :key="index"
              >
                <b-row no-gutters>
                  <b-col md="6">
                    <b-card-img
                      :src="`http://localhost/vue/board/image/${article.no}/${article.name}`"
                      img-height="50"
                      alt="Image"
                      class="rounded-0 mx-auto"
                    ></b-card-img>
                  </b-col>
                  <b-col md="6">
                    <b-card-body :title="article.title">
                      <b-card-text>
                        <b-row hover>{{
                          article.detail | oneLineDetail
                        }}</b-row>
                        <b-row v-if="article.monthlyFee > 0"
                          >보증금 : {{ article.deposit }} / 월세 :
                          {{ article.monthlyFee }}</b-row
                        >
                        <b-row v-else> 매매가 : {{ article.deposit }}</b-row>
                        <b-row>{{ article.roadnameAddress }}</b-row>
                        <b-row
                          ><b-button
                            block
                            variant="primary"
                            @click="viewArticle(article)"
                            >상세보기</b-button
                          ></b-row
                        >
                      </b-card-text>
                    </b-card-body>
                  </b-col>
                </b-row>
              </b-card>
            </div>
          </b-tab>
          <b-tab :title="useraddress" disabled></b-tab>
        </b-tabs>
      </div>
    </div>

    <div class="category">
      <ul>
        <li id="coffeeMenu" @click="starcheck">
          <span class="list coffee"></span>
          카페
        </li>
        <li id="subwayMenu" @click="subwaycheck">
          <span class="list subway"></span>
          지하철
        </li>
        <li id="allMenu" @click="allcheck">
          <span class="list all"></span>
          전체
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import Slider from "@vueform/slider/dist/slider.vue2.js";
import apt from "@/assets/apt.png";
import {
  getMarker,
  starbucksStore,
  subwayStore,
  changeMarker,
  setCoffeeMarkers,
  setStoreMarkers,
  geo,
  deleMaker,
  atoll,
} from "@/api/map";
import http from "@/api/http";
export default {
  name: "HouseSearchBar",
  components: {
    Slider,
  },
  data() {
    return {
      fields: [
        { key: "aptName", label: "아파트이름" },
        { key: "dealAmount", label: "가격" },
      ],
      field2: [
        { key: "dealYear", label: "거래년도" },
        { key: "dealAmount", label: "가격" },
        { key: "msize", label: "평수" },
        { key: "dealperSize", label: "평당가격" },
      ],
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
      housedeal: [],
      filtercheck: false,
      check: false,
      detail: false,
      value: 5,
      path: "",
      dealhistory: [],
      cafe: [],
      subways: [],
      houseboards: [],
      allcolor: false,
      subcolor: false,
      cofcolor: false,
      useraddress: "",
      settingcolor: false,
      term: {
        value: [1990, 2022],
        step: 1,
        format: {
          suffix: "년",
          decimals: 0,
        },
        tooltipPosition: "",
        min: 1990,
        max: 2022,
        lazy: true,
        merge: 2010,
      },
      worth: {
        value: [0, 500000],
        step: 500,
        format: {
          thousand: ",",
          decimals: 0,
        },
        tooltipPosition: "",
        lazy: true,
        max: 500000,
      },
      trade: {
        value: [1990, 2022],
        step: 1,
        format: {
          suffix: "년",
          decimals: 0,
        },
        tooltipPosition: "",
        merge: 2010,
        min: 1990,
        max: 2022,
        lazy: true,
      },
      apt,
    };
  },
  filters: {
    oneLineDetail(data) {
      if (data == undefined) return "";
      return data.split("<br>")[0];
    },
  },
  computed: {
    ...mapState(["sidos", "guguns", "dongs"]),
  },
  created() {
    /* global kakao */ // eslint-disable-line no-unused-vars
    this.CLEAR_SIDO_LIST();
    this.getSido();
    http
      .get(`/Building/Subway`)
      .then(({ data }) => {
        subwayStore(data);
      })
      .catch((error) => {
        console.log(error);
      });
    http
      .get(`/Building/around`)
      .then(({ data }) => {
        starbucksStore(data);
      })
      .catch((error) => {
        console.log(error);
      });
    http
      .get(`/board/allselect`)
      .then(({ data }) => {
        this.houseboards = data;
      })
      .catch((error) => {
        console.log(error);
      });
    geo((rst) => {
      this.useraddress = "접속위치: " + rst;
    });
  },
  methods: {
    ...mapActions(["getSido", "getGugun", "getDong"]),
    ...mapMutations(["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST", "CLEAR_DONG_LIST"]),

    gugunList() {
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      this.housedeal = [];
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    dongList() {
      this.CLEAR_DONG_LIST();
      this.dongCode = null;
      this.housedeal = [];
      this.check = false;
      this.detail = false;
      if (this.gugunCode) this.getDong(this.gugunCode);
    },
    async getDeal(type, val) {
      if (this.dongCode) {
        if (type == 1) {
          //돈
          this.worth.value[0] = val[0];
          this.worth.value[1] = val[1];
        } else if (type == 2) {
          //시간
          this.term.value[0] = val[0];
          this.term.value[1] = val[1];
        } else if (type == 3) {
          //시간
          this.trade.value[0] = val[0];
          this.trade.value[1] = val[1];
        }
        this.check = true;
        this.housedeal = [];
        const params = {
          dong: this.dongCode,
          minval: this.worth.value[0],
          maxval: this.worth.value[1],
          termlow: this.term.value[0],
          termhigh: this.term.value[1],
          tradelow: this.trade.value[0],
          tradehigh: this.trade.value[1],
        };
        console.log(params);
        http
          .get(`/Building/House`, { params })
          .then(({ data }) => {
            this.detail = false;

            if (data.length > 0) {
              getMarker(data);
              this.housedeal = data;
            } else {
              alert("거래내역이 없습니다");
            }
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    checkvalue(data) {
      if (data[0].aptCode != null) {
        this.dealhistory = [];
        const params = { code: data[0].aptCode };
        http
          .get(`/Building/Deal`, { params })
          .then(({ data }) => {
            this.dealhistory = data;
            this.detail = true;
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        alert("오류발생");
      }
    },
    resultToggle() {
      this.check = !this.check;
    },
    dealToggle() {
      this.detail = !this.detail;
    },
    starcheck() {
      if (!this.cofcolor) {
        changeMarker("coffee");
        this.cofcolor = true;
      } else {
        this.cofcolor = false;
        setCoffeeMarkers(null);
        var coffeeMenu = document.getElementById("coffeeMenu");
        coffeeMenu.className = "";
      }
    },
    subwaycheck() {
      if (!this.subcolor) {
        changeMarker("subway");
        this.subcolor = true;
      } else {
        this.subcolor = false;
        setStoreMarkers(null);
        var subwayMenu = document.getElementById("subwayMenu");
        subwayMenu.className = "";
      }
    },
    allcheck() {
      if (!this.allcolor) {
        changeMarker("all");
        this.allcolor = true;
      } else {
        this.allcolor = false;
        this.subcolor = false;
        this.cofcolor = false;
        setStoreMarkers(null);
        setCoffeeMarkers(null);
        var coffeeMenu = document.getElementById("coffeeMenu");
        var subwayMenu = document.getElementById("subwayMenu");
        var allMenu = document.getElementById("allMenu");
        coffeeMenu.className = "";
        subwayMenu.className = "";
        allMenu.className = "";
      }
    },
    openfilter() {
      this.filtercheck = !this.filtercheck;
    },
    clear() {
      this.check = false;
      this.detail = false;
      this.worth.value = [0, 500000];
      this.term.value = [1990, 2022];
      this.trade.value = [1990, 2022];
      deleMaker();
    },
    viewArticle(article) {
      this.$router.push({
        name: "detail",
        params: { articleno: article.no },
      });
    },
    // geo((rst) => {
    //   this.useraddress = "접속위치: " + rst;
    // });
    changeWorth(data) {
      console.log(data);
      this.getDeal(1, data);
    },
    changeTrade(data) {
      console.log(data);
      this.getDeal(3, data);
    },
    changeTerm(data) {
      console.log(data);
      this.getDeal(2, data);
    },
    linkClass(data) {
      if (data == 1) {
        this.clear();
        atoll(this.houseboards);
      } else if (data == 0) {
        this.clear();
      }
    },
  },
};
</script>

<style scoped>
::-webkit-scrollbar {
  display: none;
}
#cardBoard {
  height: 700px;
  width: 500px;
  overflow-y: auto;
}
#map {
  width: 100%;
  height: 100%;
}
#filter {
  width: 100%;
  z-index: 1;
  background: white;
}
#wrap {
  position: relative;
  width: 100%;
  height: 850px;
}
#result {
  position: relative;
  top: 0px;
  right: 0px;
  width: 400px;
  height: 100%;
}
#value {
  position: absolute;
  width: 100%;
  height: 700px;
  background: white;
}
#deal {
  position: relative;
  width: 100%;
  height: 710px;
  z-index: 2;
  background: white;
}
#board {
  position: absolute;
  top: 0px;
  right: 0px;
  width: 500px;
  height: 100%;
  z-index: 1;
  background: white;
  border: 1px solid black;
}
.category {
  position: absolute;
  overflow: hidden;
  top: 10px;
  left: 10px;
  width: 158px;
  height: 60px;
  z-index: 10;
  border: 1px solid black;
  font-family: "Malgun Gothic", "맑은 고딕", sans-serif;
  font-size: 12px;
  text-align: center;
  background-color: #fff;
}
.category li {
  list-style: none;
  float: left;
  width: 50px;
  height: 70px;
  padding-top: 5px;
  padding-left: 1px;
  margin: 0 auto;
  cursor: pointer;
}
.category .menu_selected {
  background: rgb(20, 181, 250);
  color: #fff;
  border-left: 1px solid #915b2f;
  border-right: 1px solid #915b2f;
  margin: 0 -1px;
}
.category,
.category * {
  margin: 0;
  padding: 0;
  color: #000;
}
.category .list {
  display: block;
  margin: 0 auto 2px;
  width: 32px;
  height: 32px;
}
.coffee {
  background: url("@/assets/starbucks.png") no-repeat;
}
.subway {
  background: url("@/assets/subway.png") no-repeat;
}
.all {
  background: url("@/assets/all.png") no-repeat;
}
.filter {
  background: url("@/assets/filter.png") no-repeat;
}
</style>
<style src="@vueform/slider/themes/default.css"></style>
