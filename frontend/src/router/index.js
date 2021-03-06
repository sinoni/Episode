import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../store';

import Main from '../components/main/PageMain'
import Signup from '../components/member/PageSignUp'
import Signin from '../components/member/PageSignIn'
import MyInfo from '../components/member/PageMyInfo'
import Editor from '../components/editor/PageEditor'
import Mywork from '../components/work/PageMyWork'
import Feel from '../components/feel/PageFeel'  //  여긴 나중에 바뀔 예정
import Top100 from '../components/top/PageTop100'  //  여긴 나중에 바뀔 예정
import NovelDetail from '../components/novel/PageNovelDetail'
import NovelGen from '../components/novel/PageNovelGenerate'
import NovelSet from '../components/novel/PageNovelSetting'
import Search from '../components/search/PageSearch'

import All from '../components/all/PageAll' //  (구)소설 전체 보기
import NovelList from '../components/all/PageNovelList' //  (현)소설 전체 보기

import Viewer from '../components/viewer/PageViewer'
import CharacterSet from '../components/characterset/PageCharacterSet'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Main',
    component: Main
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/signin',
    name: 'Signin',
    component: Signin, 
    beforeEnter: rejectAuthUser

  },
  {
    path: '/myinfo',
    name: MyInfo,
    component: MyInfo,
    beforeEnter: rejectUnAuthUser
  },
  {
    path: '/novel/edit',
    name: 'Editor',
    component: Editor,
    beforeEnter: rejectUnAuthUser
  },
  {
    path: '/mywork',
    name: 'Mywork',
    component: Mywork,
    beforeEnter: rejectUnAuthUser
  },
  {
    path: '/feel/:feelPk',
    name: 'Feel',
    component: Feel
  },
  {
    path: '/top100',
    name: 'Top100',
    component: Top100
  },
  {
    path: '/novel/detail/:novelPk',
    name: 'NovelDetail',
    component: NovelDetail
  },
  {
    path: '/viewer/:episodePk',
    name: 'Viewer',
    component: Viewer
  },
  {
    path: '/novel/generate',
    name: 'NovelGen',
    component: NovelGen,
    beforeEnter: rejectUnAuthUser
  },
  { 
    path: '/search/:searchKeyword/:searchPk/:pageNum',
    name: 'Search',
    component: Search
  },
  {
    path: '/all',
    name: 'All',
    component: All
  },
  {
    path: '/novel/list/:genrePk/:pageNum',
    name: 'NovelList',
    component: NovelList
  },
  {
    path: '/novel/setting/:novelPk',
    name: 'NovelSet',
    component: NovelSet,
    beforeEnter: rejectUnAuthUser
  },
  {
    path: '/characterset/:novelPk',
    name: 'CharacterSet',
    component: CharacterSet
  }
]

function rejectUnAuthUser (to, from, next) {
  store.dispatch('checkSession');
  if(!store.state.isLogin) {  // 로그인하지 않은 유저
    alert("로그인 하여야 이용할 수 있습니다! \n로그인 해주세요.")
    next("/signin")
  }else {
    next()
  }
}
function rejectAuthUser (to, from, next) {
  store.dispatch('checkSession');
  if(store.state.isLogin) { // 이미 로그인 한 유저 가드
    alert("이미 로그인 했습니다.")
    next("/")
  }else {
    next()
  }
}

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
