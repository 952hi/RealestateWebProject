/* global kakao */
import subway from "@/assets/subway.png";
import house from "@/assets/house.png";
import coffee from "@/assets/starbucks.png";
import ssafy from "@/assets/ssafy.png";
var map;
var markers = [];
var subways = [];
var starbucks = []; // eslint-disable-line no-unused-vars
var mapContainer;
var mapOption;
var starbuckmarker = [];
var subwaysmarker = [];
var infowindows = [];
var geocoder = new kakao.maps.services.Geocoder();
function deleMaker() {
  // infowindow.close();
  if (infowindows.length > 0) {
    infowindows.forEach((item) => {
      item.close();
    });
    infowindows = [];
  }
  if (markers.length > 0) {
    markers.forEach((item) => {
      item.setMap(null);
    });
    markers = [];
  }
}
function getMarker(items) {
  if (markers.length > 0) {
    markers.forEach((item) => {
      item.setMap(null);
    });
    markers = [];
  }
  var bounds = new kakao.maps.LatLngBounds();
  var imageSrc = house;
  for (var i = 0; i < items.length; i++) {
    var imageSize = new kakao.maps.Size(35, 35);
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
    var marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: new kakao.maps.LatLng(items[i].lat, items[i].lng), // 마커를 표시할 위치
      title: items[i].aptName, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
      image: markerImage, // 마커 이미지
    });
    markers[i] = marker;
    bounds.extend(new kakao.maps.LatLng(items[i].lat, items[i].lng));
  }
  if (markers.length > 0) {
    markers.forEach((item) => {
      item.setMap(map);
    });
  }
  map.setBounds(bounds);
  // return markers;
}
function first() {
  mapContainer = document.getElementById("map");
  // 지도를 표시할 div
  mapOption = {
    center: new kakao.maps.LatLng(37.5012743, 127.039585), // 지도의 중심좌표
    level: 4, // 지도의 확대 레벨
  };
  // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
  map = new kakao.maps.Map(mapContainer, mapOption);
  createCoffeeMarkers(); // 커피숍 마커를 생성하고 커피숍 마커 배열에 추가합니다
  createSubwayMarkers(); // 편의점 마커를 생성하고 편의점 마커 배열에 추가합니다
  setStoreMarkers(null);
  setCoffeeMarkers(null);
  var imageSrc = ssafy;
  var imageSize = new kakao.maps.Size(150, 100);
  var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
  var marker = new kakao.maps.Marker({
    map: map, // 마커를 표시할 지도
    position: new kakao.maps.LatLng(37.5012743, 127.039585), // 마커를 표시할 위치
    image: markerImage, // 마커 이미지
  });
  marker.setMap(map);
}
function subwayStore(item) {
  subways = item;
}

function starbucksStore(item) {
  starbucks = item;
  first();
}
function createCoffeeMarkers() {
  var imageSrc = coffee;
  for (var i = 0; i < starbucks.length; i++) {
    var imageSize = new kakao.maps.Size(35, 35);
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
    var marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: new kakao.maps.LatLng(starbucks[i].lng, starbucks[i].let), // 마커를 표시할 위치
      image: markerImage, // 마커 이미지
    });

    // 생성된 마커를 커피숍 마커 배열에 추가합니다
    starbuckmarker.push(marker);
  }
}
function createSubwayMarkers() {
  var imageSrc = subway;
  for (var i = 0; i < subways.length; i++) {
    var imageSize = new kakao.maps.Size(35, 35);
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
    var marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: new kakao.maps.LatLng(subways[i].lat, subways[i].lng), // 마커를 표시할 위치
      image: markerImage, // 마커 이미지
    });

    // 생성된 마커를 커피숍 마커 배열에 추가합니다
    subwaysmarker.push(marker);
  }
}
function setCoffeeMarkers(data) {
  for (var i = 0; i < starbuckmarker.length; i++) {
    starbuckmarker[i].setMap(data);
  }
}
function setStoreMarkers(data) {
  for (var i = 0; i < subwaysmarker.length; i++) {
    subwaysmarker[i].setMap(data);
  }
}

function changeMarker(type) {
  var coffeeMenu = document.getElementById("coffeeMenu");
  var subwayMenu = document.getElementById("subwayMenu");
  if (type === "coffee") {
    coffeeMenu.className = "menu_selected";
    setCoffeeMarkers(map);
  } else if (type === "subway") {
    subwayMenu.className = "menu_selected";
    setStoreMarkers(map);
  } else if (type === "all") {
    subwayMenu.className = "menu_selected";
    coffeeMenu.className = "menu_selected";
    setCoffeeMarkers(map);
    setStoreMarkers(map);
  }
}
function geo(cb) {
  if (navigator.geolocation) {
    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
    navigator.geolocation.getCurrentPosition(function (position) {
      var lat = position.coords.latitude, // 위도
        lon = position.coords.longitude; // 경도
      let coord = new kakao.maps.LatLng(lat, lon);
      geocoder.coord2RegionCode(
        coord.getLng(),
        coord.getLat(),
        function (result, status) {
          if (status === kakao.maps.services.Status.OK) {
            cb(result[0].address_name);
          }
        }
      );
    });
  }
}
function atoll(data) {
  var bounds = new kakao.maps.LatLngBounds();
  var imageSrc = house;
  data.forEach((item) => {
    geocoder.addressSearch(item.roadnameAddress, function (result, status) {
      // 정상적으로 검색이 완료됐으면
      if (status === kakao.maps.services.Status.OK) {
        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
        var imageSize = new kakao.maps.Size(35, 35);
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
        // 결과값으로 받은 위치를 마커로 표시합니다
        var marker = new kakao.maps.Marker({
          map: map,
          position: coords,
          image: markerImage,
        });
        markers.push(marker);
        // 인포윈도우로 장소에 대한 설명을 표시합니다
        var infowindow = new kakao.maps.InfoWindow({
          content: `<div style="width:150px;text-align:center;padding:6px 0;">${item.title}</div>`,
        });
        infowindows.push(infowindow);
        infowindow.open(map, marker);
        bounds.extend(coords);
        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        map.setBounds(bounds);
      }
    });
  });
}
export {
  getMarker,
  starbucksStore,
  subwayStore,
  changeMarker,
  setCoffeeMarkers,
  setStoreMarkers,
  geo,
  deleMaker,
  atoll,
};
