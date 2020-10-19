<template>
  <div>
    <div style="height:55px;">
      <h4
        v-text="title"
        class="ml-6 mt-3 float-left"
        style="font-family:Georgia;font-size:22px;color:#272727;"
      ></h4>
      <div v-if="token != null" class="float-right mt-2 mr-4">
        <template>
          <div class="text-center" data-app="true">
            <v-menu :open-on-hover="openOnHover" offset-y>
              <template v-slot:activator="{ on }">
                <v-btn text v-on="on">{{ user }}</v-btn>
              </template>
              <v-list>
                <v-list-item @click="showLogOutDialog">
                  <v-list-item-title>Log out</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
        </template>
      </div>
      <div v-if="token == null" class="float-right mt-2 mr-4">
        <div data-app="true">
          <v-btn text large @click="showLogInDialog">Login / Register</v-btn>
        </div>
      </div>
      <v-dialog v-model="loginDialog" max-width="500">
        <v-card>
          <v-tabs
            v-model="tab"
            centered
            icons-and-text
            background-color="#BCAAA4"
          >
            <v-tabs-slider style="color:#827717;"></v-tabs-slider>
            <v-tab
              class="ml-4 black--text"
              href="#tab-1"
              style="text-decoration:none;"
            >
              Log in<v-icon>mdi-account-box</v-icon>
            </v-tab>
            <v-tab
              class="black--text"
              href="#tab-2"
              style="text-decoration:none;"
            >
              Sign up<v-icon>mdi-heart</v-icon>
            </v-tab>
          </v-tabs>
          <v-tabs-items v-model="tab">
            <v-tab-item :value="'tab-1'">
              <template>
                <v-form
                  ref="loginform"
                  v-model="loginform.valid"
                  lazy-validation
                  class="pl-4 pt-3 pr-4 pb-4 ml-2 mr-2 mt-2"
                >
                  <v-text-field
                    v-model="loginform.username"
                    :rules="loginform.nameRules"
                    label="Username"
                    style="color:red;"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="loginform.password"
                    :rules="loginform.passwordRules"
                    type="password"
                    label="Password"
                    style="color:red;"
                    required
                  ></v-text-field>
                  <v-btn
                    text
                    :disabled="!loginform.valid"
                    style="color:#4CAF50;"
                    class="mr-4 mb-5 mt-3"
                    @click="validateLoginform"
                  >
                    Login
                  </v-btn>
                  <v-btn
                    text
                    style="color:#F44336;"
                    class="mr-4 mb-5 mt-3"
                    @click="resetLoginform"
                  >
                    Reset Form
                  </v-btn>
                </v-form>
              </template>
            </v-tab-item>
            <v-tab-item :value="'tab-2'">
              <template>
                <v-form
                  ref="registerform"
                  v-model="registerform.valid"
                  lazy-validation
                  class="pl-4 pt-3 pr-4 pb-4 ml-2 mr-2 mt-2"
                >
                  <v-text-field
                    v-model="registerform.username"
                    :rules="registerform.nameRules"
                    label="Username"
                    style="color:red;"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="registerform.fullname"
                    :rules="registerform.fullnameRules"
                    label="Fullname"
                    style="color:red;"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="registerform.password"
                    :rules="registerform.passwordRules"
                    type="password"
                    label="Password"
                    style="color:red;"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="registerform.checkPassword"
                    :rules="registerform.checkPasswordRules"
                    type="password"
                    label="Confirm Password"
                    style="color:red;"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="registerform.e_mail"
                    :rules="registerform.e_mailRules"
                    label="Mailbox"
                    style="color:red;"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="registerform.companie"
                    :rules="registerform.companieRules"
                    label="Companies"
                    style="color:red;"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="registerform.area"
                    :rules="registerform.areaRules"
                    label="Area"
                    style="color:red;"
                    required
                  ></v-text-field>
                  <v-btn
                    text
                    :disabled="!registerform.valid"
                    style="color:#4CAF50;"
                    class="mr-4 mb-3 mt-3"
                    @click="validateRegisterform"
                  >
                    Register
                  </v-btn>
                  <v-btn
                    text
                    style="color:#F44336;"
                    class="mr-4 mb-3 mt-3"
                    @click="resetRegisterform"
                  >
                    Reset Form
                  </v-btn>
                </v-form>
              </template>
            </v-tab-item>
          </v-tabs-items>
        </v-card>
      </v-dialog>
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
    </div>
    <v-content>
      <section>
        <v-parallax height="600" src="../../static/book1.jpg">
          <v-layout column align-center justify-center class="white--text">
            <h1
              class="white--text mb-2 text-center"
              style="font-family:Georgia !important;font-size:80px;"
            >
              Welcome to StarChair
            </h1>
            <div class="subheading mb-5 text-center">
              A Platform Created for Scientists
            </div>
            <div v-if="token == null" class="mt-4 mr-4 text-center">Administrator username:admin; password: password.</div>
            <v-btn
              class="mx-auto white--text mt-4"
              color="#272727"
              large
              width="300"
              @click="goToMenu"
            >
              Get Started
            </v-btn>
          </v-layout>
        </v-parallax>
      </section>

      <section>
        <v-layout column wrap class="my-12" align-center>
          <v-flex xs12 sm4 class="my-4">
            <div class="text-center mb-4">
              <h2
                class="headline font-weight-regular"
                style="font-size:24px;color:#272727;margin-top:20px;"
              >
                The best platform to contribute your paper.
              </h2>
              <span class="subheading" style="font-size:16px;color:#272727;">
                People from all the countries gather here.
              </span>
            </div>
          </v-flex>
          <v-flex xs12 class="mb-4">
            <v-container grid-list-xl>
              <v-layout row wrap align-center>
                <v-flex xs12 md4>
                  <v-card flat class="transparent">
                    <v-card-text class="text-center">
                      <v-icon x-large class="#272727--text">mdi-palette</v-icon>
                    </v-card-text>
                    <v-card-title primary-title class="layout justify-center">
                      <div
                        class="headline text-center mx-auto"
                        style="font-size:22px;"
                      >
                        About StarChair
                      </div>
                    </v-card-title>
                    <v-card-text style="font-size:16px;">
                      StarChair is a conference management system that is
                      flexible, easy to use, and has many features to make it
                      suitable for various conference models. It is currently
                      probably the most commonly used conference management
                      system. EasyChair was designed to help conference
                      organisers to cope with the complexity of the refereeing
                      process...
                    </v-card-text>
                  </v-card>
                </v-flex>
                <v-flex xs12 md4>
                  <v-card flat class="transparent">
                    <v-card-text class="text-center">
                      <v-icon x-large class="#272727--text">mdi-flash</v-icon>
                    </v-card-text>
                    <v-card-title primary-title class="layout justify-center">
                      <div class="headline mx-auto" style="font-size:22px;">
                        Why StarChair
                      </div>
                    </v-card-title>
                    <v-card-text style="font-size:16px;">
                      You can use it for free!Management and monitoring of the
                      program committee more easily! Sophisticated and flexible
                      management of the access of PC members and referees to
                      papers and conflicts of interests! Flexibility, EasyChair
                      has been used for evaluating project proposals, teaching
                      students paper writing and peer reviewing...
                    </v-card-text>
                  </v-card>
                </v-flex>
                <v-flex xs12 md4>
                  <v-card flat class="transparent">
                    <v-card-text class="text-center">
                      <v-icon x-large class="#272727--text">mdi-wrench</v-icon>
                    </v-card-text>
                    <v-card-title primary-title class="layout justify-center">
                      <div
                        class="headline text-center mx-auto"
                        style="font-size:22px;"
                      >
                        News
                      </div>
                    </v-card-title>
                    <v-card-text style="font-size:16px;">
                      StarChair launched a call for papers service for
                      conferences.<br />
                      Following the success of the StarChair EPiC Series...<br />
                      The design of EasyChair homepage changed.<br />
                      The design of EasyChair homepage changed.<br />
                      The design of EasyChair homepage changed.<br />
                      The design of EasyChair homepage changed.<br />
                    </v-card-text>
                  </v-card>
                </v-flex>
              </v-layout>
            </v-container>
          </v-flex>
        </v-layout>
      </section>

      <section>
        <v-parallax src="../../static/city1.jpg" height="380" class="mt-5 mb-4">
          <v-layout column align-center justify-center>
            <div
              class="headline white--text mb-4 text-center"
              style="font-size:30px;"
            >
              Attracts talents from all over the world
            </div>
            <em style="font-size:16px;" class="mb-5"
            >Kick-start contribute your paper today</em
            >
            <v-btn
              class="mx-auto white--text mt-4"
              color="#272727"
              large
              width="300"
              @click="goToMenu"
            >
              Get Started
            </v-btn>
          </v-layout>
        </v-parallax>
      </section>

      <section class="mb-4">
        <v-container grid-list-xl>
          <v-layout row wrap justify-center class="my-12">
            <v-flex xs12 sm4 offset-sm1>
              <v-card flat class="transparent">
                <v-card-title primary-title class="layout justify-center mt-4">
                  <div class="headline mx-auto" style="font-size:22px">
                    Services
                  </div>
                </v-card-title>
                <v-card-text>
                  Cras facilisis mi vitae nunc lobortis pharetra. Nulla volutpat
                  tincidunt ornare.
                </v-card-text>
                <v-list class="transparent">
                  <v-list-item>
                    <v-list-item-action class="mr-4">
                      <v-icon class="#272727">mdi-account-multiple</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                      <v-list-item-title class="text-left"
                      >Conference managing</v-list-item-title
                      >
                    </v-list-item-content>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-action class="mr-4">
                      <v-icon class="#272727">mdi-apple</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                      <v-list-item-title class="text-left"
                      >Smart program</v-list-item-title
                      >
                    </v-list-item-content>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-action class="mr-4">
                      <v-icon class="#272727">mdi-arrow-up-bold-outline</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                      <v-list-item-title class="text-left"
                      >Publishing</v-list-item-title
                      >
                    </v-list-item-content>
                  </v-list-item>
                </v-list>
              </v-card>
            </v-flex>
            <v-flex xs12 sm4 offset-sm1>
              <v-card flat class="transparent">
                <v-card-title primary-title class="layout justify-center mt-4">
                  <div class="headline mx-auto" style="font-size:22px">
                    Contact us
                  </div>
                </v-card-title>
                <v-card-text>
                  Cras facilisis mi vitae nunc lobortis pharetra. Nulla volutpat
                  tincidunt ornare.
                </v-card-text>
                <v-list class="transparent">
                  <v-list-item>
                    <v-list-item-action class="mr-4">
                      <v-icon class="#272727">mdi-phone</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                      <v-list-item-title class="text-left"
                      >777-867-5309</v-list-item-title
                      >
                    </v-list-item-content>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-action class="mr-4">
                      <v-icon class="#272727">mdi-map-marker</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                      <v-list-item-title class="text-left"
                      >Shanghai, CN</v-list-item-title
                      >
                    </v-list-item-content>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-action class="mr-4">
                      <v-icon class="#272727">mdi-email</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                      <v-list-item-title class="text-left"
                      >starchair@fudan.edu.cn</v-list-item-title
                      >
                    </v-list-item-content>
                  </v-list-item>
                </v-list>
              </v-card>
            </v-flex>
          </v-layout>
        </v-container>
      </section>

      <v-footer color="#EFEBE9">
        <v-layout row wrap align-center>
          <v-flex xs12>
            <div class="black--text ml-4" style="font-size:16px;">
              Copyright © 2020 StarChair
            </div>
          </v-flex>
        </v-layout>
      </v-footer>
    </v-content>
  </div>
</template>

<script>
  export default {
    name: "Homepage",
    data() {
      return {
        title: "StarChair",
        user: "",
        token: "",
        openOnHover: true,
        logOutDialog: false,
        loginDialog: false,
        tab: null,
        loginform: {
          valid: false,
          username: "",
          password: "",
          nameRules: [v => !!v || "The username cannot be empty"],
          passwordRules: [v => !!v || "The password cannot be empty"]
        },
        registerform: {
          valid: false,
          username: "",
          fullname: "",
          password: "",
          checkPassword: "",
          e_mail: "",
          companie: "",
          area: "",
          nameRules: [
            v => !!v || "The username cannot be empty",
            v =>
              /^[a-zA-Z][a-zA-Z0-9_]{4,31}$/.test(v) ||
              "Username can only begin with a letter and contain 5-32 characters of letters, Numbers, and special characters"
          ],
          fullnameRules: [v => !!v || "The fullname cannot be empty"],
          passwordRules: [
            v => !!v || "The password cannot be empty",
            v =>
              /(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^[^]{5,31}$/.test(v) ||
              "The password can only contain 5-32 characters in two or more types of letters, Numbers and special characters, and does not include the user name"
          ],
          checkPasswordRules: [
            v => !!v || "Please enter your password again",
            v =>
              v == this.registerform.password ||
              "The two input passwords are inconsistent!"
          ],
          e_mailRules: [
            v => !!v || "The e-mail address cannot be empty",
            v =>
              /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(v) ||
              "Please enter the correct email address"
          ],
          companieRules: [
            v => !!v || "The subordinate companies cannot be empty"
          ],
          areaRules: [v => !!v || "The area cannot be empty"]
        }
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
      }
    }
  };
</script>

<style>
  ::-webkit-scrollbar {
    width: 0px;
  }
</style>
