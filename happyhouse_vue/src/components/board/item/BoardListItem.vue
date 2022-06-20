<template>
  <b-tr>
    <b-td>{{ no }}</b-td>
    <b-th class="text-left">
      <router-link :to="{ name: 'detail', params: { articleno: no } }">{{
        title
      }}</router-link>
    </b-th>
    <b-td>{{ hit }}</b-td>
    <b-td>{{ id }}</b-td>
    <b-td>{{ time | dateFormat }}</b-td>
  </b-tr>
</template>

<script>
import moment from "moment";

export default {
  name: "BoardListItem",
  props: {
    no: Number,
    id: String,
    title: String,
    hit: Number,
    time: String,
  },
  filters: {
    dateFormat(time) {
      return moment(new Date(time)).format("YY.MM.DD");
    },
  },
};
</script>

<style></style>
