<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
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
          <div id="chair_container">
            <v-card min-height="600">
              <v-toolbar flat max-height="45">
                <v-toolbar-title class="ml-4">Chair</v-toolbar-title>
                <v-spacer></v-spacer>
                <v-btn
                  tile
                  class="white--text mr-4"
                  color="blue-grey"
                  right
                  @click="handleContribute(submitStatus)"
                >
                  {{ submitStatus }}
                  <v-icon right class="ml-3">mdi-file-document</v-icon>
                </v-btn>
              </v-toolbar>
              <v-divider></v-divider>
              <v-tabs vertical>
                <v-tab class="justify-start">
                  <v-icon left>mdi-arrow-up-bold-box-outline</v-icon>Invite PC
                  Member
                </v-tab>
                <v-tab class="justify-start">
                  <v-icon left>mdi-account-box</v-icon>Check PC Member
                </v-tab>
                <v-tab class="justify-start">
                  <v-icon left>mdi-message-text</v-icon>Meeting review
                </v-tab>

                <v-tab-item class="m-4 mt-2">
                  <v-card flat>
                    <div>
                      <v-row style="width:100%">
                        <v-col :cols="6">
                          <v-row class="ml-2">
                            <v-text-field
                              label="Please enter the real name of the invitee"
                              v-model="input"
                            ></v-text-field>
                            <v-btn class="mt-4" text @click="searchUser(input)">
                              <v-icon>mdi-account-search-outline</v-icon>
                            </v-btn>
                          </v-row>
                        </v-col>
                      </v-row>

                      <v-data-table
                        height="300"
                        show-select
                        v-model="selected"
                        :headers="headers"
                        :items="tableData"
                        :items-per-page="5"
                        class="elevation-1"
                        :footer-props="{
                          showFirstLastPage: true,
                          firstIcon: 'mdi-arrow-collapse-left',
                          lastIcon: 'mdi-arrow-collapse-right',
                          prevIcon: 'mdi-minus',
                          nextIcon: 'mdi-plus'
                        }"
                      ></v-data-table>
                    </div>

                    <div style="margin-top: 20px">
                      <v-btn
                        tile
                        class="white--text mr-4"
                        color="blue-grey"
                        @click="sendInvitation"
                      >
                        Send Invitation
                        <v-icon right class="ml-3">mdi-chat-outline</v-icon>
                      </v-btn>
                    </div>
                  </v-card>
                </v-tab-item>

                <v-tab-item class="ml-1 mt-2 mr-6">
                  <v-card flat>
                    <v-data-iterator
                      :items="pcList"
                      :items-per-page.sync="itemsPerPage"
                      :page="page"
                      hide-default-footer
                    >
                      <template v-slot:default="props">
                        <v-row>
                          <v-col
                            class="block"
                            style="max-width:33.3%"
                            v-for="(pc, pcKey) in props.items"
                            :key="pcKey"
                          >
                            <v-card width="320" class="m-3">
                              <v-system-bar>
                                <v-icon left>mdi-account-multiple-outline</v-icon>Pcmember
                              </v-system-bar>

                              <v-row class="px-6 py-3" align="center">
                                <v-menu
                                  bottom
                                  right
                                  transition="scale-transition"
                                  origin="top left"
                                >
                                  <template v-slot:activator="{ on }">
                                    <v-chip pill v-on="on">
                                      <v-avatar left>
                                        <v-icon right>mdi-account-box</v-icon>
                                      </v-avatar>
                                      {{ pc.fullName }}
                                    </v-chip>
                                    <div>
                                      <v-btn class="ma-1 ml-3" text icon>
                                        <v-icon medium>mdi-home</v-icon>
                                      </v-btn>

                                      <v-btn class="ma-1" text icon color="blue lighten-2">
                                        <v-icon medium>mdi-thumb-up</v-icon>
                                      </v-btn>

                                      <v-btn class="ma-1" text icon color="red lighten-2">
                                        <v-icon medium>mdi-thumb-down</v-icon>
                                      </v-btn>
                                    </div>
                                  </template>
                                  <v-card width="270">
                                    <v-list dark>
                                      <v-list-item>
                                        <v-list-item-avatar>
                                          <v-icon right large>mdi-account-box</v-icon>
                                        </v-list-item-avatar>
                                        <v-list-item-content>
                                          <v-list-item-title>
                                            {{
                                            pc.username
                                            }}
                                          </v-list-item-title>
                                          <v-list-item-subtitle>
                                            {{
                                            pc.e_mail
                                            }}
                                          </v-list-item-subtitle>
                                        </v-list-item-content>
                                      </v-list-item>
                                    </v-list>
                                    <v-list>
                                      <v-list-item class="mt-1">
                                        <v-list-item-subtitle>
                                          <strong>Company :</strong>
                                          {{ pc.companie }}
                                        </v-list-item-subtitle>
                                        <v-list-item-subtitle>
                                          <strong>Area :</strong>
                                          {{ pc.area }}
                                        </v-list-item-subtitle>
                                      </v-list-item>
                                      <v-list-item>
                                        <p style="font:14px Avenil" class="ml-2">
                                          <strong>Topic :</strong>
                                          {{ handlePcTopic(pcKey) }}
                                        </p>
                                      </v-list-item>
                                    </v-list>
                                  </v-card>
                                </v-menu>
                              </v-row>
                            </v-card>
                          </v-col>
                        </v-row>
                      </template>
                      <template v-slot:footer>
                        <v-row class="mt-2 mb-2 mr-4" align="center" justify="end">
                          <span
                            class="mr-4 grey--text"
                            style="font-size:16px !important;font-family:Times New Roman;"
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
                  </v-card>
                </v-tab-item>

                <v-tab-item class="m-4 mt-2">
                  <v-btn
                    tile
                    left
                    absolute
                    class="white--text ml-4"
                    color="blue-grey"
                    @click="showChooseDialog"
                    v-if="openMeetingReview == true"
                  >Open meeting review</v-btn>
                  <v-dialog v-model="chooseReviewWay" width="400">
                    <v-container style="background-color:white" class="pa-8">
                      <h5
                        style="color:black;"
                        class="text-left"
                      >Choose a manuscript allocation strategy :</h5>
                      <v-radio-group v-model="reviewWay" :mandatory="false">
                        <v-radio :value="reviewWay1" :label="reviewWay1"></v-radio>
                        <v-radio :value="reviewWay2" :label="reviewWay2"></v-radio>
                      </v-radio-group>
                      <v-btn text color="success" @click="handleOpenReview(reviewWay)">confirm</v-btn>
                    </v-container>
                  </v-dialog>

                  <v-btn
                    tile
                    left
                    absolute
                    class="white--text ml-4"
                    color="blue-grey"
                    @click="showReviewContent"
                    v-if="checkReviewContent == true"
                  >Check the allocation results</v-btn>
                  <v-btn
                    tile
                    color="blue-grey"
                    class="white--text mt-2"
                    @click="publishReviewResults"
                    v-if="tentativeStatus == true"
                  >Publish the tentative results</v-btn>
                  <v-btn
                    tile
                    color="blue-grey"
                    class="white--text mt-2"
                    @click="publishFinalResults"
                    v-if="finalStatus == true"
                  >Publish the final results</v-btn>
                  <v-container fluid v-if="meetingReviewContent == true">
                    <v-btn
                      tile
                      color="blue-grey"
                      class="white--text mt-2"
                      @click="finishReview"
                      v-if="closeReview == true"
                    >Closing meeting review</v-btn>
                    <v-data-iterator
                      :items="reviewList"
                      :items-per-page.sync="itemsPerPageReview"
                      :page="pageReview"
                      hide-default-footer
                    >
                      <template v-slot:default="props">
                        <v-row justify="start">
                          <v-col
                            class="block"
                            v-for="item in props.items"
                            :key="item.name"
                            cols="6"
                          >
                            <v-card
                              height="560"
                              class="ma-4 pa-4"
                              style=" overflow: scroll;border-radius:10px;"
                            >
                              <div class="overline mb-1 pl-4" style="height:20px;text-align:left;">
                                <v-icon class="mr-2" small>mdi-book-outline</v-icon>ARTICLE DETAILS
                              </div>
                              <v-card-title
                                class="subheading font-weight-bold ml-4"
                                style="font-family:Times New Roman;
                                font-size: 1.45rem;letter-spacing:.0125em;color:#272727;"
                              >{{ item.articleName }}</v-card-title>
                              <v-simple-table
                                class="pl-4 pr-4 pb-6"
                                style="font-size:18px !important;font-family:Times New Roman;text-align:left"
                              >
                                <tbody>
                                <tr>
                                  <td>
                                    <h6 class="mt-2 mr-2" style="font-weight:bold">Contributor :</h6>
                                  </td>
                                  <td>
                                    <h5
                                      class="mt-1 mr-2"
                                      style="color:#2E7D99;font-weight:bold"
                                    >{{ item.contributor }}</h5>
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <h6 class="mr-2 mt-2" style="font-weight:bold">Topics :</h6>
                                  </td>
                                  <td class="pt-2 pb-2">
                                <tr
                                  class="mr-2"
                                  v-for="(key, index) in item.topics"
                                  :key="index"
                                  style="overflow:
                                    auto;font-size:16px;word-break: break-all"
                                >
                                  {{
                                  key
                                  }}
                                </tr>
                                </td>
                                </tr>
                                <tr v-if="submitStatus!=='Final release phase'">
                                  <td>
                                    <h6
                                      class="mt-2 mr-2"
                                      style="color:#000;font-weight:bold"
                                    >Review Status :</h6>
                                  </td>
                                  <td v-if="item.reviewStatus == 'Reviewed'">
                                    <h6 style="color:#2E7D32;font-weight:bold">Checked</h6>
                                  </td>
                                  <td v-if="item.reviewStatus == 'Reviewing'">
                                    <h6
                                      class="mt-2"
                                      style="color:#FFAB00;font-weight:bold"
                                    >Under Review</h6>
                                  </td>
                                  <td v-if="item.reviewStatus == 'false'">
                                    <h6
                                      class="mt-2"
                                      style="color:#6A5ACD;font-weight:bold"
                                    >Check Pending</h6>
                                  </td>
                                  <td v-if="item.reviewStatus == 'Rebuttaled'">
                                    <h6
                                      class="mt-2"
                                      style="color:#6A5ACD;font-weight:bold"
                                    >Rebuttaled</h6>
                                  </td>
                                  <td v-if="item.reviewStatus == 'Confirmed'">
                                    <h6
                                      class="mt-2"
                                      style="color:#6A5ACD;font-weight:bold"
                                    >Confirmed</h6>
                                  </td>
                                </tr>
                                <tr v-if="item.reviewStatus == 'Rebuttaled'&&submitStatus!=='Final release phase'">
                                  <td>
                                    <h6 class="mt-2 mr-2" style="font-weight:bold">Rebuttal :</h6>
                                  </td>
                                  <td>
                                    <h5
                                      class="mt-1 mr-2"
                                    >{{ item.rebuttal }}</h5>
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <h6
                                      class="mr-2"
                                      style="font-weight:bold;text-align:left"
                                    >Score :</h6>
                                  </td>
                                  <td class="ml-6 pt-2 pb-2">
                                <tr
                                  class="mt-2"
                                  v-for="(key,
                                        index) in item.refereeScoreList"
                                  :key="index"
                                  style="overflow: auto;text-align:left;font-size:16px"
                                >
                                  {{
                                  key
                                  }}
                                </tr>
                                </td>
                                </tr>
                                <tr>
                                  <td style="width=50%">
                                    <h6
                                      class="mr-2"
                                      style="font-weight:bold;text-align:left;"
                                    >Comment :</h6>
                                  </td>
                                  <td class="ml-6 pt-2 pb-2">
                                <tr
                                  class="mt-2"
                                  v-for="(key,
                                        index) in item.refereeCommentList"
                                  :key="index"
                                  style="overflow: auto;text-align:left;font-size:16px;word-break: break-all"
                                >
                                  {{
                                  key
                                  }}
                                </tr>
                                </td>
                                </tr>
                                <tr>
                                  <td>
                                    <h6
                                      class="mr-2"
                                      style="font-weight:bold;text-align:left"
                                    >Confidence :</h6>
                                  </td>
                                  <td class="ml-6 pt-2 pb-2">
                                <tr
                                  class="mt-2"
                                  v-for="(key,
                                        index) in item.refereeConfidenceList"
                                  :key="index"
                                  style="overflow: auto;text-align:left;font-size:16px"
                                >
                                  {{
                                  key
                                  }}
                                </tr>
                                </td>
                                </tr>
                                <tr v-if="submitStatus == 'First Discussion'&&tentativeStatus==false">
                                  <td>
                                    <h6
                                      class="mr-2"
                                      style="font-weight:bold;text-align:left"
                                    >Discussion :</h6>
                                  </td>
                                  <td class="ml-6 pt-2 pb-2">
                                    <v-btn
                                      tile
                                      color="blue-grey"
                                      class="white--text mt-2"
                                      @click="toDiscussion(item.id)"
                                    >First discussion</v-btn>
                                  </td>
                                </tr>
                                <tr
                                  v-if="(item.reviewStatus == 'Rebuttaled'||item.reviewStatus == 'Confirmed')
                                           &&(finalStatus == false && submitStatus !== 'Final release phase')"
                                >
                                  <td>
                                    <h6
                                      class="mr-2"
                                      style="font-weight:bold;text-align:left"
                                    >Discussion :</h6>
                                  </td>
                                  <td class="ml-6 pt-2 pb-2">
                                    <v-btn
                                      tile
                                      color="blue-grey"
                                      class="white--text mt-2"
                                      @click="toDiscussion(item.id)"
                                    >Sencond discussion</v-btn>
                                  </td>
                                </tr>
                                </tbody>
                              </v-simple-table>
                            </v-card>
                          </v-col>
                        </v-row>
                      </template>

                      <template v-slot:footer>
                        <v-row class="mt-2" align="center" justify="end">
                          <span
                            class="mr-4 grey--text"
                            style="font-size:19px !important;font-family:Times New Roman;"
                          >
                            Page {{ pageReview }} of
                            {{ numberOfPagesReview }}
                          </span>
                          <v-btn
                            fab
                            dark
                            color="#455A64"
                            class="mr-1"
                            @click="formerPageReview"
                            x-small
                          >
                            <v-icon>mdi-chevron-left</v-icon>
                          </v-btn>
                          <v-btn
                            fab
                            dark
                            color="#455A64"
                            class="ml-1"
                            @click="nextPageReview"
                            x-small
                          >
                            <v-icon>mdi-chevron-right</v-icon>
                          </v-btn>
                        </v-row>
                      </template>
                    </v-data-iterator>
                  </v-container>
                  <v-dialog v-model="noArticleDialog" max-width="400">
                    <v-card>
                      <v-card-title style="font-size:18px;font-family:Georgia;">
                        There is no submission at present, do you choose to
                        close the submission and open the review?
                      </v-card-title>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn text @click="noArticleDialog = false" style="color:red;">Disagree</v-btn>
                        <v-btn text @click="handleNoArticle">Agree</v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
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
    name: "chair",
    data() {
      return {
        user: "",
        token: "",
        openOnHover: true,
        logOutDialog: false,
        chooseReviewWay: false,
        input: "",
        pcList: [{}, {}, {}, {}, {}],
        topicList: [],
        tableData: [],
        selected: [],
        inviteeList: [],
        page: 1,
        itemsPerPage: 6,
        submitStatus: "Open the Submission",
        reviewWay: "",
        reviewWay1: "Based on topic relevance",
        reviewWay2: "Based on the average burden of review",
        noArticleDialog: false,
        headers: [
          {
            text: "ID",
            align: "start",
            sortable: false,
            value: "id"
          },
          { text: "Username", value: "username" },
          { text: "Fullname", value: "fullName" },
          { text: "Email", value: "e_mail" },
          { text: "Company", value: "companie" },
          { text: "Area", value: "area" }
        ],
        //meeting review 标签页的相关变量
        meetingReviewContent: false, //稿件分配结果的container
        checkReviewContent: false, //查看分配结果的按钮
        openMeetingReview: false, //开启审稿的按钮
        pageReview: 1,
        itemsPerPageReview: 2,
        articleList: [],

        closeReview: false,
        tentativeStatus: false,
        finalStatus: false,
        confStatus: "",

        reviewList: [
          //article基本信息
          {
            articleName: "Frozen Yogurt 1",
            contributor: "test", //投稿人用户名
            reviewStatus: "under review",
            topics: ["aasqwqa", "ssbbfb", "cwcfgfc", "sdfsefg"],
            refereeScoreList: ["hahaha:10", "bababa:20", "clclcl:20"] //审稿人及对应的分数
          }
        ]
      };
    },
    created() {
      this.handleUserData(); 
      this.handleToken();
    },
    computed: {
      numberOfPages() {
        return Math.ceil(this.pcList.length / this.itemsPerPage);
      },
      numberOfPagesReview() {
        return Math.ceil(this.reviewList.length / this.itemsPerPageReview);
      }
    },
    methods: {
      loadData() {
        var self = this;
        this.$axios
          .get("http://114.116.136.180/getPcList", {
            params: {
              meetingID: this.$route.query.confID // 123
            }
          })
          .then(resp => {
            // 根据后端的返回数据修改
            if (resp.status == 200 && resp.data) {
              var data = resp.data;
              self.topicList = JSON.parse(JSON.stringify(data.topicDetails));
              self.pcList = JSON.parse(JSON.stringify(data.userDetails));
              
              if (data.submitStatus == "false") {
                self.submitStatus = "Open the Submission";
              } else if (data.submitStatus == "inSubmit") {
                self.submitStatus = "Be Submitting ···";
                self.openMeetingReview = true;
                self.checkReviewContent = false;
              } else if (
                data.submitStatus == "withTopic" ||
                data.submitStatus == "withAverage"
              ) {
                self.submitStatus = "Be Reviewing ···";
                self.openMeetingReview = false;
                self.checkReviewContent = true;
              } else if (data.submitStatus == "firstDiscussion") {
                self.submitStatus = "First Discussion";
                self.openMeetingReview = false;
                self.checkReviewContent = true;
              } else if (data.submitStatus == "rebuttal") {
                self.submitStatus = "Rebuttal phase";
                self.openMeetingReview = false;
                self.checkReviewContent = true;
              } else if (data.submitStatus == "final") {
                self.submitStatus = "Final release phase";
                self.openMeetingReview = false;
                self.checkReviewContent = true;
              }
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
      },
      handlePcTopic(pcKey) {
        var meetingTopic = "";
        pcKey = pcKey + 6 * (this.pageReview - 1);
        this.topicList[pcKey].forEach(element => {
          meetingTopic += "\t❤\t" + element.topic;
        });
        return meetingTopic;
      },
      nextPage() {
        if (this.page + 1 <= this.numberOfPages) this.page += 1;
      },
      formerPage() {
        if (this.page - 1 >= 1) this.page -= 1;
      },
      nextPageReview() {
        if (this.pageReview + 1 <= this.numberOfPagesReview) this.pageReview += 1;
      },
      formerPageReview() {
        if (this.pageReview - 1 >= 1) this.pageReview -= 1;
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
      showChooseDialog() {
        this.chooseReviewWay = true;
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
          type: "success",
          duration: 2000
        });
      },
      handleContribute(status) {
        if (status == "Open the Submission") {
          if (Date.now() <= new Date(this.$route.query.confDdl).getTime()) {
            status = "inSubmit";
            this.$axios
              .get("http://114.116.136.180/changeSubmitStatus", {
                params: {
                  meetingID: this.$route.query.confID,
                  submitStatus: status
                }
              })
              .then(resp => {
                // 根据后端的返回数据修改
                if (resp.status == 200 && resp.data) {
                  this.$message({
                    message: "Contribution started successfully !",
                    type: "success",
                    duration: 2000
                  });
                  this.submitStatus = "Be Submitting ···";
                } else {
                  this.$message({
                    message: "process error !",
                    type: "error",
                    duration: 2000
                  });
                }
              })
              .catch(error => {
                console.log(error);
                this.$message({
                  message: error.message,
                  type: "error",
                  duration: 2000
                });
              });
          } else {
            this.$message({
              message:
                "The deadline of the meeting has arrived. Submission cannot be opened !",
              type: "error",
              duration: 5000
            });
          }
        }
      },
      searchUser(input) {
        var self = this;
        this.$axios
          .get("http://114.116.136.180/searchUserInfo", {
            params: {
              fullName: input
            }
          })
          .then(resp => {
            // 根据后端的返回数据修改
            if (resp.status == 200 && resp.data) {
              var data = resp.data;
              self.tableData = JSON.parse(JSON.stringify(data));
            } else {
              this.$message({
                message: "process error !",
                type: "error",
                duration: 2000
              });
            }
          })
          .catch(error => {
            console.log(error);
            this.$message({
              message: error.message,
              type: "error",
              duration: 2000
            });
          });
      },
      sendInvitation() {
        var self = this;
        self.selected.forEach(element => {
          this.inviteeList.push(element.id);
        });
        //console.log(this.inviteeList);
        if (
          self.submitStatus !== "Open the Submission" &&
          self.submitStatus !== "Be Submitting ···"
        ) {
          this.$message({
            message:
              "You can only send invitation before starting article review !",
            type: "error",
            duration: 2000
          });
        } else {
          this.$axios
            .post("http://114.116.136.180/sendInvitation", {
              meetingID: this.$route.query.confID,
              inviter: JSON.parse(localStorage.getItem("userDetails")).username,
              invitee: self.inviteeList
            })
            .then(resp => {
              // 根据后端的返回数据修改
              if (resp.status == 200 && resp.data) {
                var data = resp.data;
                if (JSON.parse(JSON.stringify(data)).message == "success") {
                  this.$message({
                    message:
                      "The invitation was sent successfully! Please check the status of the invitation on the page:Invitations sent",
                    type: "success",
                    duration: 2000
                  });
                }
              } else {
                this.$message({
                  message: "process error !",
                  type: "error",
                  duration: 2000
                });
              }
            })
            .catch(error => {
              console.log(error);
              this.$message({
                message: error.message,
                type: "error",
                duration: 2000
              });
            });
        }
      },
      handleOpenReview(reviewWay) {
        if (!reviewWay) {
          this.$message({
            message: "Please choose at least one item !",
            type: "error",
            duration: 2000
          });
        } else {
          if (reviewWay == this.reviewWay1) {
            reviewWay = "withTopic";
          } else {
            reviewWay = "withAverage";
          }
          if (this.submitStatus !== "Open the Submission") {
            var self = this;
            this.$axios
              .get("http://114.116.136.180/startReview", {
                params: {
                  meetingId: this.$route.query.confID,
                  strategy: reviewWay
                }
              })
              .then(resp => {
                // 根据后端的返回数据修改
                if (resp.status == 200 && resp.data) {
                  var data = resp.data;
                  var mes = JSON.parse(JSON.stringify(data)).message;
                  if (mes == "success") {
                    self.$message({
                      message:
                        "Successfully send the draft strategy, the system will assign tasks to your PC member !",
                      type: "success",
                      duration: 2000
                    });
                    self.chooseReviewWay = false;
                    self.openMeetingReview = false;
                    self.checkReviewContent = true; //////////////////////////////////////////////////////////
                    self.submitStatus = "Be Reviewing ···";
                  } else if (mes == "No article found!") {
                    self.noArticleDialog = true;
                  }
                  //self.tableData = JSON.parse(JSON.stringify(data));
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
                  duration: 5000
                });
              });
          } else {
            this.$message({
              message:
                "The conference has not opened the submission, can not open the review !",
              type: "error",
              duration: 5000
            });
          }
        }
      },
      showReviewContent() {
        var self = this;
        this.$axios
          .get("http://114.116.136.180/viewAllArticles", {
            params: {
              meetingId: this.$route.query.confID
            }
          })
          .then(resp => {
            // 根据后端的返回数据修改
            if (resp.status == 200 && resp.data) {
              var data = resp.data;
              self.articleList = JSON.parse(JSON.stringify(data));
              //self.handleMeetingTopicList();
              if (self.articleList.length == 0) {
                this.$message({
                  message: "No article found !",
                  type: "info",
                  duration: 2000
                });
              } else {
                self.reviewList = self.articleList;
                var checkedNumber = 0;

                console.log(self.articleList);
                self.articleList.forEach(element => {
                  var tentativeNum = 0;//article的第一次分数修改数量

                  element["refereeScoreList"] = []; //添加refereeScoreList为key,里面是审稿人及对应分数的list
                  element["refereeCommentList"] = [];
                  element["refereeConfidenceList"] = [];
                  if (element.reviewStatus == "Reviewed") {
                    checkedNumber++;
                  } else if(element.reviewStatus == "Rebuttaled" || element.reviewStatus == "Confirmed"){
                    var finalNumber = 0;//article的第二次分数修改数量
                  }
                  //articleList里每一个item都有一个scorelist和memberlist
                  element.scores.forEach(elementScore => {
                    if (elementScore.modifyStatus !== null) {
                      tentativeNum++;
                      finalNumber++;
                    }
                    //score里的ID和member里的ID相对应
                    element.pcmembers.forEach(elementPC => {
                      if (elementPC.id == elementScore.pcmember_id) {
                        //找到对应的审稿人和分数，添加到reviewlist里
                        element["refereeScoreList"].push(
                          elementPC.username + ": " + elementScore.score
                        );
                        element["refereeCommentList"].push(
                          elementPC.username + ": " + elementScore.comment
                        );
                        element["refereeConfidenceList"].push(
                          elementPC.username + ": " + elementScore.confidence
                        );
                      }
                    });
                  });
                  if (
                    tentativeNum == element.scores.length &&
                    self.submitStatus != "Rebuttal phase" &&
                    self.submitStatus !== "Final release phase"
                  ) {
                    self.tentativeStatus = true;
                  }
                  if (
                    finalNumber == element.scores.length &&
                    self.submitStatus !== "Final release phase"
                  ) {
                    self.finalStatus = true;
                  }
                });

                if (
                  checkedNumber == self.articleList.length &&
                  self.submitStatus !== "First Discussion" &&
                  self.submitStatus != "Rebuttal phase" &&
                  self.submitStatus != "Final release phase"
                ) {
                  self.closeReview = true;
                }


                self.reviewList.forEach(element => {
                  var eleTopics = [];
                  element.topics.forEach(ele => {
                    eleTopics.push(ele.topic);
                  });
                  element.topics = eleTopics;
                });

                self.meetingReviewContent = true;
                self.checkReviewContent = false;

                //self.tableData = JSON.parse(JSON.stringify(data));
              }
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
      },
      handleNoArticle() {
        this.noArticleDialog = false;
        var self = this;
        this.$axios
          .get("http://114.116.136.180/confirmFinish", {
            params: {
              meetingId: this.$route.query.confID
            }
          })
          .then(resp => {
            // 根据后端的返回数据修改
            if (resp.status == 200 && resp.data) {
              var data = resp.data;
              if (JSON.parse(JSON.stringify(data)).message == "success") {
                self.$message({
                  message: "Successfully process !",
                  type: "success",
                  duration: 2000
                });
              }
              self.chooseReviewWay = false;
              self.openMeetingReview = false;
              self.checkReviewContent = true;
              self.submitStatus = "Be Reviewing ···";
              //self.tableData = JSON.parse(JSON.stringify(data));
              //self.meetingReviewContent = true; //////////////////////////////////////////////////////////
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
      },
      finishReview() {
        var self = this;
        this.$axios
          .get("http://114.116.136.180/finishReview", {
            params: {
              meetingId: this.$route.query.confID
            }
          })
          .then(resp => {
            // 根据后端的返回数据修改
            if (resp.status == 200) {
              self.submitStatus = "First Discussion";
              self.closeReview = false;
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
      },
      publishReviewResults() {
        /////////////////////////////////////////////////////////////////////
        var self = this;
        this.$axios
          .get("http://114.116.136.180/firstPublishScores", {
            params: {
              meetingId: this.$route.query.confID
            }
          })
          .then(resp => {
            // 根据后端的返回数据修改
            if (resp.status == 200) {
              self.$message({
                message: "Successfully publish the review results !",
                type: "success",
                duration: 2000
              });
              self.tentativeStatus = false;
              self.closeReview = false;
              this.$router.go(0);
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
              duration: 5000
            });
          });
      },
      publishFinalResults() {
        /////////////////////////////////////////////////////////////////////
        var self = this;
        this.$axios
          .get("http://114.116.136.180/finalPublishScores", {
            params: {
              meetingId: this.$route.query.confID
            }
          })
          .then(resp => {
            // 根据后端的返回数据修改
            if (resp.status == 200) {
              self.$message({
                message: "Successfully publish the final results !",
                type: "success",
                duration: 2000
              });
              self.finalStatus = false;
              self.tentativeStatus = false;
              this.$router.go(0);
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
              duration: 5000
            });
          });
      },
      toDiscussion(articleId) {
        this.$router.push("http://114.116.136.180/discussion?articleId=" + articleId);
      }
    },
    mounted: function() {
      this.confStatus = this.$route.query.confStatus;
      this.loadData();
      // 页面加载执行方法
    }
  };
</script>

<style>
  ::-webkit-scrollbar {
    width: 0px;
  }
  #chair_container {
    width: 100%;
    margin: 1% 0;
  }
</style>
