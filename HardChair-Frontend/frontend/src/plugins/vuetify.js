import Vue from "vue";
import Vuetify from "vuetify";
import "vuetify/dist/vuetify.min.css";

const opts = {
  theme: {
    themes: {
      light: {
        primary: "#4E342E",
        secondary: "#b0bec5",
        anchor: "#272727"
      }
    }
  }
};
Vue.use(Vuetify);
export default new Vuetify(opts);
