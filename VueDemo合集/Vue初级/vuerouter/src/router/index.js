import Vue from 'vue'
import Router from 'vue-router'
import Home from '../components/Home'
import About from '../components/About'
import User from '../components/User'
//懒加载模式
const HomeNews = () => import('../components/HomeNews')
const HomeMessage = () => import('../components/HomeMessage')
const Profile = () => import('../components/Profile')
// 安装插件
Vue.use(Router)

//创建路由对象
const router = new Router({
  // 配置路径和组件之间的映射关系
  routes: [
    {
      path: '',
      redirect: "/home"
    },
    {
      path: '/home',
      component: Home,
      meta: {
        title: '首页'
      },
      children: [
        {
          path: '',
          redirect: 'news'
        },
        {
          path: 'news',
          component: HomeNews
        },
        {
          path: 'message',
          component: HomeMessage
        }
      ]
    },
    {
      path: '/about',
      component: About,
      meta: {
        title: '关于'
      }
    },
    {
      path: '/user/:userId',
      component: User,
      meta: {
        title: '用户'
      }
    },
    {
      path: '/profile',
      component: Profile,
      meta: {
        title: '档案'
      }
    }
  ],
  mode: 'history',
  linkActiveClass: "active"
})

//前置钩子
router.beforeEach((to, from, next) => {
  document.title = to.matched[0].meta.title;
  //console.log(to);
  console.log('++++');
  next();
})

//后置钩子
router.afterEach((to, from) => {
  console.log('-----');
})

export default router
