<template>
  <b-container class="bv-example-row mt-3">
    <b-col>
      <b-row>
        <b-col cols="1" />
        <b-col cols="10">
          <b-row>
            <b-col>
              <b-alert show>
                <h4>
                  아래 작성한 형태로 사용자에게 보이게 됩니다.
                  <a href="#"><strong>작성방법 참고</strong></a>
                </h4>
              </b-alert>
            </b-col>
          </b-row>
          <b-row class="mb-2">
            <b-col class="text-left">
              <strong>*항목은 필수로 입력해야합니다.</strong>
            </b-col>
          </b-row>
          <b-row>
            <b-form-file
              button-variant="outline-primary"
              browse-text="찾기.."
              multiple
              accept=".jpg, .png, .gif"
              v-model="files"
              :state="Boolean(files)"
              placeholder="드래그 혹은 클릭으로 실제 매물 사진을 업로드해주세요."
              drop-placeholder="여기에 드래그해주세요."
            >
            </b-form-file>
          </b-row>
          <b-row>
            <b-form-textarea
              id="textarea"
              v-model="title"
              placeholder="* 제목을 작성해주세요."
              rows="1"
              max-rows="1"
              size="sm"
              no-resize
            ></b-form-textarea>
          </b-row>
          <b-row class="mb-5">
            <b-card
              text-variant="secondary"
              style="width: 100%; font-size: 12px"
            >
              <b-row align-h="between">
                <b-col cols="3">최초 등록일</b-col>
                <b-col cols="3">{{ crntDate() }}</b-col>
              </b-row>
            </b-card>
          </b-row>
          <b-row>
            <b-col class="text-left">
              <!-- 왼쪽으로 붙이기 -->
              <h5>
                <strong>거래정보</strong>
                <hr style="border: 1.5px solid black" />
              </h5>
            </b-col>
          </b-row>
          <b-row style="font-size: 15px" align-v="center">
            <b-col>
              <b-row align-h="between">
                <b-col cols="4"><strong>* 계약형태</strong></b-col>
                <b-col>
                  <b-form-radio-group
                    required
                    button-variant="outline-primary"
                    id="btn-radios-1"
                    v-model="selectedContract"
                    :options="options1"
                    name="radios-btn-default"
                    buttons
                  ></b-form-radio-group>
                </b-col>
              </b-row>
            </b-col>
            <b-col>
              <b-row align-h="between" align-v="center">
                <b-col><strong>* 가격정보</strong></b-col>
                <b-col cols="9">
                  <template
                    v-if="selectedContract == 2 || selectedContract == 1"
                  >
                    <b-row class="pb-5" align-v="center">
                      <b-col cols="4">보증금</b-col>
                      <b-col cols="5">
                        <b-form-input
                          :id="`type-number`"
                          type="number"
                          v-model="deposit"
                        ></b-form-input>
                      </b-col>
                      <b-col>만원</b-col>
                    </b-row>
                    <b-row align-v="center">
                      <b-col cols="4">월세</b-col>
                      <b-col cols="5">
                        <b-form-input
                          :id="`type-number`"
                          type="number"
                          v-model="monthlyFee"
                        ></b-form-input>
                      </b-col>
                      <b-col>만원</b-col>
                    </b-row>
                  </template>
                  <template v-else>
                    <b-row align-v="center">
                      <b-col cols="4">매매가</b-col>
                      <b-col cols="5">
                        <b-form-input
                          :id="`type-number`"
                          type="number"
                          v-model="deposit"
                        ></b-form-input>
                      </b-col>
                      <b-col>만원</b-col>
                    </b-row>
                  </template>
                </b-col>
              </b-row>
            </b-col>
          </b-row>

          <hr class="m-4" />

          <b-row style="font-size: 15px">
            <b-col>
              <b-row align-v="center">
                <b-col cols="4"><strong>* 공용 관리비</strong></b-col>
                <b-col cols="5">
                  <b-form-input
                    :id="`type-number`"
                    type="number"
                    v-model="commonMaintainFee"
                  ></b-form-input>
                </b-col>
                <b-col>만원</b-col>
              </b-row>
            </b-col>
            <b-col>
              <b-row align-v="center">
                <b-col cols="4"><strong>공용 관리비 항목</strong></b-col>
                <b-col>
                  <b-form-tags
                    input-id="tags-remove-on-delete"
                    :input-attrs="{
                      'aria-describedby': 'tags-remove-on-delete-help',
                    }"
                    v-model="maintainTag"
                    separator=" "
                    placeholder="각각 입력 후 엔터"
                    remove-on-delete
                  ></b-form-tags>
                </b-col>
              </b-row>
            </b-col>
          </b-row>
          <hr class="m-4" />
          <b-row style="font-size: 15px">
            <b-col>
              <b-row align-v="center">
                <b-col cols="4"><strong>* 융자여부</strong></b-col>
                <b-col>
                  <b-form-radio-group
                    button-variant="outline-primary"
                    id="btn-radios-1"
                    v-model="selectedLoan"
                    :options="options2"
                    name="radios-btn-default"
                    buttons
                  ></b-form-radio-group>
                </b-col>
              </b-row>
            </b-col>
            <b-col>
              <b-row align-v="center">
                <b-col cols="4"><strong>개별 사용료 항목</strong></b-col>
                <b-col>
                  <b-form-tags
                    input-id="tags-remove-on-delete"
                    :input-attrs="{
                      'aria-describedby': 'tags-remove-on-delete-help',
                    }"
                    v-model="eachFeeTag"
                    separator=" "
                    placeholder="각각 입력 후 엔터"
                    remove-on-delete
                  ></b-form-tags>
                </b-col>
              </b-row>
            </b-col>
          </b-row>
          <hr class="m-4" />
          <b-row style="font-size: 15px" align-v="center">
            <b-col>
              <b-row align-v="center">
                <b-col cols="4"><strong>* 입주가능일</strong></b-col>
                <b-col>
                  <b-button @click="liveNow" variant="primary">
                    즉시입주
                  </b-button>
                </b-col>
                <b-col>
                  <b-button v-b-toggle.collapse-1 variant="primary">
                    달력선택
                  </b-button>
                </b-col>
              </b-row>
            </b-col>
            <b-col>
              <b-row>
                <b-col cols="1" />
                <b-col class="text-left">
                  <strong>선택날짜 : </strong> {{ calendarValue | filterDate }}
                </b-col>
              </b-row>
            </b-col>
          </b-row>
          <b-row>
            <b-col cols="7">
              <b-collapse id="collapse-1" class="mt-2">
                <div
                  :style="{
                    position: choose,
                    left: leftPx,
                    top: topPx,
                  }"
                >
                  <b-calendar
                    v-model="calendarValue"
                    @context="onContext"
                    locale="ko-KR"
                    label-help="예정입주 가능일을 선택해주세요."
                    label-no-date-selected=""
                  ></b-calendar>
                </div>
              </b-collapse>
            </b-col>
          </b-row>
          <hr class="m-4" />
          <b-row class="pt-4">
            <b-col class="text-left">
              <!-- 왼쪽으로 붙이기 -->
              <h5>
                <strong>상세설명</strong>
                <hr style="border: 1.5px solid black" />
              </h5>
            </b-col>
          </b-row>
          <b-row>
            <b-form-textarea
              id="textarea"
              v-model="detailText"
              placeholder="매물에 대해 상세한 설명을 작성할 수 있습니다."
              rows="3"
              max-rows="6"
              wrap="hard"
              cols="20"
            ></b-form-textarea>
          </b-row>
          <b-row class="pt-5">
            <b-col class="text-left">
              <!-- 왼쪽으로 붙이기 -->
              <h5>
                <strong>* 위치</strong>
                <hr style="border: 1.5px solid black" />
              </h5>
            </b-col>
          </b-row>
          <b-row>
            <b-button
              @click="sample5_execDaumPostcode(cb)"
              value="주소로 검색"
              variant="primary"
            >
              매물주소 검색
            </b-button>
          </b-row>
          <b-row>
            <div id="map" style="width: 100%; height: 300px" class="my-4"></div>
          </b-row>
          <b-row>
            <b-form-input
              type="text"
              id="sample5_address"
              disabled
              placeholder="* 도로명주소 입력"
              v-model="address"
              trim
            />
          </b-row>
          <b-row>
            <b-form-input
              type="text"
              placeholder="* 상세주소 입력"
              v-model="extraAddress"
              trim
            />
          </b-row>
          <b-row>
            <b-button class="w-100 my-3" variant="primary" @click="sendData">
              매물등록
            </b-button>
          </b-row>
        </b-col>
      </b-row>
    </b-col>
  </b-container>
</template>

<script>
import { mapGetters } from "vuex";
import axios from "axios";
export default {
  name: "BoardWrite",
  components: {},
  filters: {
    filterDate(date) {
      let divide = date.split("-");
      if (divide.length == 1) return date;
      else {
        return divide[0] + "년 " + Number(divide[1]) + "월 " + divide[2] + "일";
      }
    },
  },
  data() {
    return {
      options1: [
        { text: "매매", value: 0 },
        { text: "전세", value: 1 },
        { text: "월세", value: 2 },
      ],
      options2: [
        { text: "융자없음", value: 0 },
        { text: "융자있음", value: 1 },
      ],
      options3: [
        { text: "중개", value: 1 },
        { text: "직거래", value: 0 },
      ],

      title: "",
      files: [],

      selectedContract: 0,
      selectedLoan: [],
      selectedTrade: [],

      maintainTag: [],
      eachFeeTag: [],
      calendar: false,
      choose: "relative",
      leftPx: "25px",
      topPx: "0px",

      deposit: "",
      monthlyFee: "",
      commonMaintainFee: "",

      calendarValue: "미선택",
      context: null,

      detailText: "",

      postcode: "",
      address: "",
      extraAddress: "",

      map: "",
      marker: "",
      mapContainer: "",
      mapOption: "",
      geocoder: "",
    };
  },
  methods: {
    onContext(ctx) {
      this.context = ctx;
    },
    cb(addr) {
      this.address = addr;
    },
    sample5_execDaumPostcode(cb) {
      let geocoder = this.geocoder;
      // let mapContainer = this.mapContainer;
      let map = this.map;
      let marker = this.marker;

      new window.daum.Postcode({
        oncomplete(data) {
          var addr = data.address; // 최종 주소 변수

          // 주소 정보를 해당 필드에 넣는다.
          document.getElementById("sample5_address").value = addr;

          cb(addr);
          // 주소로 상세 정보를 검색
          geocoder.addressSearch(data.address, function (results, status) {
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
          });
        },
      }).open();
    },
    crntDate() {
      let crntYYYYMMDD = new Date(Date.now());
      return (
        crntYYYYMMDD.getFullYear() +
        "년 " +
        (crntYYYYMMDD.getMonth() + 1) +
        "월 " +
        crntYYYYMMDD.getDate() +
        "일"
      );
    },
    liveNow() {
      this.calendarValue = "즉시입주";
    },
    async sendData() {
      if (this.loginUser.id == undefined) {
        alert("로그인된 사용자만 이용할 수 있습니다.");
        return;
      } else if (this.loginUser.grade == 1) {
        alert("전문회원만 이용할 수 있습니다.");
        return;
      } else if (this.title == "") {
        alert("제목을 입력해주세요.");
        return;
      } else if (this.selectedContract.length == 0) {
        alert("계약형태를 선택해주세요.");
        return;
      } else if (this.deposit < 0) {
        alert("보증금을 제대로 입력해주세요.");
        return;
      } else if (this.monthlyFee < 0) {
        alert("월세를 제대로 입력해주세요.");
        return;
      } else if (this.commonMaintainFee < 0) {
        alert("공용 관리비를 제대로 입력해주세요.");
        return;
      } else if (this.selectedLoan.length == 0) {
        alert("융자여부를 선택해주세요.");
        return;
      } else if (this.calendarValue == "미선택") {
        alert("날짜를 선택해주세요.");
        return;
      } else if (this.address.trim() == "") {
        alert("주소를 입력해주세요.");
        return;
      } else if (this.extraAddress.trim() == "") {
        alert("상세 주소를 입력해주세요.");
        return;
      }
      let Data = new FormData();
      await axios
        .post(
          "board/insertThread",
          {
            id: this.loginUser.id,
            title: this.title,
            contractOpt: Number(this.selectedContract),
            deposit: this.deposit == "" ? Number(0) : Number(this.deposit),
            monthlyFee:
              this.monthlyFee == "" ? Number(0) : Number(this.monthlyFee),
            commonMaintainFee:
              this.commonMaintainFee == ""
                ? Number(0)
                : Number(this.commonMaintainFee),
            loan: this.selectedLoan,
            date: this.calendarValue,
            detail: this.detailText,
            roadnameAddress: this.address,
            detailAddress: this.extraAddress,
            commonMaintainItem: this.maintainTag,
            eachFeeItem: this.eachFeeTag,
          },
          {
            baseURL: "http://localhost/vue",
            headers: {
              "Content-type": "application/json",
            },
          }
        )
        .then((res) => {
          console.log(res);
          Data = new FormData();
          for (let i = 0; i < this.files.length; i++) {
            Data.append("imgs", this.files[i]);
          }

          if (this.files.length > 0) {
            axios
              .post("board/insertImage", Data, {
                baseURL: "http://localhost/vue",
                headers: {
                  "Content-Type": "multipart/form-data",
                },
              })
              .then((res) => {
                console.log(res);
              })
              .catch((err) => {
                console.log(err);
              });
          }
        })
        .catch((err) => {
          console.log(err);
        });

      //등록할 이미지가 존재할 때

      this.$router.push({ name: "board" });
    },
  },
  mounted() {
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
      position: "",
      map: this.map,
    });

    // --------------------------------지도 설정 끝 ------------------------------------------
  },
  computed: {
    ...mapGetters(["loginUser"]),
  },
};
</script>

<style></style>
