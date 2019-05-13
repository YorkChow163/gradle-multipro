//在这里定义组件
let navMenuItem = Vue.extend({

    
});
//初始化组件
Vue.component('navMenuItem',navMenuItem);
let vm = new Vue({
    el: '#sideBar',
    data: {
        user: '',
        navTitle: '菜单',
        navMenuList: ''
    },
    methods: {
        //获取导航菜单
        getNavMenuList: function () {
            axios.get("/menu/nav").then(response=>{
                console.log("获取导航菜单成功：",response);
                vm.navMenuList=response.navMenuList;
            }).catch(error=>{
                console.log("获取导航菜单失败：",error);
            })
        },
        //获取用户
        getUser:function () {
            axios.get("/user/list").then(response=>{
                console.log("获取用户成功：",response);
                vm.user=response.user;
            }).catch(error=>{
                console.log("获取用户失败：",error);
            })
        },
        //更新密码
        updatePassword:function () {

        }
    },
    mounted: function () {
        this.getNavMenuList();
        this.getUser();
    },
    //vue数据驱动更新钩子函数
    update:function () {

    }
});