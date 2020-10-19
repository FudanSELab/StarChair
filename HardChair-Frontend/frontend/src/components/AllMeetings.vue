<template>
  <v-card class="overflow-hidden" style="width:100%;height:100%;background-color:#ECEFF1;">
    <v-app-bar flat dark height="60" style="border-radius:0px;" color="#263238">
      <v-toolbar-title style="font-family:Georgia;" class="ml-8">StarChair</v-toolbar-title>
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
                {{ user
                }}
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
      <v-container fluid style="padding:2% 10%;">
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
          <template>
            <v-container fluid>
              <!-- //////////////////applyList////////////////////-->
              <v-data-iterator
                :items="meetingList"
                :items-per-page.sync="itemsPerPage"
                :page="page"
                hide-default-footer
              >
                <template v-slot:default="props">
                  <v-row justify="start">
                    <v-col
                      class="block"
                      v-for="(meeting,meetingKey) in props.items"
                      :key="meetingKey"
                      :cols="4"
                    >
                      <v-card class="ma-4" style=" border-radius:10px;" height="240">
                        <div>
                          <div class="overline mb-7 pl-4 pt-2" style="height:20px;text-align:left;">
                            <v-icon class="mr-2" small>mdi-book-outline</v-icon>ALL CONFERENCES
                          </div>
                          <h4
                            class="mb-6"
                            style="font-family:Times New Roman;
                            font-size: 1.45rem;letter-spacing:.0125em;color:#272727;"
                          >{{ meeting.fullName }}</h4>
                          <v-row
                            style="font-size:18px !important;font-family:Times New Roman;"
                            justify="center"
                          >
                            <h6 class="mr-2">Contribute Status :</h6>
                            <h6 v-if="meeting.submitStatus == 'pass'" style="color:#FFAB00;">Pass</h6>
                            <h6
                              v-if="meeting.submitStatus == 'inSubmit'"
                              style="color:#2E7D32;"
                            >InSubmit</h6>
                          </v-row>
                          <v-btn
                            text
                            @click="handleDialog(meeting,meetingKey)"
                            class="mt-2"
                            color="#37474F"
                          >more information</v-btn>
                          <v-btn
                            text
                            v-if="ifContribute(meeting.submitStatus,meeting.ddl)"
                            style="color:green"
                            @click="handleIfChair(meeting.id,meetingKey)"
                            class="mt-1 mb-6"
                          >contribute</v-btn>
                        </div>
                      </v-card>
                    </v-col>
                  </v-row>
                  <v-dialog v-model="meetingDialog" max-width="450">
                    <v-card class="pa-8">
                      <h4
                        class="mb-8"
                        style="font-family:Georgia;
                            font-size: 1.35rem;letter-spacing:.0125em;color:#272727;"
                      >Meeting Information</h4>
                      <v-container fluid style="overflow:scroll">
                        <v-simple-table>
                          <template v-slot:default>
                            <tbody class="ml-2 text-left">
                              <tr v-for="item in tableData" :key="item.left">
                                <td
                                  style="font-family:Times New Roman;font-size:16px"
                                >{{ item.left }}</td>
                                <td
                                  style="font-family:Times New Roman;font-size:16px"
                                >{{ item.right }}</td>
                              </tr>
                            </tbody>
                          </template>
                        </v-simple-table>
                      </v-container>
                    </v-card>
                  </v-dialog>
                </template>
                <template v-slot:footer>
                  <v-row class="mt-2" align="center" justify="end">
                    <span
                      class="mr-4 grey--text"
                      style="font-size:19px !important;font-family:Times New Roman;"
                    >Page {{ page }} of {{ numberOfPages }}</span>
                    <v-btn fab dark color="#455A64" class="mr-1" @click="formerPage" x-small>
                      <v-icon>mdi-chevron-left</v-icon>
                    </v-btn>
                    <v-btn fab dark color="#455A64" class="ml-1" @click="nextPage" x-small>
                      <v-icon>mdi-chevron-right</v-icon>
                    </v-btn>
                  </v-row>
                </template>
              </v-data-iterator>
            </v-container>
          </template>
        </v-content>
      </v-container>
    </v-sheet>
  </v-card>
</template>

<script>
export default {
  name: "allMeetings",
  data() {
    return {
      token: "",
      user: "",
      openOnHover: true,
      logOutDialog: false,
      applyList: [],
      meetingList: [],
      topicList: [],
      meetingDialog: false,
      tableData: [],
      page: 1,
      itemsPerPage: 6
    };
  },
  created() {
    this.handleUserData();
    this.handleToken();
  },
  computed: {
    numberOfPages() {
      return Math.ceil(this.meetingList.length / this.itemsPerPage);
    }
  },
  methods: {
    ifContribute(status,time) {
      console.log(new Date(time).getTime());
      return (status == 'inSubmit') && (Date.now() <= new Date(time).getTime());
    },
    nextPage() {
      if (this.page + 1 <= this.numberOfPages) this.page += 1;
    },
    formerPage() {
      if (this.page - 1 >= 1) this.page -= 1;
    },
    handleIfChair(meetingID,meetingKey) {
      var meetingTopic = "";
      meetingKey = meetingKey + 6 * (this.page - 1);
      this.topicList[meetingKey].forEach(element => {
        meetingTopic += element.topic + "\\";
      });
      var self = this;
      this.$axios
        .get("http://114.116.136.180/checkIfChair", {
          params: {
            meetingId: meetingID,
            username: JSON.parse(localStorage.getItem("userDetails")).username
          }
        })
        .then(function(response) {
          var message = JSON.parse(JSON.stringify(response.data)).message;
          if (message == "success") {
            self.$router.push("/contribution?meetingId=" + meetingID + "&topicList=" + meetingTopic);
          } else if (message == "warning") {
            self.$message({
              message: "Chair cannot contribute !",
              type: "error",
              duration: 2000
            });
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    // 发送ajax请求，获取会议申请列表
    loadData: function() {
      var self = this;
      this.$axios
        .get("http://114.116.136.180/getAllPassedMeetings")
        .then(function(response) {
          var data = response.data;
          self.applyList = JSON.parse(JSON.stringify(data));
          self.handleMeetingTopicList();
          if (self.applyList.length == 0) {
            this.$message({
              message: "No meeting found !",
              type: "info",
              duration: 2000
            });
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    handleDialog(meeting, meetingKey) {
      this.meetingDialog = true;
      var meetingTopic = "";
      meetingKey = meetingKey + 6 * (this.page - 1);
      this.topicList[meetingKey].forEach(element => {
        meetingTopic += element.topic + "\\";
      });
      console.log(this.topicList[meetingKey] + meetingKey);
      this.tableData = [
        {
          left: "Shorten Form",
          right: meeting.shortenForm
        },
        {
          left: "FullName",
          right: meeting.fullName
        },
        {
          left: "Location",
          right: meeting.location
        },
        {
          left: "Deadline",
          right: meeting.ddl
        },
        {
          left: "Publishing Time",
          right: meeting.publishingTime
        },
        {
          left: "Applicant",
          right: meeting.applicant
        },
        {
          left: "Topic",
          right: meetingTopic
        }
      ];
    },
    handleMeetingTopicList() {
      for (var i = 0; i < this.applyList.length; i += 2) {
        this.meetingList.push(this.applyList[i]);
        this.topicList.push(this.applyList[i + 1]);
      }
    },
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
    }
  },
  mounted: function() {
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
