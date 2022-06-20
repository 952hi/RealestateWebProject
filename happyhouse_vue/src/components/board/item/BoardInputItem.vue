<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset" enctype="multipart/form-data">
        <b-form-group
          id="userid-group"
          label="작성자:"
          label-for="userid"
          description="작성자를 입력하세요."
        >
          <b-form-input
            id="userid"
            :disabled="isUserid"
            v-model="article.id"
            type="text"
            required
            placeholder="작성자 입력..."
          >
          </b-form-input>
        </b-form-group>

        <b-form-group
          id="subject-group"
          label="제목:"
          label-for="subject"
          description="제목을 입력하세요."
        >
          <b-form-input
            id="subject"
            v-model="article.title"
            type="text"
            required
            placeholder="제목 입력..."
          >
          </b-form-input>
        </b-form-group>

        <b-form-group id="content-group" label="내용:" label-for="content">
          <b-form-textarea
            id="content"
            v-model="article.content"
            placeholder="내용 입력..."
            rows="10"
            max-rows="15"
          >
          </b-form-textarea>
        </b-form-group>
        <b-form-file
          browse-text="업로드"
          multiple
          v-model="files"
          :state="Boolean(files)"
          accept=".jpg, .png, .gif"
          placeholder="파일을 선택하거나 여기로 드래그하세요."
          drop-placeholder="Drop file here..."
        >
        </b-form-file>
        <b-button
          type="submit"
          variant="primary"
          class="m-1"
          v-if="this.type === 'register'"
        >
          글작성
        </b-button>
        <b-button type="submit" variant="primary" class="m-1" v-else
          >글수정</b-button
        >
        <b-button type="reset" variant="danger" class="m-1">취소</b-button>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
import http from "@/api/http";
import axios from "axios";
export default {
  name: "BoardInputItem",
  components: {},
  data() {
    return {
      files: [],
      article: {
        no: 0,
        id: "",
        title: "",
        content: "",
      },
      isUserid: false,
    };
  },
  props: {
    type: { type: String },
  },
  created() {
    if (this.type === "modify") {
      http.get(`/board/${this.$route.params.articleno}`).then(({ data }) => {
        // this.article.articleno = data.article.articleno;
        // this.article.userid = data.article.userid;
        // this.article.subject = data.article.subject;
        // this.article.content = data.article.content;
        this.article = data;
      });
      this.isUserid = true;
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();
      let err = true;
      let msg = "";
      !this.article.id &&
        ((msg = "작성자 입력해주세요"), (err = false), this.$refs.id.focus());
      err &&
        !this.article.title &&
        ((msg = "제목 입력해주세요"), (err = false), this.$refs.title.focus());
      err &&
        !this.article.content &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      console.log(this.article);
      if (!err) alert(msg);
      else
        this.type === "register" ? this.registArticle() : this.modifyArticle();
    },
    onReset(event) {
      event.preventDefault();
      this.article.no = 0;
      this.article.title = "";
      this.article.content = "";
      this.$router.push({ name: "boardList" });
    },

    registArticle() {
      const Data = new FormData();
      Data.append("id", this.article.id);
      Data.append("title", this.article.title);
      Data.append("content", this.article.content);
      for (let i = 0; i < this.files.length; i++) {
        Data.append("imgs", this.files[i]);
      }
      axios
        .post(`/board`, Data, {
          baseURL: "http://localhost/vue",
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then(({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        });
    },
    modifyArticle() {
      http
        .put(`/board/${this.article.no}`, {
          no: this.$route.params.articleno,
          id: this.article.id,
          title: this.article.title,
          content: this.article.content,
        })
        .then(({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          // 현재 route를 /list로 변경.
          this.$router.push({ name: "boardList" });
        });
    },
    moveList() {
      this.$router.push({ name: "boardList" });
    },
  },
};
</script>
<style src="@vueform/slider/themes/default.css"></style>
<style></style>
