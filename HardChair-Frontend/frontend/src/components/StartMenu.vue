<template>
  <v-card class="overflow-hidden">
    <v-app-bar
      fixed
      flat
      dark
      height="70"
      scroll-target="#scrolling-techniques"
      style="border-radius:0px;background:transparent"
      id="navbar"
    >
      <v-toolbar-title style="font-family:Georgia" class="ml-8"
        >StarChair</v-toolbar-title
      >
      <v-spacer></v-spacer>
      <template>
        <div class="text-center mr-4" data-app="true">
          <v-menu :open-on-hover="openOnHover" offset-y>
            <template v-slot:activator="{ on }">
              <v-btn text v-on="on"
                ><v-icon class="mr-2">mdi-lightbulb-on-outline</v-icon
                >Conference<v-icon x-small class="ml-1"
                  >mdi-chevron-down</v-icon
                ></v-btn
              >
            </template>
            <v-list>
              <v-list-item to="allMeetings" v-if="user != 'admin'">
                <v-list-item-title class="text-left"
                  >All Conferences</v-list-item-title
                >
              </v-list-item>
              <v-list-item to="UserConf" v-if="user != 'admin'">
                <v-list-item-title class="text-left"
                  >Attended Conference</v-list-item-title
                >
              </v-list-item>
              <v-list-item to="reviewMeeting" v-if="user == 'admin'">
                <v-list-item-title class="text-left"
                  >Audit Conference</v-list-item-title
                >
              </v-list-item>
            </v-list>
          </v-menu>
        </div>
      </template>
      <template v-if="user != 'admin'">
        <div class="text-center mr-4" data-app="true">
          <v-menu :open-on-hover="openOnHover" offset-y>
            <template v-slot:activator="{ on }">
              <v-btn text v-on="on"
                >Application<v-icon x-small class="ml-1"
                  >mdi-chevron-down</v-icon
                ></v-btn
              >
            </template>
            <v-list>
              <v-list-item to="myApplication">
                <v-list-item-title class="text-left"
                  >My Application</v-list-item-title
                >
              </v-list-item>
              <v-list-item to="conferenceApplication">
                <v-list-item-title class="text-left"
                  >New Application</v-list-item-title
                >
              </v-list-item>
            </v-list>
          </v-menu>
        </div>
      </template>
      <v-btn text to="Homepage">
        Home
      </v-btn>
      <v-btn icon to="userCenter" v-if="user != 'admin'">
        <v-icon>mdi-bell-outline</v-icon>
      </v-btn>
      <v-btn icon class="mr-1">
        <v-icon>mdi-magnify</v-icon>
      </v-btn>
      <template>
        <div class="text-center" data-app="true">
          <v-menu :open-on-hover="openOnHover" offset-y>
            <template v-slot:activator="{ on }">
              <v-btn text v-on="on"
                >{{ user
                }}<v-icon x-small class="ml-1">mdi-chevron-down</v-icon></v-btn
              >
            </template>
            <v-list>
              <v-list-item @click="showLogOutDialog">
                <v-list-item-title>Log out</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </div>
      </template>
    </v-app-bar>
    <v-sheet id="scrolling-techniques" class="overflow-y-auto" fluid>
      <v-container fluid style="padding:0px;">
        <v-dialog v-model="logOutDialog" max-width="400">
          <v-card>
            <v-card-title style="font-size:18px;font-family:Georgia;"
              >Are you sure to logout?</v-card-title
            >
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn text @click="logOutDialog = false" style="color:red;">
                Disagree
              </v-btn>
              <v-btn text @click="logout">
                Agree
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-content>
          <section>
            <v-parallax height="650" src="../../static/homepage1.jpg">
              <v-layout column align-center justify-center class="white--text">
                <h1
                  class="white--text mb-2 text-center"
                  style="font-family:Georgia !important;font-size:85px;"
                >
                  StarChair
                </h1>
                <div class="subheading mt-6 text-center">
                  A Platform Created for Scientists.<br />
                  Start your conference or join any conference you are
                  interested.<br />
                  Contribute your paper here, we have the best team.
                </div>
              </v-layout>
            </v-parallax>
          </section>
        </v-content>
      </v-container>
    </v-sheet>
  </v-card>
</template>

<script>
export default {
  name: "startMenu",
  data() {
    return {
      token: "",
      user: "",
      openOnHover: true,
      logOutDialog: false
    };
  },
  created() {
    this.handleUserData();
    this.handleToken();
  },
  methods: {
    handleToken() {
      this.token = localStorage.getItem("token");
    },
    handleUserData() {
      if (JSON.parse(localStorage.getItem("userDetails"))) {
        this.user = JSON.parse(localStorage.getItem("userDetails")).username;
      }
    },
    showLogOutDialog() {
      this.logOutDialog = true;
    },
    logout() {
      var self = this;
      const info = {
        userDetails: localStorage.getItem("userDetails"),
        token: localStorage.getItem("token")
      };
      self.$store.commit("logout", info);
      this.$router.replace("/");
      this.$router.go(0);
      self.$message({
        message: "Successful logout",
        type: "success"
      });
    },
    handleScroll() {
      var scrollTop =
        window.pageYOffset ||
        document.documentElement.scrollTop ||
        document.body.scrollTop;
      if (scrollTop >= 50) {
        document.querySelector("#navbar").style.background = "#272727";
      } else {
        document.querySelector("#navbar").style.background = "transparent";
      }
    },
  },
  mounted() {
    //给window添加一个滚动滚动监听事件
    window.addEventListener("scroll", this.handleScroll);
  },
  destroyed() {
    //离开该页面需要移除这个监听的事件
    window.removeEventListener("scroll", this.handleScroll);
  }
};
</script>

<style>
::-webkit-scrollbar {
  width: 0px;
}
</style>
