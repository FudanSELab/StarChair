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
    <v-sheet
      id="scrolling-techniques"
      class="overflow-y-auto"
      fluid
      color="#ECEFF1"
    >
      <v-container fluid style="padding:4% 6%;">
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
          <v-card
            outlined
            width="58%"
            class="mx-auto"
            style=" border-radius:10px;"
          >
            <v-card-text
              class="mt-8"
              style="font-family:Georgia;font-size: 1.25rem;font-weight:bold;
              letter-spacing:.0125em;line-height: 2rem;color:#272727"
              >CONTRIBUTE
            </v-card-text>

            <v-form
              ref="contributionForm"
              v-model="contributionForm.valid"
              lazy-validation
              class="pl-10 pt-3 pr-10 pb-4 ml-10 mr-10"
            >
              <v-text-field
                v-model="contributionForm.articleName"
                :rules="contributionForm.articleNameRules"
                label="Article Name"
                required
              ></v-text-field>
              <v-row>
                <v-col>
                  <p style="font-size: 16px;float: left">Add author:</p>
                  <v-btn fab small outlined @click="showEmptyAuthorDialog">
                    <v-icon dark>mdi-plus</v-icon>
                  </v-btn>
                </v-col>
                <v-col>
                  <vuedraggable
                    class="wrapper"
                    v-model="contributionForm.author"
                  >
                    <v-row
                      v-for="(author, index) in this.contributionForm.author"
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
                          <v-icon class="mr-2" small>mdi-account-circle</v-icon
                          >AUTHOR INFORMATION
                        </div>
                        
                        <v-list flat>
                          <v-list-item-group color="primary">
                            <v-list-item>
                              <v-list-item-content style="text-align:left">
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
                            <v-card-actions class="pt-0">
                              <v-btn
                                text
                                small
                                color="blue darken-4"
                                @click="showAuthorDialog(author, index)"
                                >Modify</v-btn
                              >
                              <v-btn text color="error" @click="del(index)" small
                                >Delete</v-btn
                              >
                            </v-card-actions>
                          </v-list-item-group>
                        </v-list>
                      </v-card>
                    </v-row>
                  </vuedraggable>
                </v-col>
              </v-row>
              <v-textarea
                v-model="contributionForm.summary"
                :rules="contributionForm.summaryRules"
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
                  :key="topic"
                  :label="topic"
                  :value="topic"
                  v-model="contributionForm.topics"
                  :rules="contributionForm.topicsRule"
                ></v-checkbox>
              </v-card>
              <template>
                <v-file-input
                  v-model="contributionForm.file"
                  :rules="contributionForm.fileRules"
                  accept=".pdf"
                  label="File input"
                >
                </v-file-input>
              </template>
              <v-btn
                text
                :disabled="!contributionForm.valid"
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
            <v-dialog v-model="authorDialog" max-width="600">
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
                  <v-btn color="blue darken-1" text @click="add">Save </v-btn>
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
                  <v-btn color="blue darken-1" text @click="modifyAuthor()"
                    >Save
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-card>
        </v-content>
      </v-container>
    </v-sheet>
  </v-card>
</template>

<script>
import vuedraggable from "vuedraggable";

export default {
  name: "contribution",
  components: { vuedraggable },
  props: {
    value: Array,
    required: true,
    default: () => []
  },
  data() {
    return {
      user: "",
      token: "",
      openOnHover: true,
      logOutDialog: false,
      authorDialog: false,
      authorModifyDialog: false,
      index: 0,
      contributionForm: {
        articleName: "",
        author: [],
        summary: "",
        topics: [],
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
            if (this.contributionForm.topics.length === 0)
              return "There must be at least one topic";
            else return true;
          }
        ],
        fileRules: [
          v => {
            if (this.contributionForm.file.length === 0)
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
      author: {
        name: "",
        email: "",
        company: "",
        area: ""
      },
      fileList: [],
      topics: [],
      loading: false,
      tags: [],
      current: ""
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
    // showAuthorDialog() {
    //   console.log(this.tags)
    //   this.authorDialog = true;
    // },
    reset() {
      this.$refs.contributionForm.reset();
    },
    add() {
      console.log("???????????");
      var different = false;
      console.log(this.contributionForm.author.length);
      for (var i = 0; i < this.contributionForm.author.length; i++) {
        if (this.authorForm.name === this.contributionForm.author[i].name) {
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
        this.contributionForm.author.push(data);
        const val = this.authorForm.name;
        if (!val) return;
        this.$refs.authorForm.reset();
        this.authorDialog = false;
      }
      // console.log(this.tags)
    },
    // delTag(index) {
    //   this.contributionForm.author.splice(index, 1)
    //   this.tags.splice(index, 1)
    //   this.$emit('input', this.tags)
    // },
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
    del(index) {
      this.contributionForm.author.splice(index, 1);
      // this.tags.splice(index, 1)
      // this.$emit('input', this.tags)
    },
    modifyAuthor() {
      var different = false;
      for (var i = 0; i < this.contributionForm.author.length; i++) {
        if (
          this.index !== i &&
          this.authorForm.name === this.contributionForm.author[i].name
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
        this.contributionForm.author[this.index].name = this.authorForm.name;
        this.contributionForm.author[this.index].email = this.authorForm.email;
        this.contributionForm.author[this.index].area = this.authorForm.area;
        this.contributionForm.author[
          this.index
        ].company = this.authorForm.company;
        this.$refs.authorForm.reset();
        this.authorModifyDialog = false;
      }
    },
    contribution() {
      console.log(this.contributionForm.file);
      if (
        this.contributionForm.author == null ||
        this.contributionForm.articleName === "" ||
        this.topics == null
      ) {
        this.$message({
          message: "Please fill out the form !",
          type: "error"
        });
      } else if (this.contributionForm.file == null) {
        this.$message({
          message: "The file cannot be empty !",
          type: "error"
        });
      } else {
        let tmp = Object.assign(
          [],
          JSON.parse(JSON.stringify(this.contributionForm.author))
        );
        let copyAuthor = tmp;
        for (var i = 0; i < this.tags.length; i++) {
          for (var j = 0; j < copyAuthor.length; j++) {
            let tmp1 = Object.assign(
              {},
              JSON.parse(JSON.stringify(copyAuthor[j]))
            );
            if (copyAuthor[j].name === this.tags[i]) {
              this.contributionForm.author[i] = tmp1;
            }
            // console.log("copyAj:"+copyAuthor[j].name)
            // console.log("tagi:"+this.tags[i])
          }
        }
        // console.log(this.contributionForm.author);
        let fd = new FormData();
        fd.append("templateFile", this.contributionForm.file);
        fd.append("articleName", this.contributionForm.articleName);
        fd.append("author", JSON.stringify(this.contributionForm.author));
        fd.append("summary", this.contributionForm.summary);
        fd.append("topics", this.contributionForm.topics);
        fd.append(
          "username",
          JSON.parse(localStorage.getItem("userDetails")).username
        );
        fd.append("meetingId", this.meetingId);
        console.log(this.meetingId);
        let config = {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        };
        this.$axios
          .post("http://114.116.136.180/contribution", fd, config)
          .then(resp => {
            if (resp.status === 200 && resp.data) {
              //this.$store.commit('contribution', resp.data);

              this.$router.replace({ path: "/allMeetings" });
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
    loadData() {}
  },

  mounted: function() {
    // 页面加载执行方法
    this.loadData();
    this.meetingId = this.$route.query.meetingId;
    var topics = this.$route.query.topicList;
    this.topics = topics.split("\\");
    this.topics.length--;
  }
};
</script>

<style>
::-webkit-scrollbar {
  width: 0;
}
</style>
