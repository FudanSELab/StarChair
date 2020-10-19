<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <v-card class="overflow-hidden" style="width:100%;height:100%;background-color:#ECEFF1;">
    <v-app-bar flat dark height="60" style="border-radius:0;" color="#263238">
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
    <v-sheet id="scrolling-techniques" class="overflow-y-auto" fluid>
      <v-container fluid style="padding:0;">
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
                :items="list"
                :items-per-page.sync="itemsPerPage"
                :page="page"
                hide-default-footer
              >
                <template v-slot:default="props">
                  <v-row justify="start" class="mx-auto">
                    <v-col
                      class="block"
                      v-for="(item, index) in props.items"
                      :key="index"
                      :cols="4"
                    >
                      <v-card
                        class="ma-3"
                        style="overflow: scroll;height:480px; border-radius:10px;"
                        :onload="loadCard(item)"
                      >
                        <div>
                          <div class="overline mb-7 pl-4 pt-2" style="height:20px;text-align:left;">
                            <v-icon class="mr-2" small>mdi-book-outline</v-icon>REVIEW Article
                          </div>
                          <h4
                            class="mb-6"
                            style="font-family:Times New Roman;
                            font-size: 1.45rem;letter-spacing:.0125em;color:#272727;"
                          >{{ item.articleName }}</h4>
                          <v-container fluid>
                            <v-simple-table class="pl-4 pr-4">
                              <tbody class="text-left justify-center">
                              <tr>
                                <td
                                  class="text-capitalize"
                                  style="font-family:Times New Roman;font-size:18px;width: 30%"
                                >Summary</td>
                                <td
                                  style="font-family:Times New Roman;font-size:18px;word-break: break-all"
                                >
                                  <v-row>{{ item.summary }}</v-row>
                                </td>
                              </tr>
                              <tr>
                                <td
                                  class="text-capitalize"
                                  style="font-family:Times New Roman;font-size:18px;width: 30%"
                                >Topics</td>
                                <td
                                  style="font-family:Times New Roman;font-size:18px;word-break: break-all"
                                >
                                  <v-row
                                    v-for="topic in item.topics"
                                    :key="topic.topic"
                                  >{{ topic.topic }}</v-row>
                                </td>
                              </tr>
                              <tr>
                                <td
                                  class="text-capitalize"
                                  style="font-family:Times New Roman;font-size:18px;width: 30%"
                                >File</td>
                                <td>
                                  <v-row>
                                    <v-btn
                                      color="blue-grey darken-3"
                                      text
                                      @click="pdfPreview(item)"
                                    >Preview</v-btn>
                                    <v-btn
                                      color="blue-grey darken-3"
                                      text
                                      @click="pdfDownload(item)"
                                    >Download</v-btn>
                                  </v-row>
                                </td>
                              </tr>
<!--                              reviewStatus-->
                              <tr v-if="item.scores[0].reviewStatus === 'reviewed'">
                                <td class="text-capitalize" style="font-family:Times New Roman;font-size:18px;width: 30%"
                                >Status</td>
                                <td style="font-family:Times New Roman;font-size:18px;word-break: break-all"
                                >
                                  <v-row style="color: green">
                                    {{item.scores[0].reviewStatus }}
                                  </v-row>
                                </td>
                              </tr>
                              <tr v-if="item.scores[0].reviewStatus == null">
                                <td
                                  class="text-capitalize"
                                  style="font-family:Times New Roman;font-size:18px;width: 30%"
                                >Status</td>
                                <td
                                  style="font-family:Times New Roman;font-size:18px;word-break: break-all"
                                >
                                  <v-row style="color: #d58512">To review</v-row>
                                </td>
                              </tr>
<!--                              Score-->
                              <tr v-if="item.scores[0].reviewStatus === 'reviewed'">
                                <td
                                  class="text-capitalize"
                                  style="font-family:Times New Roman;font-size:18px;width: 30%"
                                >Score</td>
                                <td
                                  style="font-family:Times New Roman;font-size:18px;word-break: break-all"
                                >
                                  <v-row>{{ item.scores[0].score }}</v-row>
                                </td>
                              </tr>
<!--                              Comment-->
                              <tr v-if="item.scores[0].reviewStatus === 'reviewed'">
                                <td
                                  class="text-capitalize"
                                  style="font-family:Times New Roman;font-size:18px;width: 30%"
                                >Comment</td>
                                <td
                                  style="font-family:Times New Roman;font-size:18px;word-break: break-all"
                                >
                                  <v-row>{{ item.scores[0].comment }}</v-row>
                                </td>
                              </tr>
<!--                              Confidence-->
                              <tr v-if="item.scores[0].reviewStatus === 'reviewed'">
                                <td
                                  class="text-capitalize"
                                  style="font-family:Times New Roman;font-size:18px;width: 30%"
                                >Confidence</td>
                                <td
                                  style="font-family:Times New Roman;font-size:18px;word-break: break-all"
                                >
                                  <v-row>
                                    {{
                                    item.scores[0].confidence
                                    }}
                                  </v-row>
                                </td>
                              </tr>
<!--                              Rebuttal-->
                              <tr v-if="item.reviewStatus==='Rebuttaled'">
                                <td
                                  class="text-capitalize"
                                  style="font-family:Times New Roman;font-size:18px;width: 30%"
                                >Rebuttal</td>
                                <td
                                  style="font-family:Times New Roman;font-size:18px;word-break: break-all"
                                >
                                  <v-row>
                                    {{
                                    item.rebuttal
                                    }}
                                  </v-row>
                                </td>
                              </tr>
<!--                              First discussion-->
                              <tr v-if="item.scores[0].reviewStatus === 'reviewed'&& item.scores[0].modifyStatus === null
                                        && submitStatus==='firstDiscussion'">
                                <td>
                                  <h6
                                    class="text-capitalize"
                                    style="font-family:Times New Roman;font-size:18px;"
                                  >Discussion</h6>
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
<!--                              Second discussion-->
                              <tr v-if="(item.reviewStatus === 'Rebuttaled'||item.reviewStatus === 'Confirmed')&& item.scores[0].modifyStatus === null
                                        && submitStatus==='rebuttal'">
                                <td>
                                  <h6
                                    class="text-capitalize"
                                    style="font-family:Times New Roman;font-size:18px;"
                                  >Discussion</h6>
                                </td>
                                <td class="ml-6 pt-2 pb-2">
                                  <v-btn
                                    tile
                                    color="blue-grey"
                                    class="white--text mt-2"
                                    @click="toDiscussion(item.id)"
                                  >Second discussion</v-btn>
                                </td>
                              </tr>
                              </tbody>
                            </v-simple-table>
<!--                            showEvaluateDialog-->
                            <v-btn v-if="item.scores[0].reviewStatus == null"
                              text
                              x-large
                              class="mt-4"
                              color="light-blue darken-4"
                              @click="showEvaluateDialog(item)"
                            >Review Now</v-btn>
<!--                            Modify-->
                            <v-btn v-if="item.scores[0].reviewStatus==='reviewed'&& item.scores[0].modifyStatus===null
                                         && (submitStatus==='firstDiscussion'||submitStatus==='rebuttal')"
                                   text
                                   x-large
                                   class="mt-4"
                                   color="light-blue darken-4"
                                   @click="showModifyDialog(item)"
                            >Modify</v-btn>
                            <v-btn v-if="item.scores[0].reviewStatus==='reviewed'&&item.scores[0].modifyStatus===null
                                        && (submitStatus==='firstDiscussion'||submitStatus==='rebuttal')"
                                   text
                                   x-large
                                   class="mt-4"
                                   color="light-blue darken-4"
                                   @click="showConfirmDialog(item)"
                            >Confirm</v-btn>
                          </v-container>
                        </div>
                      </v-card>
                    </v-col>
                  </v-row>
                  <v-dialog v-model="evaluateDialog" max-width="500">
                    <v-card class="pa-8">
                      <h4
                        class="mb-3 mt-4"
                        style="font-size:20px;font-family:Georgia;"
                      >Evaluate the article</h4>
                      <v-form
                        ref="contributionForm"
                        v-model="evaluateForm.valid"
                        lazy-validation
                        class="pl-6 pt-3 pr-6 pb-4 ml-10 mr-10"
                      >
                        <v-radio-group
                          v-model="evaluateForm.grade"
                          :rules="evaluateForm.gradeRules"
                          row
                        >
                          <template v-slot:label>
                            <div>
                              <p>Grade</p>
                            </div>
                          </template>
                          <v-radio label="-2(reject)" value="-2"></v-radio>
                          <v-radio label="-1(weak reject)" value="-1"></v-radio>
                          <v-radio label="1(weak accept)" value="1"></v-radio>
                          <v-radio label="2(accept)" value="2"></v-radio>
                        </v-radio-group>
                        <v-textarea
                          v-model="evaluateForm.comment"
                          :rules="evaluateForm.commentRules"
                          outlined
                          counter
                          auto-grow
                          label="Comment"
                          rows="2"
                          row-height="20"
                          required
                        ></v-textarea>
                        <v-radio-group
                          v-model="evaluateForm.confidence"
                          :rules="evaluateForm.confidenceRules"
                          row
                        >
                          <template v-slot:label>
                            <div>
                              <p>Confidence</p>
                            </div>
                          </template>
                          <v-radio label="very low" value="-2"></v-radio>
                          <v-radio label="low" value="-1"></v-radio>
                          <v-radio label="high" value="1"></v-radio>
                          <v-radio label="very high" value="2"></v-radio>
                        </v-radio-group>
                      </v-form>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn text @click="submitScore" style="color:green;">submit</v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                  <v-dialog v-model="modifyDialog" max-width="500">
                    <v-card class="pa-8">
                      <h4
                        class="mb-3 mt-4"
                        style="font-size:20px;font-family:Georgia;"
                      >Evaluate the article</h4>
                      <v-form
                        ref="contributionForm"
                        v-model="modifyForm.valid"
                        lazy-validation
                        class="pl-6 pt-3 pr-6 pb-4 ml-10 mr-10"
                      >
                        <v-radio-group
                          v-model="modifyForm.grade"
                          :rules="modifyForm.gradeRules"
                          row
                        >
                          <template v-slot:label>
                            <div>
                              <p>Grade</p>
                            </div>
                          </template>
                          <v-radio label="-2(reject)" value="-2"></v-radio>
                          <v-radio label="-1(weak reject)" value="-1"></v-radio>
                          <v-radio label="1(weak accept)" value="1"></v-radio>
                          <v-radio label="2(accept)" value="2"></v-radio>
                        </v-radio-group>
                        <v-textarea
                          v-model="modifyForm.comment"
                          :rules="modifyForm.commentRules"
                          outlined
                          counter
                          auto-grow
                          label="Comment"
                          rows="2"
                          row-height="20"
                          required
                        ></v-textarea>
                        <v-radio-group
                          v-model="modifyForm.confidence"
                          :rules="modifyForm.confidenceRules"
                          row
                        >
                          <template v-slot:label>
                            <div>
                              <p>Confidence</p>
                            </div>
                          </template>
                          <v-radio label="very low" value="-2"></v-radio>
                          <v-radio label="low" value="-1"></v-radio>
                          <v-radio label="high" value="1"></v-radio>
                          <v-radio label="very high" value="2"></v-radio>
                        </v-radio-group>
                      </v-form>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn text @click="submitModify" style="color:green;">submit</v-btn>
                        <v-text style="color: red;font-size: small">Only one chance!</v-text>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                  <v-dialog v-model="confirmDialog" max-width="500">
                    <v-card class="pa-8">
                      <h4
                        class="mb-3 mt-4"
                        style="font-size:20px;font-family:Georgia;"
                      >Confirm your evaluation</h4>
                        <v-col><v-btn text @click="submitConfirm" style="color:green;">Confirm</v-btn>
                          <v-text style="color: red;font-size: small">Only one chance!</v-text></v-col>
                        <v-btn text @click="confirmDialog=false" style="color:gray;">Close</v-btn>

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
    name: "pcMember",
    data() {
      return {
        submitStatus:'',
        page: 1,
        itemsPerPage: 3,
        user: "",
        token: "",
        openOnHover: true,
        logOutDialog: false,
        evaluateDialog: false,
        modifyDialog:false,
        confirmDialog:false,
        evaluateForm: {
          grade: 0,
          comment: "",
          confidence: 0,
          gradeRules: [v => !!v || "The grade cannot be empty"],
          commentRules: [
            v => !!v || "The comment cannot be empty",
            v => v.length <= 800 || "Max 800 characters"
          ],
          confidenceRules: [v => !!v || "The confidence cannot be empty"]
        },
        modifyForm: {
          grade: 0,
          comment: "",
          confidence: 0,
          gradeRules: [v => !!v || "The grade cannot be empty"],
          commentRules: [
            v => !!v || "The comment cannot be empty",
            v => v.length <= 800 || "Max 800 characters"
          ],
          confidenceRules: [v => !!v || "The confidence cannot be empty"]
        },
        list: [],
        item: {},
        articleName: "",
        authorName: "",
        summary: "",
        isRouterAlive: true
      };
    },
    provide() {
      return {
        reload: this.reload
      };
    },
    computed: {
      numberOfPages() {
        return Math.ceil(this.list.length / this.itemsPerPage);
      }
    },
    created() {
      this.handleUserData();
       this.handleToken();
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
      handleSizeChange: function(size) {
        this.pagesize = size;
        //console.log(`每页 ${val} 条`);
        // console.log(this.pagesize);
      },
      handleCurrentChange: function(currentPage) {
        this.currentPage = currentPage;
        // console.log(this.currentPage);
      },
      showLogOutDialog() {
        this.logOutDialog = true;
      },
      showEvaluateDialog(item) {
        let data = Object.assign({}, JSON.parse(JSON.stringify(item)));
        this.item = data;
        this.evaluateDialog = true;
      },
      showModifyDialog(item) {
        let data = Object.assign({}, JSON.parse(JSON.stringify(item)));
        this.item = data;
        this.modifyForm.grade=item.scores[0].score;
        this.modifyForm.comment=item.scores[0].comment;
        this.modifyForm.confidence=item.scores[0].confidence;
        this.modifyDialog = true;
      },
      showConfirmDialog(item) {
        let data = Object.assign({}, JSON.parse(JSON.stringify(item)));
        this.item = data;
        this.confirmDialog=true;
      },
      submitScore() {
        this.$axios
          .post("http://114.116.136.180/submitScore", {
            username: JSON.parse(localStorage.getItem("userDetails")).username,
            articleId: this.item.id,
            score: this.evaluateForm.grade,
            comment: this.evaluateForm.comment,
            confidence: this.evaluateForm.confidence,
          })
          .then(resp => {
            if (resp.status === 200 && resp.data) {
              this.loadData();
              this.evaluateDialog = false;
              this.evaluateForm.confidence = 1;
              this.evaluateForm.comment = " ";
              this.evaluateForm.grade = 1;
              this.$message({
                message: "successfully evaluate !",
                type: "success"
              });
            } else {
              this.$message({
                message: "submit error !",
                type: "error"
              });
            }
          })
          .catch(error => {
            console.log(error);
            this.$message({
              message: "submit error !",
              type: "error"
            });
          });
        this.reload();
      },
      submitModify(){
        this.$axios
          .post("http://114.116.136.180/submitModify", {
            username: JSON.parse(localStorage.getItem("userDetails")).username,
            articleId: this.item.id,
            score: this.modifyForm.grade,
            comment: this.modifyForm.comment,
            confidence: this.modifyForm.confidence,
            modifyStatus: "modified"
          })
          .then(resp => {
            if (resp.status === 200 && resp.data) {
              this.loadData();
              this.modifyDialog = false;
              this.modifyForm.confidence = 1;
              this.modifyForm.comment = " ";
              this.modifyForm.grade = 1;
              this.$message({
                message: "successfully modify!",
                type: "success"
              });
            } else {
              this.$message({
                message: "submit error !",
                type: "error"
              });
            }
          })
          .catch(error => {
            console.log(error);
            this.$message({
              message: "submit error !",
              type: "error"
            });
          });
        this.reload();
      },
      submitConfirm(){
        this.$axios
          .post("http://114.116.136.180/submitModify", {
            username: JSON.parse(localStorage.getItem("userDetails")).username,
            articleId: this.item.id,
            score: this.item.scores[0].score,
            comment: this.item.scores[0].comment,
            confidence: this.item.scores[0].confidence,
            modifyStatus: "confirmed"
          })
          .then(resp => {
            if (resp.status === 200 && resp.data) {
              this.loadData();
              this.$message({
                message: "successfully confirm !",
                type: "success"
              });
            } else {
              this.$message({
                message: "submit error !",
                type: "error"
              });
            }
          })
          .catch(error => {
            console.log(error);
            this.$message({
              message: "submit error !",
              type: "error"
            });
          });
        this.confirmDialog=false;
        this.reload();
      },
      pdfPreview(item) {
        this.$axios
          .get("http://114.116.136.180/showPdf", {
            responseType: "arraybuffer",
            params: {
              //用户参数
              path: item.filePath
            }
          })
          .then(res => {
            const binaryData = [];
            binaryData.push(res.data);
            //获取blob链接
            this.pdfUrl = window.URL.createObjectURL(
              new Blob(binaryData, { type: "application/pdf" })
            );
            window.open(this.pdfUrl);
          });
      },
      pdfDownload(item) {
        console.log(item.filePath);
        this.$axios
          .get("http://114.116.136.180/showPdf", {
            responseType: "arraybuffer",
            params: {
              //用户参数
              path: item.filePath
            }
          })
          .then(res => {
            var self = this;
            self.pdfUrl = window.URL.createObjectURL(
              new Blob([res.data], { type: `application/pdf` })
            );
            const fname = self.pdfUrl.substring(
              self.pdfUrl.lastIndexOf("/") + 1,
              self.pdfUrl.length
            ); // 下载文件的名字
            console.log(self.pdfUrl);
            const link = document.createElement("a");
            link.href = self.pdfUrl;
            link.setAttribute("download", fname);
            document.body.appendChild(link);
            link.click();
          });
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
        ////////////////////////////////////////////////////////////
        var self = this;
        this.$axios
          .get("http://114.116.136.180/getAllotedArticle", {
            params: {
              //用户参数
              username: JSON.parse(localStorage.getItem("userDetails")).username,
              meetingId: this.meetingId
            }
          })
          .then(function(response) {
            var data = response.data;
            console.log(data);
            self.submitStatus = JSON.parse(JSON.stringify(data.submitStatus));
            self.list = JSON.parse(JSON.stringify(data.articleList));
            console.log(self.list);
          })
          .catch(function(error) {
            console.log(error);
          });
      },
      loadCard(item) {
        if (item.scores.reviewStatus === "false") {
        } else if (item.scores.reviewStatus === "Reviewing") {
        }
      },
      reload() {
        this.isRouterAlive = false;
        this.$nextTick(function() {
          this.isRouterAlive = true;
        });
      },
      toDiscussion(articleId){
        this.$router.push("/discussion?articleId=" + articleId);
      }
    },
    mounted: function() {
      /////////////////////////////////////////////////////////////
      // 页面加载执行方法

      this.meetingId = this.$route.query.confID;
      console.log("this meeting:" + this.meetingId);
      this.loadData();
      // this.meetingId =1;
    }
  };
</script>

<style>
  ::-webkit-scrollbar {
    width: 0;
  }
</style>
