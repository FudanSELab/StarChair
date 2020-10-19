<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <v-card
    class="overflow-hidden"
    style="width:100%;height:100%;background-color:#ECEFF1;"
  >
    <v-app-bar flat dark height="60" style="border-radius:0;" color="#263238">
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
    <v-sheet id="scrolling-techniques" class="overflow-y-auto" fluid>
      <v-container fluid style="padding:0;">
        <v-dialog v-model="logOutDialog" max-width="400">
          <v-card>
            <v-card-title style="font-size:18px;font-family:Georgia;"
            >Are you sure to logout?
            </v-card-title
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
          <template>
            <v-container fluid>
              <v-data-iterator
                :items="meetingList"
                :items-per-page.sync="itemsPerPage"
                :page="page"
                :sort-by="sortBy.toLowerCase()"
                :sort-desc="sortDesc"
                hide-default-footer
              >
                <template v-slot:default="props">
                  <v-row justify="start">
                    <v-col
                      class="block"
                      v-for="(item,itemKey) in props.items"
                      :key="itemKey"
                      :cols="4"
                    >
                      <v-card class="ma-4" style=" border-radius:10px;">
                        <div>
                          <div
                            class="overline mb-7 pl-4 pt-2"
                            style="height:20px;text-align:left;"
                          >
                            <v-icon class="mr-2" small>mdi-book-outline
                            </v-icon
                            >
                            Attended Conference
                          </div>
                          <h4
                            class="mb-6"
                            style="font-family:Times New Roman;
                            font-size: 1.45rem;letter-spacing:.0125em;color:#272727;"
                          >
                            {{ item.fullName }}
                          </h4>
                          <v-container fluid>
                            <v-simple-table>
                              <tbody class="text-left justify-center">
                              <tr v-for="(key, index) in filteredKeys"
                                  :key="index">
                                <td class="text-capitalize" style="font-family:Times New Roman;font-size:16px">{{ key }}</td>
                                <td style="font-family:Times New Roman;font-size:16px">{{ item[key] }}</td>
                              </tr>
                              <tr>
                                <td class="text-capitalize" style="font-family:Times New Roman;font-size:16px">Topic</td>
                                <td style="font-family:Times New Roman;font-size:16px">{{handleTopic(itemKey)}}</td>
                              </tr>
                              </tbody>
                            </v-simple-table>
                          </v-container>
                          <v-btn
                            text
                            @click="showChooseIdDialog(item)"
                            class="mt-1 mb-10"
                            color="#37474F"
                          >Choose your identity
                          </v-btn
                          >
                        </div>
                      </v-card>
                    </v-col>
                  </v-row>
                </template>

                <template v-slot:footer>
                  <v-row class="mt-2 mr-4" align="center" justify="end">
                    <span
                      class="mr-4 grey--text"
                      style="font-size:19px !important;font-family:Times New Roman;"
                    >
                      Page {{ page }} of {{  numberOfPages }}
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
              <v-dialog v-model="chooseIdDialog" max-width="400">
                <v-card>
                  <v-card-title
                    class="mb-8 text-center"
                    style="background:#BCAAA4;color:#272727;"
                    primary-title
                  >
                    Choose your identity
                  </v-card-title>
                  <v-form
                    ref="form"
                    lazy-validation
                  >
                    <v-radio-group :mandatory="false" style="margin-left: 5%">
                      <v-radio v-for="(element, index) in this.identity" :key="index"
                               :value="element.authority.toString()"
                               :label="element.authority.toString()"
                               @change="getRadioVal(element.authority.toString())"
                      >
                      </v-radio>
                    </v-radio-group>


                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn
                        color="primary"
                        text
                        @click="ChooseIdentity"
                      >
                        Go to
                      </v-btn>
                    </v-card-actions>
                  </v-form>
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
  export default {
    name: "UserConf",

    data() {
      return {
        activeName: "allMeeting",
        lists: [], //所有会议
        item: [], //选中的会议
        identity: [],
        isRouterAlive: true,
        identityChosen: "",
        radioVal: "",
        loading: false,
        identityForm: {},

        user: "",
        token: "",
        openOnHover: true,
        logOutDialog: false,
        chooseIdDialog: false,

        tab: null,

        filter: {},
        sortDesc: false,
        page: 1,
        itemsPerPage: 3,
        sortBy: 'fullName',
        keys: [
          'fullName',
          'shortenForm',
          'applicant',
          'time',
          'location',
          'ddl',
          'publishingTime'
        ],
        items: [],
        meetingList: [],
        topicList: [],
      };
    },
    computed: {
      numberOfPages() {
        return Math.ceil(this.meetingList.length / this.itemsPerPage)
      },
      filteredKeys() {
        return this.keys.filter(key => key !== `fullName`)
      }
    },
    created() {
      this.handleUserData();
      this.handleToken();
    },
    methods: {
      nextPage() {
        if (this.page + 1 <= this.numberOfPages) this.page += 1
      },
      formerPage() {
        if (this.page - 1 >= 1) this.page -= 1
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
      showChooseIdDialog(item) {
        this.item = item;
        this.getIdentity();
        this.chooseIdDialog = true;
      },
      handleSizeChange: function (size) {
        this.pagesize = size;
        //console.log(`每页 ${val} 条`);
        console.log(this.pagesize);
      },
      handleCurrentChange: function (currentPage) {
        this.currentPage = currentPage;
        console.log(this.currentPage);
      },
      getRadioVal: function (value) {
        //得到单选框的值
        this.identityChosen = value;
        console.log(value);
      },
      getIdentity: function () {
        //从后端读取用户身份
        var self = this;
        this.$axios
          .get("http://114.116.136.180/getAuthorityList", {
            params: {
              //用户参数
              id: this.item.id,
              username: JSON.parse(localStorage.getItem("userDetails")).username
            }
          })
          .then(function (response) {
            var data = response.data;
            self.identity = JSON.parse(JSON.stringify(data));
          })
          .catch(function (error) {
            console.log(error);
          });
      },
      loadData: function () {
        var self = this;
        this.$axios
          .get("http://114.116.136.180/userConf", {
            params: {
              //用户参数
              username: JSON.parse(localStorage.getItem("userDetails")).username
            }
          })
          .then(function (response) {
            var data = response.data;
            self.items = JSON.parse(JSON.stringify(data));
            self.handleMeetingTopicList();
            if(self.items.length === 0){
              self.$message({
                message: "No Conference Found !",
                type: "info",
                duration: 2000
              });
            }
            
          })
          .catch(function (error) {
            console.log(error);
          });
      },
      handleMeetingTopicList() {
        for(var i = 0; i < this.items.length; i+=2){  
          this.meetingList.push(this.items[i]);
          this.topicList.push(this.items[i+1]);
        }
      },
      handleTopic(itemKey) {
        var meetingTopic = "";
        itemKey = itemKey+3*(this.page-1);
        this.topicList[itemKey].forEach(element => {
          meetingTopic+=(element.topic+"\\");
        });
        return meetingTopic;
      },
      ChooseIdentity: function () {
        //选择身份后跳转
        if (this.identityChosen === "ROLE_PCMEMBER") {
          this.$router.push(
            "/PcMember?confID=" +
            this.item.id +
            "&confStatus=" +
            this.item.submitStatus
          );
        } else if (this.identityChosen === "ROLE_AUTHOR") {
          this.$router.push(
            "/Author?confID=" +
            this.item.id +
            "&confStatus=" +
            this.item.submitStatus
          );
        } else if (this.identityChosen === "ROLE_CHAIR") {
          this.$router.push(
            "/chair?confID=" +
            this.item.id +
            "&confStatus=" +
            this.item.submitStatus + 
            "&confDdl=" +
            this.item.ddl
          );
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
      }
    },
    reload() {
      //重新加载页面
      this.isRouterAlive = false; //先关闭，
      this.$nextTick(function () {
        this.isRouterAlive = true; //再打开
      });
    },
    mounted: function () {
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
