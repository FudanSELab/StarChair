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
                      <v-card class="ma-4" style=" border-radius:10px;">
                        <div>
                          <div class="overline mb-7 pl-4 pt-2" style="height:20px;text-align:left;">
                            <v-icon class="mr-2" small>mdi-book-outline</v-icon>MY APPLICATION
                          </div>
                          <h4
                            class="mb-6"
                            style="font-family:Times New Roman;
                            font-size: 1.45rem;letter-spacing:.0125em;color:#272727;"
                          >{{ meeting.fullName }}</h4>
                          <v-row
                            justify="center"
                            style="font-size:18px !important;font-family:Times New Roman;"
                          >
                            <h6 class="mr-2">Application Status:</h6>
                            <h6
                              v-if="
                                handleMeetingStatus(meeting.isVarified) ==
                                  'pass'
                              "
                              style="color:#2E7D32;"
                            >Pass</h6>
                            <h6
                              v-if="
                                handleMeetingStatus(meeting.isVarified) ==
                                  'reject'
                              "
                              style="color:#D32F2F;"
                            >Rejected</h6>
                            <h6
                              v-if="
                                handleMeetingStatus(meeting.isVarified) ==
                                  'check pending'
                              "
                              style="color:#FFAB00"
                            >Check Pending</h6>
                          </v-row>
                          <v-btn
                            text
                            @click="handleApplyDialog(meeting,meetingKey)"
                            class="mt-2 mb-10"
                            color="#37474F"
                          >more information</v-btn>
                        </div>
                      </v-card>
                    </v-col>
                  </v-row>
                  <v-dialog v-model="dialogMeetingVisible" max-width="500">
                    <v-card class="pa-8">
                      <h4
                        class="mb-8"
                        style="font-family:Georgia;
                            font-size: 1.35rem;letter-spacing:.0125em;color:#272727;"
                      >Application Information</h4>
                      <v-container fluid style="overflow:scroll">
                        <v-simple-table>
                          <template v-slot:default>
                            <tbody class="ml-2 text-left" >
                              <tr v-for="item in tableDataApply" :key="item.left">
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
  name: "myApplication",
  data() {
    return {
      user: "",
      token: "",
      openOnHover: true,
      logOutDialog: false,
      applyList: [],
      meetingList: [],
      topicList: [],
      tableDataApply: [],
      dialogMeetingVisible: false,
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
    nextPage() {
      if (this.page + 1 <= this.numberOfPages) this.page += 1;
    },
    formerPage() {
      if (this.page - 1 >= 1) this.page -= 1;
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
    },
    handleMeetingStatus(status) {
      if (status == "false") {
        return "check pending";
      } else {
        return status;
      }
    },
    loadData: function() {
      var self = this;
      this.$axios
        .get("http://114.116.136.180/myApplication", {
          params: {
            username: JSON.parse(localStorage.getItem("userDetails")).username
          }
        })
        .then(function(response) {
          var data = response.data;
          self.applyList = JSON.parse(JSON.stringify(data));
          self.handleMeetingTopicList();
          if (self.applyList.length == 0) {
            self.$message({
              message: "No Application Found !",
              type: "info",
              duration: 2000
            });
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    handleApplyDialog(meeting, meetingKey) {
      this.dialogMeetingVisible = true;
      meetingKey = meetingKey + 6 * (this.page - 1);
      var meetingTopic = "";
      this.topicList[meetingKey].forEach(element => {
        meetingTopic += element.topic + "\\";
      });
      this.tableDataApply = [
        {
          left: "Full Name",
          right: meeting.fullName
        },
        {
          left: "Applicant",
          right: meeting.applicant
        },
        {
          left: "Shorten Form",
          right: meeting.shortenForm
        },
        {
          left: "Location",
          right: meeting.location
        },
        {
          left: "Time",
          right: meeting.time
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
