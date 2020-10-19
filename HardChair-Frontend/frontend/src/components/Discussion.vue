<template>
  <v-card class="overflow-hidden" style="width:100%;height:100%;background-color:#ECEFF1;">
    <v-app-bar flat dark height="60" style="border-radius:0px;" color="#263238">
      <v-toolbar-title style="font-family:Georgia" class="ml-8">StarChair</v-toolbar-title>
      <v-spacer></v-spacer>
      <template>
        <div class="text-center mr-4" data-app="true">
          <v-menu :open-on-hover="openOnHover" offset-y>
            <template v-slot:activator="{ on }">
              <v-btn text v-on="on">
                <v-icon class="mr-2">mdi-lightbulb-on-outline</v-icon>Conference
                <v-icon x-small class="ml-1">mdi-chevron-down</v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item to="allMeetings">
                <v-list-item-title class="text-left">All Conferences</v-list-item-title>
              </v-list-item>
              <v-list-item to="UserConf">
                <v-list-item-title class="text-left">Attended Conference</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </div>
      </template>
      <template>
        <div class="text-center mr-4" data-app="true">
          <v-menu :open-on-hover="openOnHover" offset-y>
            <template v-slot:activator="{ on }">
              <v-btn text v-on="on">
                Application
                <v-icon x-small class="ml-1">mdi-chevron-down</v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item to="myApplication">
                <v-list-item-title class="text-left">My Application</v-list-item-title>
              </v-list-item>
              <v-list-item to="conferenceApplication">
                <v-list-item-title class="text-left">New Application</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </div>
      </template>
      <v-btn text to="startMenu">Menu</v-btn>
      <v-btn text to="Homepage">Home</v-btn>
      <v-btn icon to="userCenter">
        <v-icon>mdi-bell-outline</v-icon>
      </v-btn>
      <v-btn icon class="mr-1">
        <v-icon>mdi-magnify</v-icon>
      </v-btn>
      <template>
        <div class="text-center" data-app="true">
          <v-menu :open-on-hover="openOnHover" offset-y>
            <template v-slot:activator="{ on }">
              <v-btn text v-on="on">
                {{ user }}
                <v-icon x-small class="ml-1">mdi-chevron-down</v-icon>
              </v-btn>
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
    <v-sheet id="scrolling-techniques" class="overflow-y-auto" fluid color="#ECEFF1">
      <v-container fluid style="padding:2.5% 6%;">
        <v-dialog v-model="logOutDialog" max-width="400">
          <v-card>
            <v-card-title style="font-size:18px;font-family:Georgia;">Are you sure to logout?</v-card-title>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn text @click="logOutDialog = false" style="color:red;">Disagree</v-btn>
              <v-btn text @click="logout">Agree</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-content>
          <v-card class="mx-auto" max-width="1200">
            <v-card dark flat>
              <v-card-title class="pa-2 blue-grey lighten-1">
                <v-btn icon>
                  <v-icon>mdi-menu</v-icon>
                </v-btn>
                <h3 class="title font-weight-light text-center grow">Discussion</h3>
              </v-card-title>
            </v-card>
            <v-card-text class="py-0">
              <v-timeline align-top dense clipped>
                <v-timeline-item fill-dot class="white--text mb-12" color="black" large>
                  <template v-slot:icon>
                    <v-icon color="white">mdi-plus</v-icon>
                  </template>
                  <v-text-field
                    v-model="input"
                    hide-details
                    flat
                    label="Leave a comment..."
                    solo
                    @keydown.enter="comment"
                  >
                    <template v-slot:append>
                      <v-btn class="mx-0" depressed @click="comment">Post</v-btn>
                    </template>
                  </v-text-field>
                </v-timeline-item>
                <v-timeline-item
                  v-for="discuss in discussList"
                  :key="discuss.mainId"
                  class="mb-4"
                  color="grey"
                  small
                >
                  <v-card>
                    <v-card-title>
                      {{discuss.username}}
                    </v-card-title>
                    <v-card-text>
                      <v-row justify="space-between">
                        <v-col cols="6" v-text="discuss.content" style="text-align:left"></v-col>
                        <v-col class="text-right" cols="3" v-text="discuss.createTime"></v-col>
                        <v-btn class="mt-2" cols="1" @click="replyShow = discuss.mainId" text>Reply</v-btn>
                        <v-card
                          v-if="replyShow == discuss.mainId"
                          style="margin-left: 50%;width: 80%;margin-bottom:3%;margin-right:2%"
                        >
                          <v-card-text>
                            <v-text-field
                              v-model="inputReply"
                              hide-details
                              flat
                              label="Leave a reply..."
                              solo
                              @keydown.enter="replyToComment(discuss.mainId)"
                            >
                              <template v-slot:append>
                                <v-btn
                                  class="mx-0"
                                  depressed
                                  @click="replyToComment(discuss.mainId)"
                                >Post</v-btn>
                              </template>
                            </v-text-field>
                          </v-card-text>
                        </v-card>
                      </v-row>
                    </v-card-text>
                  </v-card>

                  <v-timeline-item
                    v-for="reply in discuss.sub_discussions"
                    :key="reply.subId"
                    class="mr-4 mt-2"
                    color="white"
                    small
                  >
                    <v-row justify="space-between" style="font-size:0.5rem">
                      <v-col cols="2" style="font-weight:bold;">{{reply.username}}</v-col>
                      <v-col cols="4" v-text="reply.content"></v-col>
                      <v-col class="text-right" cols="2" v-text="reply.createTime"></v-col>
                      <v-btn class="mt-2" cols="1" @click="reply2Show = reply.subId" text>Reply</v-btn>
                      <v-card
                        v-if="reply2Show == reply.subId"
                        style="margin-left: 50%;width: 80%;margin-bottom:3%;margin-right:2%"
                      >
                        <v-card-text>
                          <v-text-field
                            v-model="input2Reply"
                            hide-details
                            flat
                            label="Leave a reply..."
                            solo
                            @keydown.enter="replyToReply(reply.subId,discuss.username)"
                          >
                            <template v-slot:append>
                              <v-btn
                                class="mx-0"
                                depressed
                                @click="replyToReply(reply.subId,discuss.username)"
                              >Post</v-btn>
                            </template>
                          </v-text-field>
                        </v-card-text>
                      </v-card>
                    </v-row>
                  </v-timeline-item>
                </v-timeline-item>
              </v-timeline>
            </v-card-text>
          </v-card>
        </v-content>
      </v-container>
    </v-sheet>
  </v-card>
</template>

<script>
export default {
  name: "discussion",
  data: () => ({
    user: "",
    token: "",
    openOnHover: true,
    logOutDialog: false,
    topics: [],
    input: null,
    reply: [],
    inputReply: null,
    input2Reply: null,
    replyShow: 0,
    reply2Show: 0,
    nonce: 0,
    discussant: "",
    discussList: [],
    subDiscussList: [],
    mainId: 0
  }),
  created() {
    this.handleUserData(); 
    this.handleToken();
  },
  computed: {
    timeline() {
      return this.topics.slice().reverse();
    },
    replayTimeline() {
      return this.reply.slice().reverse();
    }
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
    showLogInDialog() {
      this.loginDialog = true;
    },
    goToMenu() {
      if (this.token == null) {
        this.loginDialog = true;
      } else {
        this.$router.replace("/StartMenu");
      }
    },
    validateLoginform() {
      if (this.$refs.loginform.validate()) {
        this.$axios
          .post("http://114.116.136.180/login", {
            username: this.loginform.username,
            password: this.loginform.password
          })
          .then(resp => {
            if (resp.status == 200 && resp.data.hasOwnProperty("token")) {
              this.$store.commit("login", resp.data);
              this.$router.replace("/StartMenu");
              this.$message({
                message: "Login successfully !",
                type: "success",
                duration: 2000
              });
            } else {
              this.$message({
                message: "Login error !",
                type: "error",
                duration: 2000
              });
            }
          })
          .catch(error => {
            console.log(error);
            if (error.message == "Request failed with status code 403") {
              this.$message({
                message: "Wrong password !",
                type: "error",
                duration: 2000
              });
            } else if (error.message == "Request failed with status code 404") {
              this.$message({
                message: "Username not found !",
                type: "error",
                duration: 2000
              });
            }
          });
      }
    },
    resetLoginform() {
      this.$refs.loginform.reset();
    },
    validateRegisterform() {
      if (this.$refs.registerform.validate()) {
        this.$axios
          .post("http://114.116.136.180/register", {
            username: this.registerform.username,
            fullname: this.registerform.fullname,
            password: this.registerform.password,
            checkPassword: this.registerform.checkPassword,
            e_mail: this.registerform.e_mail,
            companie: this.registerform.companie,
            area: this.registerform.area
          })
          .then(resp => {
            // 根据后端的返回数据修改
            if (resp.status == 200 && resp.data.hasOwnProperty("id")) {
              // 跳转到login
              this.tab = "'tab-1'";
              this.$message({
                message: "successful registration",
                type: "success",
                duration: 2000
              });
            } else {
              this.$message({
                message: "Register error !",
                type: "error",
                duration: 2000
              });
            }
          })
          .catch(error => {
            console.log(error);
            if (error.message == "Request failed with status code 400") {
              this.$message({
                message: "Username has been registered !",
                type: "error",
                duration: 2000
              });
            }
          });
      }
    },
    resetRegisterform() {
      this.$refs.registerform.reset();
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
    comment() {
      var self = this;
      this.$axios
        .post("http://114.116.136.180/newPost", {
          articleId: this.$route.query.articleId,
          username: JSON.parse(localStorage.getItem("userDetails")).username,
          content: this.input
        })
        .then(resp => {
          // 根据后端的返回数据修改
          if (resp.status == 200 && resp.data) {
            var data = resp.data;
            self.discussList = JSON.parse(JSON.stringify(data));
            //location.reload();
          } else {
            self.$message({
              message: "process error !",
              type: "error",
              duration: 2000
            });
          }
        })
        .catch(error => {
          console.log(error);
          self.$message({
            message: error.message,
            type: "error",
            duration: 2000
          });
        });

      this.input = null;
    },
    replyToComment(mainId) {
      var self = this;
      this.$axios
        .post("http://114.116.136.180/newComment", {
          articleId: this.$route.query.articleId,
          mainId: mainId,
          username: JSON.parse(localStorage.getItem("userDetails")).username,
          content: this.inputReply
        })
        .then(resp => {
          // 根据后端的返回数据修改
          if (resp.status == 200 && resp.data) {
            var data = resp.data;
            self.discussList = JSON.parse(JSON.stringify(data));
            //location.reload();
          } else {
            self.$message({
              message: "process error !",
              type: "error",
              duration: 2000
            });
          }
        })
        .catch(error => {
          console.log(error);
          self.$message({
            message: error.message,
            type: "error",
            duration: 2000
          });
        });

      this.inputReply = null;
      this.replyShow = false;
    },
    replyToReply(subId, reviewee) {
      var username =
        JSON.parse(localStorage.getItem("userDetails")).username +
        " reply to " +
        reviewee;
      var self = this;
      this.$axios
        .post("http://114.116.136.180/replyComment", {
          articleId: this.$route.query.articleId,
          subId: subId,
          username: username,
          content: this.input2Reply
        })
        .then(resp => {
          // 根据后端的返回数据修改
          if (resp.status == 200 && resp.data) {
            var data = resp.data;
            self.discussList = JSON.parse(JSON.stringify(data));
            //location.reload();
          } else {
            self.$message({
              message: "process error !",
              type: "error",
              duration: 2000
            });
          }
        })
        .catch(error => {
          console.log(error);
          self.$message({
            message: error.message,
            type: "error",
            duration: 2000
          });
        });

      this.input2Reply = null;
      this.reply2Show = false;
    },
    loadData() {
      var self = this;
      this.$axios
        .get("http://114.116.136.180/discussion", {
          params: {
            articleId: this.$route.query.articleId // 123
          }
        })
        .then(resp => {
          // 根据后端的返回数据修改
          if (resp.status == 200 && resp.data) {
            var data = resp.data;
            self.discussList = JSON.parse(JSON.stringify(data));
          } else {
            self.$message({
              message: "process error !",
              type: "error",
              duration: 2000
            });
          }
        })
        .catch(error => {
          console.log(error);
          self.$message({
            message: error.message,
            type: "error",
            duration: 2000
          });
        });
    }
  },
  mounted: function() {
    this.discussant = this.$route.query.discussant;
    // 页面加载执行方法
    this.loadData();
  }
};
</script>

<style>
::-webkit-scrollbar {
  width: 0px;
}
</style>
