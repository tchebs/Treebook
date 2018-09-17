import Vue from 'vue'
import Router from 'vue-router'
import TreebookHome from '@/components/Vue/TreebookHome'
import TreebookDatabase from '@/components/Vue/TreebookDatabase'
import TreebookRegistration from '@/components/Vue/TreebookRegistration'
import TreebookMap from '@/components/Vue/TreebookMap'
import UserRegistration from '@/components/Vue/UserRegistration'
import UserLogin from '@/components/Vue/UserLogin'
import UserProfile from '@/components/Vue/UserProfile'
import TreebookForecast from '@/components/Vue/TreebookForecast'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/treebookforecast',
      name: 'TreebookForecast',
      component: TreebookForecast
    },
    {
      path: '/treebookhome',
      name: 'TreebookHome',
      component: TreebookHome
    },
    {
      path: '/treebookregistration',
      name: 'TreebookRegistration',
      component: TreebookRegistration
    },
    {
      path: '/database',
      name: 'TreebookDatabase',
      component: TreebookDatabase
    },
    {
      path: '/treebookmap',
      name: 'TreebookMap',
      component: TreebookMap
    },
    {
      path: '/userregistration',
      name: 'UserRegistration',
      component: UserRegistration
    },
    {
      path: '/userprofile',
      name: 'UserProfile',
      component: UserProfile
    },
    {
      path: '/userlogin',
      name: 'UserLogin',
      component: UserLogin
    }

  ]
})
