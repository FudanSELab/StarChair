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
          <div id="user_container">
            <v-card height="650">
              <v-toolbar flat dark class="pl-4" style="font-family:Georgia;">
                <v-toolbar-title>Invitation</v-toolbar-title>
              </v-toolbar>
              <v-tabs vertical>
                <v-tab>
                  <v-icon left>mdi-email</v-icon>Received invitation
                </v-tab>
                <v-tab style="margin-left:-17px">
                  <v-icon left>mdi-arrow-up-bold-box-outline</v-icon>Invitations I sent
                </v-tab>

                <!--received invitation-->
                <v-tab-item>
                  <v-container fluid class="pa-6"> <!-- //////////////////receiveList////////////////////-->
                    <v-data-iterator
                      :items="receiveList"
                      :items-per-page.sync="receivedItemsPerPage"
                      :page="receivedPage"
                      hide-default-footer
                    >
                      <template v-slot:default="props">
                        <v-row justify="start">
                          <v-col
                            class="block"
                            v-for="(meeting, meetingKey) in props.items"
                            :key="meetingKey"
                            :cols="6"
                          >
                            <v-card class="ma-2" style="border-radius:10px;">
                              <div>
                                <div
                                  class="overline pl-4 pt-2"
                                  style="height:20px;text-align:left;"
                                >
                                  <v-icon class="mr-2" small>mdi-book-outline</v-icon>INVITATION I RECEIVED
                                </div>
                                <v-col>
                                  <v-card-text
                                    class="pb-2 pt-3"
                                    style="text-align:left;font-family:Arial;font-weight:bold;font-size:1.2rem;letter-spacing:.0125em;color:#272727;"
                                  >Meeting: {{ meeting.fullName }}</v-card-text>
                                  <v-card-actions class="pt-0">
                                    <v-btn
                                      text
                                      small
                                      color="#01579B"
                                      @click="handleReceivedDialog(meeting,meetingKey)"
                                    >more information</v-btn>
                                  </v-card-actions>
                                  <v-card-text
                                    style="text-align:left;font-size:0.96rem"
                                    class="pt-0 pb-2"
                                  >
                                    "Would you become my
                                    PcMember?"
                                  </v-card-text>

                                  <v-card-actions>
                                    <v-btn
                                      text
                                      style="color: green"
                                      @click="chooseTopicVisible=true"
                                    >pass</v-btn>
                                    <v-btn
                                      text
                                      style="color: red"
                                      @click="
                                        handleInvitation(meeting, 'reject')
                                      "
                                    >reject</v-btn>
                                  </v-card-actions>

                                  <v-dialog
                                    title="topics"
                                    v-model="chooseTopicVisible"
                                    max-width="400"
                                  >
                                    <v-container fluid style="background-color:white">
                                      <v-checkbox
                                        v-model="selected"
                                        :label="item.topic"
                                        :value="item.topic"
                                        v-for="(item,itemKey) in handleTopicList(meetingKey)"
                                        :key="itemKey"
                                        class="m-4"
                                      ></v-checkbox>
                                      <v-btn text @click="handleInvitation(meeting, 'pass')">confirm</v-btn>
                                    </v-container>
                                  </v-dialog>
                                </v-col>
                              </div>
                            </v-card>
                          </v-col>
                        </v-row>
                        <v-dialog
                          title="Information"
                          v-model="dialogReceiveVisible"
                          class="meetingDialog"
                          width="400"
                        >
                          <div class="info_form">
                            <v-expansion-panels>
                              <v-expansion-panel>
                                <v-expansion-panel-header
                                  style="font-family:Georgia;
                            font-size: 1.1rem;letter-spacing:.0125em;color:#272727;"
                                >Meeting Information</v-expansion-panel-header>
                                <v-expansion-panel-content fluid style="overflow:scroll">
                                  <v-simple-table>
                                    <template v-slot:default>
                                      <tbody class="pl-4">
                                        <tr v-for="(item,itemKey) in tableDataReceive" :key="itemKey">
                                          <td
                                            style="font-family:Times New Roman;font-size:16.5px;text-align:left"
                                          >{{ item.left }}</td>
                                          <td
                                            style="font-family:Times New Roman;font-size:16.5px;text-align:left"
                                          >{{ item.right }}</td>
                                        </tr>
                                      </tbody>
                                    </template>
                                  </v-simple-table>
                                </v-expansion-panel-content>
                              </v-expansion-panel>
                              <v-expansion-panel>
                                <v-expansion-panel-header
                                  style="font-family:Georgia;
                            font-size: 1.1rem;letter-spacing:.0125em;color:#272727;"
                                >
                                  Inviter's
                                  Information
                                </v-expansion-panel-header>
                                <v-expansion-panel-content>
                                  <v-simple-table>
                                    <template v-slot:default>
                                      <tbody>
                                        <tr v-for="(item,itemKey) in tableDataReceive" :key="itemKey">
                                          <td
                                            style="font-family:Times New Roman;font-size:16.5px;text-align:left"
                                          >{{ item.left1 }}</td>
                                          <td
                                            style="font-family:Times New Roman;font-size:16.5px;text-align:left"
                                          >{{ item.right1 }}</td>
                                        </tr>
                                      </tbody>
                                    </template>
                                  </v-simple-table>
                                </v-expansion-panel-content>
                              </v-expansion-panel>
                            </v-expansion-panels>
                          </div>
                        </v-dialog>
                      </template>
                      <template v-slot:footer>
                        <v-row class="mt-4" align="center" justify="end">
                          <span
                            class="mr-4 grey--text"
                            style="font-size:19px !important;font-family:Times New Roman;"
                          >
                            Page {{ receivedPage }} of
                            {{ numberOfReceivedPages }}
                          </span>
                          <v-btn
                            fab
                            dark
                            color="#455A64"
                            class="mr-1"
                            @click="receivedFormerPage"
                            x-small
                          >
                            <v-icon>mdi-chevron-left</v-icon>
                          </v-btn>
                          <v-btn
                            fab
                            dark
                            color="#455A64"
                            class="ml-1 mr-5"
                            @click="receivedNextPage"
                            x-small
                          >
                            <v-icon>mdi-chevron-right</v-icon>
                          </v-btn>
                        </v-row>
                      </template>
                    </v-data-iterator>
                  </v-container>
                </v-tab-item>

                <!--sent invitation-->
                <v-tab-item>
                  <v-container fluid class="pa-4"> <!-- //////////////////////////////////////-->
                    <v-data-iterator
                      :items="sentInvitationList"
                      :items-per-page.sync="sentItemsPerPage"
                      :page="sentPage"
                      hide-default-footer
                    >
                      <template v-slot:default="props">
                        <v-row justify="start">
                          <v-col
                            class="block"
                            v-for="(invitee,inviteeKey) in props.items"
                            :key="inviteeKey"
                            :cols="6"
                          >
                            <v-card class="ma-4 mr-3" style="border-radius:10px;">
                              <div>
                                <div
                                  class="overline pl-4 pt-2"
                                  style="height:20px;text-align:left;"
                                >
                                  <v-icon class="mr-2" small>mdi-book-outline</v-icon>INVITATION I SENT
                                </div>
                                <v-col>
                                  <v-card-text
                                    class="pb-2 pt-3"
                                    style="text-align:left;font-family:Arial;font-weight:bold;font-size:1.2rem;letter-spacing:.0125em;color:#272727;"
                                  >
                                    Invitee:
                                    {{ handleInviteeName(invitee.invitee) }}
                                  </v-card-text>
                                  <v-card-actions class="pt-0">
                                    <v-btn
                                      text
                                      small
                                      color="#01579B"
                                      @click="handleSentDialog(invitee,inviteeKey)"
                                    >more information</v-btn>
                                  </v-card-actions>
                                  <v-card-text class="mt-1 ml-3 pb-2 mb-2">
                                    <v-row>
                                      <h6 class="mr-2" style="font-size:17px;">Invitation Status :</h6>
                                      <h6
                                        v-if="
                                          handleInvitationStatus(
                                            invitee.invitationStatus
                                          ) == 'pass'
                                        "
                                        style="color:#2E7D32;font-weight:bold"
                                      >Pass</h6>
                                      <h6
                                        v-if="
                                          handleInvitationStatus(
                                            invitee.invitationStatus
                                          ) == 'reject'
                                        "
                                        style="color:#D32F2F;font-weight:bold"
                                      >Rejected</h6>
                                      <h6
                                        v-if="
                                          handleInvitationStatus(
                                            invitee.invitationStatus
                                          ) == 'unhandled'
                                        "
                                        style="color:#FFAB00;font-weight:bold"
                                      >Check Pending</h6>
                                    </v-row>
                                  </v-card-text>
                                </v-col>
                              </div>
                            </v-card>
                          </v-col>
                        </v-row>
                        <v-dialog
                          title="Information"
                          v-model="dialogSentVisible"
                          class="meetingDialog"
                          width="400"
                        >
                          <div class="info_form">
                            <v-expansion-panels>
                              <v-expansion-panel>
                                <v-expansion-panel-header
                                  style="font-family:Georgia;
                            font-size: 1.1rem;letter-spacing:.0125em;color:#272727;"
                                >Meeting Information</v-expansion-panel-header>
                                <v-expansion-panel-content>
                                  <v-simple-table>
                                    <template v-slot:default>
                                      <tbody>
                                        <tr v-for="(item,itemKey) in tableDataSent" :key="itemKey">
                                          <td
                                            style="font-family:Times New Roman;font-size:16.5px;text-align:left"
                                          >{{ item.left }}</td>
                                          <td
                                            style="font-family:Times New Roman;font-size:16.5px;text-align:left"
                                          >{{ item.right }}</td>
                                        </tr>
                                      </tbody>
                                    </template>
                                  </v-simple-table>
                                </v-expansion-panel-content>
                              </v-expansion-panel>

                              <v-expansion-panel>
                                <v-expansion-panel-header
                                  style="font-family:Georgia;
                            font-size: 1.1rem;letter-spacing:.0125em;color:#272727;"
                                >
                                  Invitee's
                                  Information
                                </v-expansion-panel-header>
                                <v-expansion-panel-content>
                                  <v-simple-table>
                                    <template v-slot:default>
                                      <tbody>
                                        <tr v-for="(item,itemKey) in tableDataSent" :key="itemKey">
                                          <td
                                            style="font-family:Times New Roman;font-size:16.5px;text-align:left"
                                          >{{ item.left1 }}</td>
                                          <td
                                            style="font-family:Times New Roman;font-size:16.5px;text-align:left"
                                          >{{ item.right1 }}</td>
                                        </tr>
                                      </tbody>
                                    </template>
                                  </v-simple-table>
                                </v-expansion-panel-content>
                              </v-expansion-panel>
                            </v-expansion-panels>
                          </div>
                        </v-dialog>
                      </template>
                      <template v-slot:footer>
                        <v-row class="mt-2" align="center" justify="end">
                          <span
                            class="mr-4 grey--text"
                            style="font-size:19px !important;font-family:Times New Roman;"
                          >Page {{ sentPage }} of {{ numberOfSentPages }}</span>
                          <v-btn
                            fab
                            dark
                            color="#455A64"
                            class="mr-1"
                            @click="sentFormerPage"
                            x-small
                          >
                            <v-icon>mdi-chevron-left</v-icon>
                          </v-btn>
                          <v-btn
                            fab
                            dark
                            color="#455A64"
                            class="ml-1 mr-6"
                            @click="sentNextPage"
                            x-small
                          >
                            <v-icon>mdi-chevron-right</v-icon>
                          </v-btn>
                        </v-row>
                      </template>
                    </v-data-iterator>
                  </v-container>
                </v-tab-item>
              </v-tabs>
            </v-card>
          </div>
        </v-content>
      </v-container>
    </v-sheet>
  </v-card>
</template>

<script>
export default {
  name: "userCenter",
  data() {
    return {
      user: "",
      token: "",
      openOnHover: true,
      logOutDialog: false,
      receivedItemsPerPage: 4,
      receivedPage: 1,
      sentItemsPerPage: 4,
      sentPage: 1,
      inviteeName: "",

      //获取的会议、邀请等list
      receiveList: [],
      receiveTopicList: [],
      receiveInvitationList: [],
      receiveUserList: [],
      sentList: [],
      sentTopicList: [],
      sentInvitationList: [],
      sentUserList: [],

      tableDataReceive: [],
      chooseTopicVisible: false,
      selected: [],
      //receiveMeetingTopics: ["aaa", "bbb", "ccc"], //被邀请会议的topic list

      tableDataSent: [], //dialog中的详细信息list
      dialogReceiveVisible: false,
      dialogSentVisible: false //是否展示dialog
    };
  },
  created() {
    this.handleUserData();
    this.handleToken();
  },
  computed: {
    numberOfReceivedPages() {
      return Math.ceil(this.receiveUserList.length / this.receivedItemsPerPage);
    },
    numberOfSentPages() {
      return Math.ceil(this.sentUserList.length / this.sentItemsPerPage);
    }
  },
  methods: {
    receivedNextPage() {
      if (this.receivedPage + 1 <= this.numberOfReceivedPages)
        this.receivedPage += 1;
    },
    receivedFormerPage() {
      if (this.receivedPage - 1 >= 1) this.receivedPage -= 1;
    },
    sentNextPage() {
      if (this.sentPage + 1 <= this.numberOfSentPages) this.sentPage += 1;
    },
    sentFormerPage() {
      if (this.sentPage - 1 >= 1) this.sentPage -= 1;
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
    // 发送ajax请求，获取会议申请列表、收到邀请列表、发出邀请列表
    loadData: function() {
      var self = this;
      this.$axios
        .get("http://114.116.136.180/invitationIReceived", {
          params: {
            id: JSON.parse(localStorage.getItem("userDetails")).id ///id
          }
        })
        .then(function(response) {
          var applyData = JSON.parse(
            JSON.stringify(response.data.meetingDetails)
          );
          var topicData = JSON.parse(
            JSON.stringify(response.data.topicDetails)
          );
          var userData = JSON.parse(
            JSON.stringify(response.data.inviterDetails)
          );
          var invitationData = JSON.parse(
            JSON.stringify(response.data.invitationDetails)
          );
          self.receiveList = applyData;
          self.receiveUserList = userData;
          self.receiveInvitationList = invitationData;
          self.receiveTopicList = topicData;
          if (self.receiveList.length == 0) {
            self.$message({
              message: "No Invitation Received !",
              type: "info",
              duration: 2000
            });
          }
        })
        .catch(function(error) {
          console.log(error);
        });

      this.$axios
        .get("http://114.116.136.180/invitationISent", {
          params: {
            username: JSON.parse(localStorage.getItem("userDetails")).username
          }
        })
        .then(function(response) {
          var applyData = JSON.parse(
            JSON.stringify(response.data.meetingDetails)
          );
          var topicData = JSON.parse(
            JSON.stringify(response.data.topicDetails)
          );
          var userData = JSON.parse(
            JSON.stringify(response.data.inviteeDetails)
          );
          var invitationData = JSON.parse(
            JSON.stringify(response.data.invitationDetails)
          );
          self.sentList = applyData;
          self.sentUserList = userData;
          self.sentInvitationList = invitationData;
          self.sentTopicList = topicData;
          if (self.sentList.length == 0) {
            self.$message({
              message: "No Invitation Sent !",
              type: "info",
              duration: 2000
            });
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    handleInviteeName(inviteeID) {
      var inviteeName;
      this.sentUserList.forEach(element => {
        if (element.id == inviteeID) {
          inviteeName = element.username;
        }
      });
      return inviteeName;
    },
    handleInvitationStatus(status) {
      if (status == "false") {
        return "unhandled";
      } else {
        return status;
      }
    },
    handleInvitation(meeting, status) {
      var invitationID;
      if (this.selected.length == 0 && status == "pass") {
        this.$message({
          message: "Please choose at least one topic !",
          type: "error",
          duration: 2000
        });
      } else {
        console.log(this.selected);
        var self = this;
        this.receiveInvitationList.forEach(element => {
          if (element.meetingId == meeting.id) {
            invitationID = element.id;
          }
        });
        this.$axios
          .post("http://114.116.136.180/changeInvitationStatus", {
            id: invitationID,
            invitationStatus: status,
            topics: self.selected
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
              this.$message({
                message: "Process error !",
                type: "error",
                duration: 2000
              });
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
    handleTopicList(meetingKey) {
      return this.receiveTopicList[meetingKey];
    },
    handleReceivedDialog(meeting, meetingKey) {
      this.dialogReceiveVisible = true;
      var thisInvitation;
      var thisInviter;
      this.receiveInvitationList.forEach(element => {
        if (element.meetingId == meeting.id) {
          thisInvitation = element;
        }
      });
      this.receiveUserList.forEach(element => {
        if (element.username == thisInvitation.inviter) {
          thisInviter = element;
        }
      });
      var meetingTopic = "";
      meetingKey = meetingKey+6*(this.receivedPage-1);
      this.receiveTopicList[meetingKey].forEach(element => {
        meetingTopic+=(element.topic+"\\");
      });
      this.tableDataReceive = [
        {
          left1: "Username：",
          right1: thisInviter.username,
          left: "Full Name：",
          right: meeting.fullName
        },
        {
          left1: "Full Name：",
          right1: thisInviter.fullName,
          left: "Shorten Form：",
          right: meeting.shortenForm
        },
        {
          left1: "E_mail：",
          right1: thisInviter.e_mail,
          left: "Location：",
          right: meeting.location
        },
        {
          left1: "Company：",
          right1: thisInviter.companie,
          left: "Deadline：",
          right: meeting.ddl
        },
        {
          left1: "Area：",
          right1: thisInviter.area,
          left: "Publishing Time：",
          right: meeting.publishingTime
        },
        {
          left: "Applicant：",
          right: meeting.applicant
        },
        {
          left: "Time: ",
          right: meeting.time
        },
        {
          left: "Topic",
          right: meetingTopic
        }
      ];
    },
    handleSentDialog(invitee,inviteeKey) {
      //////////////////////////////////////invitee=invitationList.element
      this.dialogSentVisible = true;
      var thisInvitee, thisMeeting;
      this.sentUserList.forEach(element => {
        if (element.id == invitee.invitee) {
          thisInvitee = element;
        }
      });
      this.sentList.forEach(element => {
        if (element.id == invitee.meetingId) {
          thisMeeting = element;
        }
      });
      var meetingTopic = "";
      inviteeKey = inviteeKey+6*(this.sentPage-1);
      this.sentTopicList[inviteeKey].forEach(element => {
        meetingTopic+=(element.topic+"\\");
      });
      this.tableDataSent = [
        {
          left1: "Username：",
          right1: thisInvitee.username,
          left: "Full Name：",
          right: thisMeeting.fullName
        },
        {
          left1: "Full Name：",
          right1: thisInvitee.fullName,
          left: "Shorten Form：",
          right: thisMeeting.shortenForm
        },
        {
          left1: "E_mail：",
          right1: thisInvitee.e_mail,
          left: "Location：",
          right: thisMeeting.location
        },
        {
          left1: "Company：",
          right1: thisInvitee.companie,
          left: "Deadline：",
          right: thisMeeting.ddl
        },
        {
          left1: "Area：",
          right1: thisInvitee.area,
          left: "Publishing Time：",
          right: thisMeeting.publishingTime
        },
        {
          left: "Time：",
          right: thisMeeting.time
        },
        {
          left: "Applicant：",
          right: thisMeeting.applicant
        },
        {
          left: "Topic",
          right: meetingTopic
        }
      ];
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
