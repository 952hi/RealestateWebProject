import axios from "axios";

// axios 객체 생성
export default axios.create({
  baseURL: "http://localhost/vue",
  headers: {
    "Content-type": "application/json",
    "Context-Type": "multipart/form-data",
  },
});
