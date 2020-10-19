<template>
  <v-card class="overflow-hidden" style="width:100%;height:100%;background-color:#ECEFF1;">
    <v-app-bar flat dark height="60" style="border-radius:0px;" color="#263238">
      <v-toolbar-title style="font-family:Georgia" class="ml-8">StarChair</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn text to="startMenu">Menu</v-btn>
      <v-btn text to="Homepage">Home</v-btn>
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
                            <v-icon class="mr-2" small>mdi-book-outline</v-icon>AUDIT CONFERENCE
                          </div>
                          <h4
                            class="mb-5"
                            style="font-family:Times New Roman;
                            font-size: 1.45rem;letter-spacing:.0125em;color:#272727;"
                          >{{ meeting.fullName }}</h4>
                          <v-row justify="center">
                            <v-btn
                              text
                              @click="handleDialog(meeting,meetingKey)"
                              color="#37474F"
                            >more information</v-btn>
                          </v-row>
                          <v-row justify="center" class="pb-8">
                            <v-btn
                              text
                              style="color:green"
                              class="mt-2"
                              @click="postStatus(meeting.id, 'pass')"
                            >Pass</v-btn>
                            <v-btn
                              text
                              style="color:red"
                              class="mt-2"
                              @click="postStatus(meeting.id, 'reject')"
                            >Reject</v-btn>
                          </v-row>
                        </div>
                      </v-card>
                    </v-col>
                  </v-row>
                  <v-dialog v-model="dialogMeetingVisible" max-width="450">
                    <v-card class="pa-8">
                      <h4
                        class="mb-8"
                        style="font-family:Georgia;
                            font-size: 1.35rem;letter-spacing:.0125em;color:#272727;"
                      >Meeting information</h4>
                      <v-simple-table>
                        <template v-slot:default>
                          <tbody class="text-left">
                            <tr v-for="item in tableData" :key="item.left">
                              <td style="font-family:Times New Roman;font-size:16px">{{ item.left }}</td>
                              <td
                                style="font-family:Times New Roman;font-size:16px"
                              >{{ item.right }}</td>
                            </tr>
                          </tbody>
                        </template>
                      </v-simple-table>
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
  name: "reviewMeeting",
  data() {
    return {
      user: "",
      token: "",
      openOnHover: true,
      logOutDialog: false,
      applyList: [
        {
          id: 1374,
          shortenForm: "t13",
          fullName: "test13",
          time: "2020-05-14",
          location: "shanghai",
          ddl: "2020-04-27",
          publishingTime: "2020-04-30",
          applicant: "test1",
          isVarified: "false",
          submitStatus: "pass",
          authorities: []
        },
        [
          { id: 1375, topic: "math" },
          { id: 1377, topic: "computer" },
          { id: 1379, topic: "science" }
        ],
        {
          id: 1406,
          shortenForm: "t",
          fullName: "test14",
          time: "2020-05-13",
          location: "s",
          ddl: "2020-04-27",
          publishingTime: "2020-04-30",
          applicant: "test1",
          isVarified: "false",
          submitStatus: "pass",
          authorities: []
        },
        [
          { id: 1407, topic: "math" },
          { id: 1409, topic: "chemistry" }
        ]
      ],
      meetingList: [],
      topicList: [],
      dialogMeetingVisible: false,
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
    loadData: function() {
      var self = this;
      this.$axios
        .get("http://114.116.136.180/getUncheckedConference")
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
    handleDialog(meeting,meetingKey) {
      this.dialogMeetingVisible = true;
      var meetingTopic = "";
      meetingKey = meetingKey+6*(this.page-1);
      this.topicList[meetingKey].forEach(element => {
        meetingTopic+=(element.topic+"\\");
      });
      //console.log(this.topicList[meetingKey]);
      this.tableData = [
        {
          left: "ShortenForm",
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
          left: "PublishingTime",
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
      for(var i = 0; i < this.applyList.length; i+=2){
        this.meetingList.push(this.applyList[i]);
        this.topicList.push(this.applyList[i+1]);
      }
      console.log(this.topicList);
    },
    postStatus(id, status) {
      this.$axios
        .post("http://114.116.136.180/changeApplicationStatus", {
          applicationId: id,
          applyStatus: status
        })
        .then(resp => {
          if (resp.status == 200 && resp.data) {
            this.$message({
              message: "Successfully processed !",
              type: "success",
              duration: 2000
            });
            location.reload();
          } else {
            self.$message({
              message: "Process error !",
              type: "error",
              duration: 2000
            });
          }
        })
        .catch(error => {
          self.$message({
            message: error.message,
            type: "error",
            duration: 2000
          });
        });
    }
  },
  mounted: function() {
    this.loadData();
  }
};
</script>

<style>
::-webkit-scrollbar {
  width: 0px;
}
</style>
