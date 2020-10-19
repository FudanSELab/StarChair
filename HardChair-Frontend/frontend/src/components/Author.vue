<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <v-card
    class="overflow-hidden"
    style="width:100%;height:100%;background-color:#ECEFF1;"
  >
    <v-app-bar flat dark height="60" style="border-radius:0;" color="#263238">
      <v-toolbar-title style="font-family:Georgia" class="ml-8"
        >StarChair
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <template>
        <div class="text-center mr-4" data-app="true">
          <v-menu :open-on-hover="openOnHover" offset-y>
            <template v-slot:activator="{ on }">
              <v-btn text v-on="on">
                <v-icon class="mr-2">mdi-lightbulb-on-outline </v-icon>
                Conference
                <v-icon x-small class="ml-1">mdi-chevron-down </v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item to="allMeetings">
                <v-list-item-title class="text-left"
                  >All Conferences
                </v-list-item-title>
              </v-list-item>
              <v-list-item to="UserConf">
                <v-list-item-title class="text-left"
                  >Attended Conference
                </v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </div>
      </template>
      <template>
        <div class="text-center mr-4" data-app="true">
          <v-menu :open-on-hover="openOnHover" offset-y>
            <template v-slot:activator="{ on }">
              <v-btn text v-on="on"
                >Application
                <v-icon x-small class="ml-1">mdi-chevron-down </v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item to="myApplication">
                <v-list-item-title class="text-left"
                  >My Application
                </v-list-item-title>
              </v-list-item>
              <v-list-item to="conferenceApplication">
                <v-list-item-title class="text-left"
                  >New Application
                </v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </div>
      </template>
      <v-btn text to="startMenu">
        Menu
      </v-btn>
      <v-btn text to="Homepage">
        Home
      </v-btn>
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
              <v-btn text v-on="on"
                >{{ user }}
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
            <v-card-title style="font-size:18px;font-family:Georgia;"
              >Are you sure to logout?
            </v-card-title>
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
          <template>
            <v-container fluid>
              <v-data-iterator
                :items="list"
                :items-per-page.sync="itemsPerPage"
                :page="page"
                :sort-by="sortBy.toLowerCase()"
                :sort-desc="sortDesc"
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
                      >
                        <div>
                          <div
                            class="overline mb-5 pl-4 pt-2"
                            style="height:20px;text-align:left;"
                          >
                            <v-icon class="mr-2" small
                              >mdi-book-outline
                            </v-icon>
                            About Your Article
                          </div>
                          <v-container>
                            <h4
                              style="font-family:Times New Roman;
                            font-size: 1.45rem;letter-spacing:.0125em;color:#272727;"
                            >
                              {{ item.articleName }}
                            </h4>
                            <v-simple-table class="pa-4">
                              <tbody class="text-left justify-center">
                                <tr>
                                  <td
                                    class="text-capitalize"
                                    style="font-family:Times New Roman;font-size:18px;width: 30%"
                                  >
                                    Author
                                  </td>
                                  <td
                                    style="font-family:Times New Roman;font-size:18px"
                                  >
                                    <v-row
                                      v-for="author in item.authors"
                                      :key="author.name"
                                    >
                                      <v-card
                                        max-width="300"
                                        outlined
                                        style="border-radius:5px;width: 100%;border-color:transparent"
                                      >
                                        <v-list flat>
                                          <v-list-item-group color="primary">
                                            <v-list-item>
                                              <v-list-item-content>
                                                <v-list-item-title
                                                  v-text="
                                                    'Name: ' + author.name
                                                  "
                                                ></v-list-item-title>
                                                <v-list-item-title
                                                  v-text="
                                                    'Area: ' + author.area
                                                  "
                                                ></v-list-item-title>
                                                <v-list-item-title
                                                  v-text="
                                                    'Company: ' + author.company
                                                  "
                                                ></v-list-item-title>
                                                <v-list-item-title
                                                  v-text="
                                                    'Email: ' + author.email
                                                  "
                                                ></v-list-item-title>
                                              </v-list-item-content>
                                            </v-list-item>
                                          </v-list-item-group>
                                        </v-list>
                                      </v-card>
                                    </v-row>
                                  </td>
                                </tr>
                                <tr>
                                  <td
                                    class="text-capitalize"
                                    style="font-family:Times New Roman;font-size:18px;width: 30%"
                                  >
                                    Summary
                                  </td>
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
                                  >
                                    Topics
                                  </td>
                                  <td
                                    style="font-family:Times New Roman;font-size:18px;word-break: break-all"
                                  >
                                    <v-row
                                      v-for="topic in item.topics"
                                      :key="topic.topic"
                                    >
                                      {{ topic.topic }}
                                    </v-row>
                                  </td>
                                </tr>
                              </tbody>
                            </v-simple-table>
<!--                            showModifyDialog-->
                            <v-btn
                              text
                              x-large
                              class="mt-4"
                              color="blue darken-4 "
                              v-if="item.reviewStatus == 'false'"
                              @click="showModifyDialog(item)"
                            >
                              Modify Contribution
                              <v-icon x-small>mdi-pencil</v-icon>
                            </v-btn>
<!--                            showScoresDialog-->
                            <v-btn
                              text
                              x-large
                              class="mt-4"
                              color="blue darken-4 "
                              v-if="item.reviewStatus === 'Reviewed'
                              ||item.reviewStatus === 'Rebuttaled'
                              ||item.reviewStatus === 'Confirmed'
                              ||item.reviewStatus === 'Final'"
                              @click="showScoresDialog(item)"
                              >View Scores</v-btn
                            >
                            <v-col>
<!--                            showRebuttalDialog-->
                            <v-btn
                              text
                              x-large
                              class="mt-4"
                              color="red"
                              v-if="item.reviewStatus === 'Reviewed'&& item.isAccepted==='false'"
                              @click="showRebuttalDialog(item)"
                            >Rebuttal</v-btn
                            >
<!--                            showConfirmDialog-->
                            <v-btn
                              text
                              x-large
                              class="mt-4"
                              color="green"
                              v-if="item.reviewStatus === 'Reviewed'&& item.isAccepted==='false'"
                              @click="showConfirmDialog(item)"
                            >Confirm</v-btn
                            >
                            </v-col>
                          </v-container>
                        </div>
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
                      Page {{ page }} of {{ numberOfPages }}
                    </span>
                    <v-btn
                      fab
                      dark
                      color="#455A64"
                      class="mr-1"
                      @click="formerPage"
                      x-small
                    >
                      <v-icon>mdi-chevron-left</v-icon>
                    </v-btn>
                    <v-btn
                      fab
                      dark
                      color="#455A64"
                      class="ml-1"
                      @click="nextPage"
                      x-small
                    >
                      <v-icon>mdi-chevron-right</v-icon>
                    </v-btn>
                  </v-row>
                </template>
              </v-data-iterator>
              <v-dialog v-model="modifyDialog" max-width="600">
                <v-card class="pa-8">
                  <h4
                    class="mb-8"
                    style="font-size: 1.35rem;letter-spacing:.0125em;color:#272727;"
                  >
                    Author Information
                  </h4>
                  <v-card-text>
                    <v-form
                      ref="modifyForm"
                      v-model="modifyForm.valid"
                      lazy-validation
                      class="pl-4 pt-3 pr-4 pb-4 "
                    >
                      <v-text-field
                        v-model="modifyForm.articleName"
                        :rules="modifyForm.articleNameRules"
                        label="Article Name"
                        required
                      ></v-text-field>
                      <v-row>
                        <v-col>
                          <p style="font-size: 16px;float: left">Add author:</p>
                          <v-btn
                            fab
                            small
                            outlined
                            @click="showEmptyAuthorDialog()"
                          >
                            <v-icon dark>mdi-plus</v-icon>
                          </v-btn>
                        </v-col>
                        <v-col>
                          <vuedraggable
                            class="wrapper"
                            v-model="modifyForm.author"
                          >
                            <v-row
                              v-for="(author, index) in this.modifyForm.author"
                              :key="index"
                            >
                              <v-card
                                class="mx-auto"
                                max-width="300"
                                outlined
                                style="border-radius:5px;width: 100%"
                              >
                                <div
                                  class="overline pl-4 pt-2"
                                  style="height:20px;text-align:left;"
                                >
                                  <v-icon class="mr-2" small
                                    >mdi-account-circle</v-icon
                                  >AUTHOR INFORMATION
                                </div>
                                <v-list flat>
                                  <v-list-item-group color="primary">
                                    <v-list-item>
                                      <v-list-item-content
                                        style="text-align:left"
                                      >
                                        <v-list-item-title
                                          v-text="'Name: ' + author.name"
                                        ></v-list-item-title>
                                        <v-list-item-title
                                          v-text="'Area: ' + author.area"
                                        ></v-list-item-title>
                                        <v-list-item-title
                                          v-text="'Company: ' + author.company"
                                        ></v-list-item-title>
                                        <v-list-item-title
                                          v-text="'Email: ' + author.email"
                                        ></v-list-item-title>
                                      </v-list-item-content>
                                    </v-list-item>
                                    <v-btn
                                      text
                                      small
                                      color="blue darken-4"
                                      @click="showAuthorDialog(author, index)"
                                      >Modify</v-btn
                                    >
                                    <v-btn
                                      text
                                      color="error"
                                      small
                                      @click="del(index)"
                                      >Delete</v-btn
                                    >
                                  </v-list-item-group>
                                </v-list>
                              </v-card>
                            </v-row>
                          </vuedraggable>
                        </v-col>
                      </v-row>
                      <v-textarea
                        v-model="modifyForm.summary"
                        :rules="modifyForm.summaryRules"
                        outlined
                        counter
                        auto-grow
                        label="Summary"
                        rows="2"
                        row-height="20"
                        required
                      ></v-textarea>
                      <v-card outlined class="pa-4">
                        <v-list-item-title style="text-align: left"
                          >Choose at least one topic</v-list-item-title
                        >
                        <v-checkbox
                          v-for="topic in this.topics"
                          :key="topic.topic"
                          :label="topic.topic"
                          :value="topic.topic"
                          v-model="modifyForm.topic"
                          :rules="modifyForm.topicsRule"
                        ></v-checkbox>
                      </v-card>
                      <template>
                        <v-file-input
                          v-model="modifyForm.file"
                          :rules="modifyForm.fileRules"
                          accept=".pdf"
                          label="File input"
                        >
                        </v-file-input>
                      </template>
                      <v-btn
                        text
                        :disabled="!modifyForm.valid"
                        style="color:#4CAF50;"
                        class="mr-4 mb-8 mt-8"
                        @click="contribution"
                      >
                        Apply
                      </v-btn>
                      <v-btn
                        text
                        style="color:#F44336;"
                        class="mr-4 mb-8 mt-8"
                        @click="reset"
                      >
                        Reset Form
                      </v-btn>
                    </v-form>
                  </v-card-text>
                </v-card>
                <v-dialog v-model="authorDialog" max-width="600">
                  <v-card class="pa-8">
                    <h4
                      class="mb-8"
                      style="font-size: 1.35rem;letter-spacing:.0125em;color:#272727;"
                    >
                      Author Information
                    </h4>
                    <v-card-text>
                      <v-form
                        ref="authorForm"
                        v-model="authorForm.valid"
                        lazy-validation
                      >
                        <v-text-field
                          label="Name"
                          v-model="authorForm.name"
                          :rules="authorForm.nameRules"
                          required
                        ></v-text-field>
                        <v-text-field
                          label="Email"
                          v-model="authorForm.email"
                          :rules="authorForm.emailRules"
                          required
                        ></v-text-field>
                        <v-text-field
                          label="Company"
                          v-model="authorForm.company"
                          :rules="authorForm.companyRules"
                          required
                        ></v-text-field>
                        <v-text-field
                          label="Area"
                          v-model="authorForm.area"
                          :rules="authorForm.areaRules"
                          required
                        ></v-text-field>
                      </v-form>
                    </v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn
                        color="blue darken-1"
                        text
                        @click="authorDialog = false"
                        >Close</v-btn
                      >
                      <v-btn color="blue darken-1" text @click="add"
                        >Save
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
                <v-dialog v-model="authorModifyDialog" max-width="600">
                  <v-card class="pa-8">
                    <h4
                      class="mb-8"
                      style="font-family:Georgia;
                            font-size: 1.35rem;letter-spacing:.0125em;color:#272727;"
                    >
                      Author Information
                    </h4>
                    <v-card-text>
                      <v-form
                        ref="authorForm"
                        v-model="authorForm.valid"
                        lazy-validation
                      >
                        <v-text-field
                          label="Name"
                          v-model="authorForm.name"
                          :rules="authorForm.nameRules"
                          required
                        ></v-text-field>
                        <v-text-field
                          label="Email"
                          v-model="authorForm.email"
                          :rules="authorForm.emailRules"
                          required
                        ></v-text-field>
                        <v-text-field
                          label="Company"
                          v-model="authorForm.company"
                          :rules="authorForm.companyRules"
                          required
                        ></v-text-field>
                        <v-text-field
                          label="Area"
                          v-model="authorForm.area"
                          :rules="authorForm.areaRules"
                          required
                        ></v-text-field>
                      </v-form>
                    </v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn
                        color="blue darken-1"
                        text
                        @click="authorDialog = false"
                        >Close</v-btn
                      >
                      <v-btn color="blue darken-1" text @click="modifyAuthor()"
                        >Save
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-dialog>
              <v-dialog v-model="scoresDialog" max-width="500">
                <v-sheet class="overflow-y-auto" fluid>
                  <v-container fluid class="pa-8">
                    <template>
                      <h4
                        class="mb-6"
                        style="font-family:Times New Roman;
                          font-size: 1.45rem;letter-spacing:.0125em;color:#272727;"
                      >
                        View Scores
                      </h4>
                      <v-container
                        fluid
                        v-for="(score, index) in item.scores"
                        :key="index"
                        ><hr />
                        <v-simple-table>
                          <thead
                            style="float: left;font-family:Times New Roman;font-size:20px;"
                          >
                            PC{{
                              index
                            }}
                          </thead>
                          <tbody class="text-left justify-center">
                            <tr>
                              <td
                                class="text-capitalize"
                                style="font-family:Times New Roman;font-size:18px;width: 30%"
                              >
                                Score
                              </td>
                              <td
                                style="font-family:Times New Roman;font-size:18px;word-break: break-all"
                              >
                                {{ score.score }}
                              </td>
                            </tr>
                            <tr>
                              <td
                                class="text-capitalize"
                                style="font-family:Times New Roman;font-size:18px;width: 30%"
                              >
                                Comment
                              </td>
                              <td
                                style="font-family:Times New Roman;font-size:18px;word-break: break-all"
                              >
                                {{ score.comment }}
                              </td>
                            </tr>
                            <tr>
                              <td
                                class="text-capitalize"
                                style="font-family:Times New Roman;font-size:18px;width: 30%"
                              >
                                Confidence
                              </td>
                              <td
                                style="font-family:Times New Roman;font-size:18px;word-break: break-all"
                              >
                                {{ score.confidence }}
                              </td>
                            </tr>
                          </tbody>
                        </v-simple-table>
                      </v-container></template
                    >
                  </v-container>
                </v-sheet>
              </v-dialog>
              <v-dialog v-model="rebuttalDialog" max-width="500">
                <v-card class="pa-8">
                  <h4
                    class="mb-8"
                    style="font-family:Georgia;
                            font-size: 1.35rem;letter-spacing:.0125em;color:#272727;"
                  >
                    Rebuttal
                  </h4>
                  <v-card-text>
                    <v-form
                      ref="authorForm"
                      v-model="rebuttalForm.valid"
                      lazy-validation
                    >
                      <v-textarea
                        v-model="rebuttalForm.reason"
                        :rules="rebuttalForm.reasonRules"
                        outlined
                        counter
                        auto-grow
                        label="Your reason"
                        rows="2"
                        row-height="20"
                        required
                      ></v-textarea>
                      <v-btn
                        text
                        :disabled="!rebuttalForm.valid"
                        style="color:#4CAF50;"
                        class="mr-4 mb-8 mt-8"
                        @click="rebuttal"
                      >
                        Apply
                      </v-btn>
                    </v-form>
                  </v-card-text>
                </v-card>
              </v-dialog>
              <v-dialog v-model="confirmDialog" max-width="500">
                <v-card class="pa-8">
                  <h4
                    class="mb-8"
                    style="font-family:Georgia;
                            font-size: 1.35rem;letter-spacing:.0125em;color:#272727;"
                  >
                    Confirm the result?
                  </h4>
                    <v-btn text @click="submitConfirm" style="color:green;">Confirm</v-btn>
                    <v-btn text @click="confirmDialog=false" style="color:gray;">Close</v-btn>
                </v-card>
              </v-dialog>
            </v-container>
          </template>
        </v-content>
      </v-container>
    </v-sheet>
  </v-card>
</template>

<script>
import vuedraggable from "vuedraggable";

export default {
  name: "Author",
  components: { vuedraggable },
  data() {
    return {
      page: 1,
      author: {
        name: "",
        email: "",
        company: "",
        area: ""
      },
      itemsPerPage: 3,
      sortBy: "articleName",
      sortDesc: false,
      user: "",
      token: "",
      openOnHover: true,
      logOutDialog: false,
      chooseIdDialog: false,
      modifyDialog: false,
      scoresDialog: false,
      rebuttalDialog:false,
      confirmDialog:false,
      authorModifyDialog: false,
      index: 0,
      topics: [],
      list: [],
      // pcmember:{id:'',username:'',fullName:'', e_mail:'', companie:'', area:''} ,
      meetingId: this.$route.query.confID,
      dialogVisible: false,
      authorDialog: false,
      item: {},
      modifyForm: {
        articleName: "",
        author: [],
        summary: "",
        topics: [],
        topic: [],
        file: [],
        meetingId: "",
        identity: "",
        articleNameRules: [
          v => !!v || "The article name cannot be empty",
          v => v.length <= 50 || "Max 50 characters"
        ],
        authorRules: [v => !!v || "The author name cannot be empty"],
        summaryRules: [
          v => !!v || "The summary cannot be empty",
          v => v.length <= 800 || "Max 800 characters"
        ],
        topicsRule: [
          v => {
            if (this.modifyForm.topic.length === 0)
              return "There must be at least one topic";
            else return true;
          }
        ],
        fileRules: [
          v => {
            if (this.modifyForm.file.length === 0)
              return "The file cannot be empty";
            else return true;
          }
        ]
      },
      authorForm: {
        name: "",
        email: "",
        company: "",
        area: "",
        nameRules: [v => !!v || "The name cannot be empty"],
        emailRules: [
          v => !!v || "The e-mail address cannot be empty",
          v =>
            /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(v) ||
            "Please enter the correct email address"
        ],
        companyRules: [v => !!v || "The company cannot be empty"],
        areaRules: [v => !!v || "The area cannot be empty"]
      },
      rebuttalForm:{
        reason:'',
        reasonRules: [
          v => !!v || "The reason cannot be empty",
        ],
      },
      keys: ["articleName", "authorName", "summary"],
      tags: []
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
    showModifyDialog(item) {
      let data = Object.assign([], JSON.parse(JSON.stringify(item)));
      this.item = data;
      this.modifyForm.articleName = item.articleName;
      this.modifyForm.summary = item.summary;
      this.modifyForm.author = item.authors;
      this.modifyForm.topics = item.topics;
      this.modifyForm.topic = [];
      for (var i = 0; i < item.topics.length; i++) {
        this.modifyForm.topic.push(item.topics[i].topic);
      }
      this.modifyForm.file = item.filePath;
      let authors = Object.assign([], JSON.parse(JSON.stringify(item.authors)));
      // console.log(authors)
      for (let j = 0; j < authors.length; j++) {
        // console.log(j)
        const val = authors[j].name;
        this.tags.push(val);
      }
      // console.log(this.tags);
      this.modifyDialog = true;
    },
    showScoresDialog(item) {
      let data = Object.assign([], JSON.parse(JSON.stringify(item)));
      this.item = data;
      this.scoresDialog = true;
    },
    showRebuttalDialog(item){
      let data = Object.assign([], JSON.parse(JSON.stringify(item)));
      this.item = data;
      this.rebuttalDialog=true;
    },
    showConfirmDialog(item){
      let data = Object.assign([], JSON.parse(JSON.stringify(item)));
      this.item = data;
      this.confirmDialog=true;
    },
    showEmptyAuthorDialog() {
      this.authorForm.name = "";
      this.authorForm.area = "";
      this.authorForm.company = "";
      this.authorForm.email = "";
      this.authorDialog = true;
    },
    showAuthorDialog(author, index) {
      this.index = index;
      this.authorForm.name = author.name;
      this.authorForm.area = author.area;
      this.authorForm.company = author.company;
      this.authorForm.email = author.email;
      this.authorModifyDialog = true;
    },
    reset() {},
    del(index) {
      this.modifyForm.author.splice(index, 1);
      // this.tags.splice(index, 1)
      // this.$emit('input', this.tags)
    },
    modifyAuthor() {
      var different = false;
      for (var i = 0; i < this.modifyForm.author.length; i++) {
        if (
          this.index !== i &&
          this.authorForm.name === this.modifyForm.author[i].name
        ) {
          // console.log("same"+i);
          different = true;
          this.$message({
            message: "The name cannot be the same!",
            type: "error"
          });
        }
      }
      if (different === false) {
        // console.log(this.modifyForm.author);
        this.modifyForm.author[this.index].name = this.authorForm.name;
        this.modifyForm.author[this.index].email = this.authorForm.email;
        this.modifyForm.author[this.index].area = this.authorForm.area;
        this.modifyForm.author[this.index].company = this.authorForm.company;
        this.$refs.authorForm.reset();
        this.authorModifyDialog = false;
      }
    },
    add() {
      var different = false;
      console.log(this.modifyForm.author);
      for (var i = 0; i < this.modifyForm.author.length; i++) {
        if (this.authorForm.name === this.modifyForm.author[i].name) {
          // console.log("same"+i)
          different = true;
          this.$message({
            message: "You should add a different author!",
            type: "error"
          });
        }
      }
      if (different === false) {
        this.author.name = this.authorForm.name;
        this.author.email = this.authorForm.email;
        this.author.area = this.authorForm.area;
        this.author.company = this.authorForm.company;
        let data = Object.assign({}, JSON.parse(JSON.stringify(this.author)));
        this.modifyForm.author.push(data);
        const val = this.authorForm.name;
        if (!val) return;
        this.$refs.authorForm.reset();
        this.authorDialog = false;
      }
      // console.log(this.tags)
    },
    rebuttal(){
      this.$axios
        .post("http://114.116.136.180/authorRebuttal", {
          reason:this.rebuttalForm.reason,
          username: JSON.parse(localStorage.getItem("userDetails")).username,
          meetingId:this.meetingId,
          articleId: this.item.id,
          isRebuttaled:true
        })
        .then(resp => {
          if (resp.status === 200 && resp.data) {
            this.loadData();
            this.$message({
              message: "Rebuttal successfully !",
              type: "success"
            });
          } else {
            this.$message({
              message: "Application error !",
              type: "error",
              duration: 2000
            });
          }
        })
        .catch(error => {
          this.$message({
            message: "Application error !",
            type: "error",
            duration: 2000
          });
        });
      this.rebuttalDialog=false;
      this.rebuttalForm.reason='';
    },
    submitConfirm(){
      this.$axios
        .post("http://114.116.136.180/authorRebuttal", {
          reason:this.rebuttalForm.reason,
          username: JSON.parse(localStorage.getItem("userDetails")).username,
          meetingId:this.meetingId,
          articleId: this.item.id,
          isRebuttaled:false
        })
        .then(resp => {
          if (resp.status === 200 && resp.data) {
            this.loadData();
            this.$message({
              message: "Confirm successfully !",
              type: "success"
            });
          } else {
            this.$message({
              message: "Application error !",
              type: "error",
              duration: 2000
            });
          }
        })
        .catch(error => {
          this.$message({
            message: "Application error !",
            type: "error",
            duration: 2000
          });
        });
      this.confirmDialog=false;
    },
    contribution() {
      console.log(this.modifyForm.topic);
      // console.log(this.modifyForm)
      if (
        this.modifyForm.author == null ||
        this.modifyForm.articleName === "" ||
        this.modifyForm.topic == null
      ) {
        this.$message({
          message: "Please fill out the form !",
          type: "error"
        });
      } else if (this.modifyForm.file == null) {
        this.$message({
          message: "The file cannot be empty !",
          type: "error"
        });
      } else {
        // this.topics=this.topic;
        console.log(this.modifyForm);
        for (var i = 0; i < this.modifyForm.author.length; i++) {
          let data = Object.assign(
            {},
            JSON.parse(JSON.stringify(this.modifyForm.author[i]))
          );
          this.author.name = data.name;
          this.author.email = data.email;
          this.author.area = data.area;
          this.author.company = data.company;
          let tmp = Object.assign({}, JSON.parse(JSON.stringify(this.author)));
          this.modifyForm.author[i] = tmp;
        }
        let fd = new FormData();
        fd.append("templateFile", this.modifyForm.file);
        fd.append("articleName", this.modifyForm.articleName);
        fd.append("author", JSON.stringify(this.modifyForm.author));
        fd.append("summary", this.modifyForm.summary);
        fd.append("topics", this.modifyForm.topic);
        fd.append(
          "username",
          JSON.parse(localStorage.getItem("userDetails")).username
        );
        fd.append("meetingId", this.meetingId);
        fd.append("articleId", this.item.id);
        // console.log(this.meetingId);
        let config = {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        };
        this.$axios
          .post("http://114.116.136.180/modifyAuthor", fd, config)
          .then(resp => {
            if (resp.status === 200 && resp.data) {
              //this.$store.commit('contribution', rsp.data);

              // this.$router.replace({path: '/allMeetings'});
              this.loadData();
              this.$message({
                message: "Contribution successfully !",
                type: "success"
              });
            } else {
              this.$message({
                message: "Application error !",
                type: "error",
                duration: 2000
              });
            }
          })
          .catch(error => {
            this.$message({
              message: "Application error !",
              type: "error",
              duration: 2000
            });
          });
      }
      this.modifyDialog = false;
      this.loadData();
    },
    loadData: function() {
      ////////////////////////////////////////////////////////////
      var self = this;
      this.$axios
        .get("http://114.116.136.180/author", {
          params: {
            //用户参数
            username: JSON.parse(localStorage.getItem("userDetails")).username,
            meetingId: this.meetingId
          }
        })
        .then(function(response) {
          var data = response.data;
          // console.log(data);
          self.topics = JSON.parse(JSON.stringify(data.topicDetails));
          self.list = JSON.parse(JSON.stringify(data.articleDetails));
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    more: function(item) {
      this.dialogVisible = true;
      this.item = item;
    },
    close: function() {
      this.dialogVisible = false;
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
    /////////////////////////////////////////////////////////////
    // 页面加载执行方法
    this.loadData();
    this.meetingId = this.$route.query.confID;
    // console.log("this meeting:" + this.meetingId);
    // this.meetingId =1;
  }
};
</script>

<style>
::-webkit-scrollbar {
  width: 0;
}
</style>
