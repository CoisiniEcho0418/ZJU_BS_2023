import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

const router = new Router({
  mode: "history", // 去掉路径中的 # 号
  routes: [
    { path: "/", redirect: "login" },
    //{ path: "/", redirect: "home" },
    {
      path: "/login",
      meta: {
        title: "登录界面"
      },
      name: "login",
      component: () => import("@/view/login.vue")
    },
    {
      path: "/home",
      meta: {
        title: "主页"
      },
      component: () => import("@/view/home.vue"),
      redirect: "/index",
      children: [
        {
          path: "/index",
          meta: {
            title: "首页"
          },
          component: () => import("@/view/overview.vue")
        },
        {
          path: "/device/config",
          meta: {
            title: "设备配置"
          },
          component: () => import("@/view/device/config.vue")
        },
        {
          path: "/device/message",
          meta: {
            title: "设备信息"
          },
          component: () => import("@/view/device/message.vue")
        },
        {
          path: "/user",
          meta: {
            title: "用户管理"
          },
          component: () => import("@/view/user.vue")
        }
      ]
    }
  ]
});

// 挂载路由导航守卫：to表示将要访问的路径，from表示从哪里来，next是下一个要做的操作 （路由导航守卫主要用来拦截请求信息）
router.beforeEach((to, from, next) => {
  // 修改页面 title
  if (to.meta.title) {
    document.title = "物联网设备管理系统 - " + to.meta.title;
  }
  // 放行登录页面
  if (to.path === "/login") {
    return next();
  } else {
    let token = localStorage.getItem("Authorization");

    if (token === null || token === "") {
      next("/login");
    } else {
      next();
    }
  }
});

export default router;
