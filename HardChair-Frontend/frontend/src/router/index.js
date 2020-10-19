import Vue from 'vue'
import Router from 'vue-router'
import Homepage from '@/components/Homepage'
import ConferenceApplication from '@/components/ConferenceApplication'
import UserCenter from '@/components/UserCenter'
import ReviewMeeting from '@/components/ReviewMeeting'
import AllMeetings from '@/components/AllMeetings'
import MyApplication from '@/components/MyApplication'
import Chair from '@/components/Chair'
import Discussion from '@/components/Discussion'
import Contribution from '@/components/Contribution'
import UserConf from '@/components/UserConf'
import Author from '@/components/Author'
import PcMember from '@/components/PcMember'
import StartMenu from '@/components/StartMenu'
import store from '../store'

Vue.use(Router)

export const router = new Router({
  routes: [
    // {
    //   path: '/',
    //   name: 'Author',
    //   component: Author,
    //   // meta: {
    //   //   requireAuth: true // 需要登录权限
    //   // }
    // },
    {
      path: '/',
      name: 'Homepage',
      component: Homepage,
      // meta: {
      //   requireAuth: true // 需要登录权限
      // }
    },
    {
      path: '/Homepage',
      name: 'Homepage',
      component: Homepage,
      // meta: {
      //   requireAuth: true // 需要登录权限
      // }
    },
    {
      path: '/ConferenceApplication',
      name: 'ConferenceApplication',
      component: ConferenceApplication,
      meta: {
        requireAuth: true // 需要登录权限
      }
    },
    {
      path: '/UserCenter',
      name: 'userCenter',
      component: UserCenter,
      meta: {
        requireAuth: true // 需要登录权限
      }
    },
    {
      path: '/ReviewMeeting',
      name: 'reviewMeeting',
      component: ReviewMeeting,
      meta: {
        requireAuth: true // 需要登录权限
      }
    },
    {
      path: '/AllMeetings',
      name: 'allMeetings',
      component: AllMeetings,
      meta: {
        requireAuth: true // 需要登录权限
      }
    },
    {
      path: '/MyApplication',
      name: 'myApplication',
      component: MyApplication,
      meta: {
        requireAuth: true // 需要登录权限
      }
    },
    {
      path: '/Chair',
      name: 'chair',
      component: Chair,
      meta: {
        requireAuth: true // 需要登录权限
      }
    },
    {
      path: '/Discussion',
      name: 'discussion',
      component: Discussion,
      meta: {
        requireAuth: true // 需要登录权限
      }
    },
    {
      path: '/Contribution',//论文投稿
      name: 'Contribution',
      component: Contribution,
      meta: {
        requireAuth: true // 需要登录权限
      }
    },
    {
      path: '/UserConf',//管理员审核
      name: 'UserConf',
      component: UserConf,
      meta: {
        requireAuth: true // 需要登录权限
      }
    },
    {
      path: '/Author',
      name: 'Author',
      component: Author,
      meta: {
        requireAuth: true // 需要登录权限
      }
    },
    {
      path: '/PcMember',
      name: 'PcMember',
      component: PcMember,
      meta: {
        requireAuth: true // 需要登录权限
      }
    },
    {
      path: '/StartMenu',
      name: 'startMenu',
      component: StartMenu,
      meta: {
        requireAuth: true // 需要登录权限
      }
    },
    {
      path: '/Discussion',
      name: 'Discussion',
      component: Discussion,
      meta: {
        requireAuth: true // 需要登录权限
      }
    }
  ]
})

//前端登录拦截
router.beforeEach(function (to, from ,next) {
  if (to.matched.some(record => record.meta.requireAuth)) {
    if (store.state.token) {
      next()
    } else {
      next({
        path: '/',
        query: {redirect: to.fullPath} // 登录成功之后重新跳转到该路由
      })
    }
  } else {
    next()
  }
})
