//在这里定义组件
let navMenuItem = Vue.extend({
    name: 'nav-item',
    props: {
        item:{},
        active:{},
        changeActive:{
            type:Function
        }
    },
    template: [
        '<li :class="active" @click="changeActive">',
        '    <a v-if="item.type === 0"  href="javascript:;">',
        '        <i v-if="item.icon!=null" :class="item.icon"</i>',
        '        <i v-else class="glyphicon glyphicon-cog"</i>',
        '        <span>{{item.name}}</span>',
        '        <i class="fa fa-angle-left pull-right"></i>',
        '    </a>',
        '    <ul v-if="item.type === 0" class="treeview-menu">',
        '       <nav-item :item="item" v-for="item in item.list"> <nav-item/>',
        '    </ul>',
        '    <a v-if="item.type === 1 && item.parentId === 0" :href="\'#\'+item.url">',
        '        <i v-if="item.icon!=null" :class="item.icon"</i>',
        '        <span>{{item.name}}</span>',
        '    </a>',
        '    <a v-if="item.type === 1 && item.parentId != 0" :href="\'#\'+item.url">',
        '        <i v-if="item.icon!=null" :class="item.icon"</i>',
        '        <i v-else class="fa fa-circle-o">',
        '        <span>{{item.name}}</span>',
        '    </a>',
        '</li>'
    ]
});
//初始化组件
Vue.component('navMenuItem', navMenuItem);
let vm = new Vue({
    el: '#sideBar',
    data: {
        user: '',
        active:"active",
        navTitle: '菜单',
        navMenuList: '',
        main:'main.html'
    },
    methods: {
        //获取导航菜单
        getNavMenuList: function () {
            axios.get("/menu/nav").then(response => {
                console.log("获取导航菜单成功：", response);
                vm.navMenuList = response.navMenuList;
            }).catch(error => {
                console.log("获取导航菜单失败：", error);
            })
        },
        //获取用户
        getUser: function () {
            axios.get("/user/list").then(response => {
                console.log("获取用户成功：", response);
                vm.user = response.user;
            }).catch(error => {
                console.log("获取用户失败：", error);
            })
        },
        //点击展开收缩导航栏
        changeActive:function(){
            //动态修改class值
            let active = vm.active;
            if(active==''){
                vm.active='active';
            }else {
                vm.active='';
            }

        },
        //更新密码
        updatePassword: function () {

        }
    },
    mounted: function () {
        this.getNavMenuList();
        this.getUser();
    },
    //vue数据驱动更新钩子函数
    update: function () {
          let router = new MyRouter();
          routerList(router,vm.navMenuList);
          window.onhashchange=function () {
              router.changeLoad()
          }
    }
});

function routerList(router,navMenuList) {
    let mulist = Array.prototype.slice.call(navMenuList);
    for (let menu of mulist) {
        if(menu.type==0){
            //一级菜单不需要路由
            routerList(router,menu.list);
        }else if(menu.type==1){
            router.addHashList(addr,function () {
                //添加路由列表
                var addr = window.location.hash;
                //替换iframe的url
                vm.main = addr.replace('#', '');
            })
        }
    }
}