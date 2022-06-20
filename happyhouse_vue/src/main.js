import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
var EventBus = new Vue();
export default EventBus;
import { BootstrapVue, IconsPlugin, VBHover } from "bootstrap-vue";
import VueCompositionAPI from "@vue/composition-api";
// Import Bootstrap and BootstrapVue CSS files (order is important)
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue);
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin);
Vue.use(VBHover);
Vue.use(VueCompositionAPI);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
