<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <template v-if="loginUser.id == this.article.id">
        <b-col class="text-right" cols="11">
          <!-- <b-button
            variant="outline-info"
            size="sm"
            @click="moveModifyArticle"
            class="mr-2"
            >글수정</b-button
          > -->
          <b-button variant="outline-danger" size="sm" @click="deleteArticle"
            >글삭제</b-button
          >
        </b-col>
        <b-col></b-col>
      </template>
    </b-row>
    <b-row class="mt-3">
      <b-col cols="7">
        <b-row>
          <div style="max-height: 500px; max-width: 690px">
            <b-carousel
              id="carousel-1"
              v-model="slide"
              :interval="4000"
              controls
              indicators
              img-width="1024px"
              img-height="500px"
              background="#ababab"
              style="text-shadow: 1px 1px 2px #333"
              @sliding-start="onSlideStart"
              @sliding-end="onSlideEnd"
            >
              <template v-for="(file, index) in article.fileList">
                <b-carousel-slide
                  :key="index"
                  :img-src="`${download_url}/board/image/${$route.params.articleno}/${file.original_name}`"
                  class="img-fluid"
                ></b-carousel-slide>
              </template>
            </b-carousel>
          </div>
        </b-row>
        <b-row class="pb-2">
          <b-card class="text-center w-100">
            <b-row class="b-between">
              <b-col class="text-left">{{ article.title }}</b-col>
              <b-col class="text-right" cols="4" style="font-size: 14px">
                작성일 {{ article.time }}
              </b-col>
            </b-row>
          </b-card>
        </b-row>
        <b-row class="pt-5">
          <b-col class="text-left">
            <!-- 왼쪽으로 붙이기 -->
            <h5>
              <strong>거래정보</strong>
              <hr style="border: 1px solid black" />
            </h5>
          </b-col>
        </b-row>
        <b-row style="font-size: 15px" align-v="center">
          <b-col>
            <b-row align-h="between">
              <b-col cols="4"><strong>계약형태</strong></b-col>
              <b-col> {{ computedContract }} </b-col>
            </b-row>
          </b-col>
          <b-col>
            <b-row align-h="between" align-v="center">
              <b-col><strong>가격정보</strong></b-col>
              <b-col cols="8">
                <template
                  v-if="
                    this.trade.contractOpt == 2 || this.trade.contractOpt == 1
                  "
                >
                  <b-row class="pb-5" align-v="center">
                    <b-col cols="4">보증금</b-col>
                    <b-col class="text-right">{{ trade.deposit }} 만원</b-col>
                  </b-row>
                  <b-row align-v="center">
                    <b-col cols="4">월세</b-col>
                    <b-col class="text-right">
                      {{ trade.monthlyFee }} 만원
                    </b-col>
                  </b-row>
                </template>

                <template v-else>
                  <b-row align-v="center">
                    <b-col cols="5">매매가</b-col>
                    <b-col class="text-right">{{ trade.deposit }} 만원</b-col>
                  </b-row>
                </template>
              </b-col>
            </b-row>
          </b-col>
        </b-row>

        <hr class="m-4" />

        <b-row style="font-size: 15px" align-v="center">
          <b-col>
            <b-row align-v="center">
              <b-col cols="5"><strong>공용 관리비</strong></b-col>
              <b-col cols="5"> </b-col>
              <b-col>{{ trade.commonMaintainFee }} 만원</b-col>
            </b-row>
          </b-col>
          <b-col>
            <b-row align-v="center">
              <b-col cols="4"><strong>공용 관리비 항목</strong></b-col>
              <b-col>
                <template v-if="computedCommonItemLength == 0"> 없음 </template>
                <template>
                  <span
                    v-for="(item, index) in trade.commonMaintainItem"
                    :key="index"
                  >
                    [{{ item }}]
                  </span>
                </template>
              </b-col>
            </b-row>
          </b-col>
        </b-row>

        <hr class="m-4" />

        <b-row style="font-size: 15px" align-v="center">
          <b-col>
            <b-row>
              <b-col cols="4"><strong>융자여부</strong></b-col>
              <b-col class="text-right"> {{ computedLoan }} </b-col>
            </b-row>
          </b-col>
          <b-col>
            <b-row align-v="center">
              <b-col cols="4"><strong>개별 사용료 항목</strong></b-col>
              <b-col>
                <template v-if="computedEachFeeItemLength == 0">
                  없음
                </template>
                <template>
                  <span v-for="(item, index) in trade.eachFeeItem" :key="index">
                    [{{ item }}]
                  </span>
                </template>
              </b-col>
            </b-row>
          </b-col>
        </b-row>

        <hr class="m-4" />

        <b-row style="font-size: 15px" align-v="center">
          <b-col>
            <b-row align-v="center">
              <b-col cols="4"><strong>입주가능일</strong></b-col>
              <b-col> </b-col>
              <b-col>{{ trade.date }} </b-col>
            </b-row>
          </b-col>
          <b-col>
            <b-row> </b-row>
          </b-col>
        </b-row>
        <b-row>
          <b-col cols="7">
            <b-collapse id="collapse-1" class="mt-2">
              <div></div>
            </b-collapse>
          </b-col>
        </b-row>

        <hr class="m-4" />

        <b-row class="pt-4">
          <b-col class="text-left">
            <!-- 왼쪽으로 붙이기 -->
            <h5>
              <strong>상세설명</strong>
              <hr style="border: 1px solid black" />
            </h5>
          </b-col>
        </b-row>
        <b-row>
          <b-card class="text-left w-100">
            <div v-html="computedDetail"></div>
          </b-card>
        </b-row>
        <b-row class="pt-5 b-between" align-v="center">
          <b-col class="text-left">
            <!-- 왼쪽으로 붙이기 -->
            <h5>
              <strong>위치</strong>
            </h5>
          </b-col>
          <b-col>
            <b-row>
              <b-col class="text-right">{{ computedRoadnameAddress }}</b-col>
            </b-row>
            <b-row>
              <b-col class="text-right">
                {{ computedDetailAddress }}
              </b-col>
            </b-row>
          </b-col>
        </b-row>
        <b-row>
          <div id="map" style="width: 100%; height: 300px" class="my-4"></div>
        </b-row>
      </b-col>
      <b-col cols="4" class="ml-4">
        <b-row>
          <b-card class="w-100">
            <b-list-group flush class="w-100">
              <b-list-group-item variant="primary">중개매물</b-list-group-item>
              <b-list-group-item variant="dark">
                매물번호 {{ this.$route.params.articleno }}
              </b-list-group-item>
            </b-list-group>
            <b-card-body>
              <b-card-title>
                <b-row class="text-left">
                  <b-col cols="4">
                    <b-button variant="outline-primary">
                      <template v-if="this.trade.contractOpt == 0"
                        >매매
                      </template>
                      <template v-else-if="this.trade.contractOpt == 1"
                        >전세
                      </template>
                      <template v-else> 월세 </template>
                    </b-button>
                  </b-col>
                  <b-col>
                    <h1>{{ trade.deposit }} / {{ trade.monthlyFee }}</h1>
                  </b-col>
                </b-row>
              </b-card-title>
              <b-card-sub-title class="mb-2 text-left">
                {{ computedRoadnameAddress }}
              </b-card-sub-title>
            </b-card-body>
          </b-card>
        </b-row>
        <b-row>
          <b-card class="w-100">
            <b-row align-v="center">
              <b-col cols="3">
                <b-avatar
                  variant="primary"
                  :text="this.article.id[0]"
                  size="3.5rem"
                ></b-avatar>
              </b-col>
              <b-col class="text-left">
                중개업자 &nbsp;&nbsp; {{ this.article.id }}</b-col
              >
            </b-row>
          </b-card>
        </b-row>
        <b-row>
          <b-card class="w-100">
            <b-button variant="outline-primary" @click="createRoom">
              채팅상담 요청
            </b-button>
          </b-card>
        </b-row>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
// import moment from "moment";
import http from "@/api/http.js";
import { BASE_URL } from "@/api/index.js";
import { mapGetters } from "vuex";
export default {
  name: "BoardDetail",
  data() {
    return {
      download_url: "",
      article: { time: "" },
      trade: { date: "" },
      files: {},
      slide: 0,
      sliding: null,

      map: "",
      marker: "",
      mapContainer: "",
      mapOption: "",
      geocoder: "",
    };
  },
  computed: {
    ...mapGetters(["loginUser"]),
    computedFileList() {
      if (this.article.fileList == undefined) return {};
      return this.article.fileList;
    },
    computedRoadnameAddress() {
      if (this.trade.roadnameAddress == undefined) return "";
      return this.trade.roadnameAddress;
    },
    computedDetailAddress() {
      if (this.trade.detailAddress == undefined) return "";
      return this.trade.detailAddress.split("\r\n").join("<br />");
    },
    computedDetail() {
      if (this.trade.detail == undefined) return "";
      return this.trade.detail;
    },
    computedCommonItemLength() {
      if (this.trade.commonMaintainItem == undefined) return "";
      return this.trade.commonMaintainItem.length;
    },
    computedEachFeeItemLength() {
      if (this.trade.eachFeeItem == undefined) return "";
      return this.trade.eachFeeItem.length;
    },
    computedContract() {
      if (this.trade.contractOpt == undefined) return "";
      return this.trade.contractOpt == 0
        ? "매매"
        : this.trade.contractOpt == 1
        ? "전세"
        : "월세";
    },

    computedLoan() {
      if (this.trade.loan == undefined) return 0;
      return this.trade.loan == 0 ? "융자금 없음" : "융자금 있음";
    },
  },
  created() {
    this.download_url = BASE_URL();

    //게시글 정보
    http.get(`/board/${this.$route.params.articleno}`).then(({ data }) => {
      this.article = data;
      this.article.time = this.article.time.split(" ")[0].split("-").join(".");
      console.log("this.article :", data);
    });
  },
  methods: {
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
    },
    listArticle() {
      this.$router.push({ name: "boardList" });
    },
    moveModifyArticle() {
      this.$router.replace({
        name: "boardModify",
        params: { articleno: this.article.no },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    deleteArticle() {
      if (confirm("글을 삭제합니다.")) {
        http
          .delete(`/board/${this.$route.params.articleno}`)
          .then()
          .catch((err) => {
            console.log(err);
          });

        this.$router.push({ name: "boardDelete" });
      }
    },

    async loadTrade() {
      //매매 정보를 포함하고 있다면
      await http
        .get(`/board/trade/${this.$route.params.articleno}`)
        .then(({ data }) => {
          this.trade = data;
          this.trade.date = this.trade.date.split(" ")[0].split("-").join(".");
          console.log("this.trade : ", data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    async setMapLocation() {
      let geocoder = this.geocoder;
      // let mapContainer = this.mapContainer;
      let map = this.map;
      let marker = this.marker;
      // 주소로 상세 정보를 검색
      await geocoder.addressSearch(
        this.trade.roadnameAddress,
        function (results, status) {
          // 정상적으로 검색이 완료됐으면
          if (status === window.daum.maps.services.Status.OK) {
            var result = results[0]; //첫번째 결과의 값을 활용

            // 해당 주소에 대한 좌표를 받아서
            var coords = new window.daum.maps.LatLng(result.y, result.x);
            // 지도를 보여준다.
            // mapContainer.style.display = "block";
            map.relayout();
            // 지도 중심을 변경한다.
            map.setCenter(coords);
            // 마커를 결과값으로 받은 위치로 옮긴다.
            marker.setPosition(coords);
          }
        }
      );
    },
    setMapMounted() {
      // --------------------------------지도 설정 ------------------------------------------
      this.mapContainer = document.getElementById("map");
      this.mapContainer.style.display = "block";
      var mapOption = {
        center: new window.daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
        level: 5, // 지도의 확대 레벨
      };
      this.map = new window.daum.maps.Map(this.mapContainer, mapOption);
      //주소-좌표 변환 객체를 생성
      this.geocoder = new window.daum.maps.services.Geocoder();
      //마커를 미리 생성
      this.marker = new window.daum.maps.Marker({
        position: new window.daum.maps.LatLng(37.537187, 127.005476),
        map: this.map,
      });
    },
    createRoom() {
      let request = { user_id: this.loginUser.id, seller_id: this.article.id };
      http
        .post("/chat", JSON.stringify(request))
        .then(({ data }) => {
          console.log(data);
          if (data == "success") {
            console.log("중개업자와 채팅 연결: ", data);
            alert("상담 요청을 전송했습니다.\n채팅방으로 이동합니다.");
          } else {
            alert("채팅방으로 이동합니다.");
          }
          this.$router.push({ name: "chat" });
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  async mounted() {
    await this.loadTrade();
    await this.setMapMounted();
    await this.setMapLocation();
  },

  // --------------------------------지도 설정 끝 ------------------------------------------},
};
</script>

<style>
.carousel-inner {
  width: 100%;
  max-height: 400px !important;
}
</style>
