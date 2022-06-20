<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show
          ><h3>회원가입에 성공했습니다.</h3></b-alert
        >
      </b-col>
    </b-row>
    <b-row
      ><b-col
        ><router-link :to="{ name: 'home' }"
          ><b-button>시작 화면으로 이동</b-button></router-link
        ></b-col
      ></b-row
    >
  </b-container>
</template>

<script>
export default {};
</script>

<style></style>
