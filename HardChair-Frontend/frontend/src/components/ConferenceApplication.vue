<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <v-card class="overflow-hidden">
    <v-app-bar flat dark height="60" style="border-radius:0px;" color="#263238">
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
              <v-list-item to="allMeetings">
                <v-list-item-title class="text-left"
                  >All Conferences</v-list-item-title
                >
              </v-list-item>
              <v-list-item to="UserConf">
                <v-list-item-title class="text-left"
                  >Attended Conference</v-list-item-title
                >
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
              >APPLY NEW CONFERENCE</v-card-text
            >
            <v-card-text class="font-italic"
              >After the submission, the administrator will audit your
              application. <br />
              You can check the status in ' CONFERENCE → My Application
              '</v-card-text
            >
            <v-form
              ref="applicationform"
              v-model="applicationform.valid"
              lazy-validation
              class="pl-10 pt-3 pr-10 pb-4 ml-10 mr-10"
            >
              <v-text-field
                v-model="applicationform.fullName"
                :rules="applicationform.fullNameRules"
                label="Full Name"
                required
              ></v-text-field>
              <v-text-field
                v-model="applicationform.shortenForm"
                :rules="applicationform.shortenFormRules"
                class="pt-4"
                label="Shorten Form"
                required
              ></v-text-field>
              <v-text-field
                v-model="applicationform.location"
                :rules="applicationform.locationRules"
                class="pt-4"
                label="Location"
                required
              ></v-text-field>
              <v-menu
                v-model="applicationform.ddlMenu"
                :close-on-content-click="false"
                :nudge-right="40"
                transition="scale-transition"
                offset-y
                min-width="290px"
              >
                <template v-slot:activator="{ on }">
                  <v-text-field
                    v-model="applicationform.ddl"
                    :rules="applicationform.ddlRules"
                    class="pt-4"
                    label="Deadline"
                    required
                    readonly
                    v-on="on"
                  ></v-text-field>
                </template>
                <v-date-picker
                  v-model="applicationform.ddl"
                  color="#37474F"
                  :min="applicationform.currentDate"
                  :max="subTimePublish"
                  @input="applicationform.ddlMenu = false"
                ></v-date-picker>
              </v-menu>
              <v-menu
                v-model="applicationform.publishingTimeMenu"
                :close-on-content-click="false"
                :nudge-right="40"
                transition="scale-transition"
                offset-y
                min-width="290px"
              >
                <template v-slot:activator="{ on }">
                  <v-text-field
                    v-model="applicationform.publishingTime"
                    :rules="applicationform.publishingTimeRules"
                    class="pt-4"
                    label="Publishing Time"
                    required
                    readonly
                    v-on="on"
                  ></v-text-field>
                </template>
                <v-date-picker
                  v-model="applicationform.publishingTime"
                  color="#37474F"
                  :min="plusTimeDdl"
                  :max="subTime"
                  :allowed-dates="allowedDates"
                  @input="applicationform.publishingTimeMenu = false"
                ></v-date-picker>
              </v-menu>
              <v-menu
                v-model="applicationform.timeMenu"
                :close-on-content-click="false"
                :nudge-right="40"
                transition="scale-transition"
                offset-y
                min-width="290px"
              >
                <template v-slot:activator="{ on }">
                  <v-text-field
                    v-model="applicationform.time"
                    :rules="applicationform.timeRules"
                    class="pt-4"
                    label="Time"
                    required
                    readonly
                    v-on="on"
                  ></v-text-field>
                </template>
                <v-date-picker
                  v-model="applicationform.time"
                  color="#37474F"
                  :min="plusTimePublish"
                  @input="applicationform.timeMenu = false"
                ></v-date-picker>
              </v-menu>
              <v-card outlined>
                <v-row class="muli-tags">
                  <v-col>
                    <v-list-item-title style="color: #8c8c8c"
                      >Enter topics:</v-list-item-title
                    >
                    <v-text-field
                      :rules="TagsRules"
                      label="Enter the topic, press 'Enter'"
                      ref="input"
                      @keyup.enter="add"
                      @keydown.188="split"
                      v-model="current"
                      class="ml-8"
                    ></v-text-field>
                  </v-col>
                  <v-col>
                    <v-list-item-title style="color: #8c8c8c"
                      >Topics:</v-list-item-title
                    >
                    <v-btn
                      class="btn"
                      depressed
                      small
                      rounded
                      v-for="(tag, index) in tags"
                      :key="index"
                      @click="delTag(index)"
                      >{{ tag }} <v-icon dark>mdi-minus</v-icon></v-btn
                    ></v-col
                  >
                </v-row></v-card
              >
              <v-btn
                text
                :disabled="!applicationform.valid"
                style="color:#4CAF50;"
                class="mr-4 mb-8 mt-8"
                @click="validateApplicationform"
              >
                Apply
              </v-btn>
              <v-btn
                text
                style="color:#F44336;"
                class="mr-4 mb-8 mt-8"
                @click="resetApplicationform"
              >
                Reset Form
              </v-btn>
            </v-form>
          </v-card>
        </v-content>
      </v-container>
    </v-sheet>
  </v-card>
</template>

<script>
export default {
  name: "conferenceApplication",
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
      reload: true,
      subTimePublish: "",
      plusTimeDdl: "",
      subTime: "",
      plusTimePublish: "",
      applicationform: {
        valid: false,
        fullName: "",
        shortenForm: "",
        timeMenu: false,
        ddlMenu: false,
        currentDate: new Date().toISOString().substr(0, 10),
        location: "",
        ddl: "",
        publishingTime: "",
        time: "",
        applicant: "",
        fullNameRules: [v => !!v || "The full name cannot be empty"],
        shortenFormRules: [v => !!v || "The shorten form cannot be empty"],
        timeRules: [v => !!v || "The time cannot be empty"],
        locationRules: [v => !!v || "The location cannot be empty"],
        ddlRules: [v => !!v || "The deadline cannot be empty"],
        publishingTimeRules: [v => !!v || "The publishing time cannot be empty"]
      },
      tags: [],
      TagsRules: [v => this.tags.length > 0 || "There must be at least one"],
      current: ""
    };
  },
  computed: {
    ddl() {
      return this.applicationform.ddl;
    },
    publishingTime() {
      return this.applicationform.publishingTime;
    },
    time() {
      return this.applicationform.time;
    }
  },
  watch: {
    ddl: function() {
      if(!this.applicationform.ddl){
        this.applicationform.ddl = new Date().toISOString().substring(0, 10)
      }
      var date1 =
        new Date(this.applicationform.ddl).getTime() + 1000 * 3600 * 24;
      var result1 = new Date(date1);
      this.plusTimeDdl = result1.toISOString().substring(0, 10);
    },
    publishingTime: function() {
      if(!this.applicationform.publishingTime){
        this.applicationform.publishingTime = new Date().toISOString().substring(0, 10)
      }
      var date =
        new Date(this.applicationform.publishingTime).getTime() -
        1000 * 3600 * 24;
      var result = new Date(date);
      this.subTimePublish = result.toISOString().substring(0, 10);
      var date2 =
        new Date(this.applicationform.publishingTime).getTime() +
        1000 * 3600 * 24;
      var result2 = new Date(date2);
      this.plusTimePublish = result2.toISOString().substring(0, 10);
    },
    time: function() {
      if(!this.applicationform.time){
        this.applicationform.time = new Date().toISOString().substring(0, 10)
      }
      var date2 =
        new Date(this.applicationform.time).getTime() - 1000 * 3600 * 24;
      var result2 = new Date(date2);
      this.subTime = result2.toISOString().substring(0, 10);
    }
  },
  created() {
    this.handleUserData(); 
    this.handleToken();
  },
  methods: {
    allowedDates: val => Date.parse(val) > Date.now() - 8.64e7,
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
    focus() {
      this.$refs.input.focus();
    },
    split(e) {
      e.preventDefault();
      this.add(e);
    },
    add(e) {
      const val = e.target.value;
      if (!val) return;
      if (this.tags.indexOf(val) > -1) return;
      this.tags.push(val);
      this.$emit("input", this.tags);
      this.current = "";
    },
    del(e) {
      if (!e.target.value.length) {
        this.tags.pop();
        this.$emit("input", this.tags);
      }
    },
    delTag(index) {
      this.tags.splice(index, 1);
      this.$emit("input", this.tags);
    },
    validateApplicationform() {
      if (this.$refs.applicationform.validate()) {
        this.$axios
          .post("http://114.116.136.180/applyConference", {
            fullName: this.applicationform.fullName,
            shortenForm: this.applicationform.shortenForm,
            time: this.applicationform.time,
            location: this.applicationform.location,
            ddl: this.applicationform.ddl,
            publishingTime: this.applicationform.publishingTime,
            topic: this.tags,
            applicant: JSON.parse(localStorage.getItem("userDetails")).username
          })
          .then(resp => {
            if (resp.status == 200 && resp.data.hasOwnProperty("id")) {
              this.$message({
                message: "successful application !",
                type: "success",
                duration: 2000
              });
              this.$router.replace("/MyApplication");
            } else {
              this.$message({
                message: "Application error !",
                type: "error",
                duration: 2000
              });
            }
          })
          .catch(error => {
            console.log(error);
            this.$message({
              message: "Application error !",
              type: "error",
              duration: 2000
            });
          });
      }
    },
    resetApplicationform() {
      this.$refs.applicationform.reset();
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
  }
};
</script>

<style>
::-webkit-scrollbar {
  width: 0px;
}
</style>
