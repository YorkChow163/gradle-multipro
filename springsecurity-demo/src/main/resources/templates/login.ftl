<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="../statics/css/bootstrap.min.css">
    <link rel="stylesheet" href="../statics/css/font-awesome.min.css">
    <link rel="stylesheet" href="../statics/css/AdminLTE.min.css">
    <link rel="stylesheet" href="../statics/css/all-skins.min.css">
</head>
<body>
<div class="login-box" id="rrapp" v-cloak>
    <div class="login-logo">
        <b>周宇搞事系统</b>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg">管理员登录</p>
        <!--v-if，条件判断-->
        <div v-if="error" class="alert alert-danger alert-dismissible">
            <h4 style="margin-bottom: 0px;"><i class="fa fa-exclamation-triangle"></i> {{errorMsg}}</h4>
        </div>
        <div class="form-group has-feedback">
            <input type="text" class="form-control" v-model="username" placeholder="账号">
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
            <input type="password" class="form-control" v-model="password" placeholder="密码">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
            <input type="text" class="form-control" v-model="captcha" @keyup.enter="login" placeholder="验证码">
            <span class="glyphicon glyphicon-warning-sign form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
            <!--vue中:属性名 加冒号的是 vue 的 v-bind 语法糖，属性名绑定vue的一个数据-->
            <img alt="如果看不清楚，请单击图片刷新！" class="pointer" :src="src" @click="refreshCode">
            &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" @click="refreshCode">点击刷新</a>
        </div>


        <div class="row">
            <div class="col-xs-8">
                <div class="checkbox">
                </div>
            </div>
            <!-- /.col -->
            <div class="col-xs-4">
                <button type="button" class="btn btn-primary btn-block btn-flat" @click="login">登录</button>
            </div>
            <!-- /.col -->
        </div>
        <!-- /.social-auth-links -->

    </div>
    <!-- /.login-box-body -->
</div>
<script src="../statics/libs/jquery.min.js"></script>
<script src="../statics/libs/vue.js"></script>
<#--<script src="https://cdn.jsdelivr.net/npm/vue@2.6.10/dist/vue.js"></script>-->
<script src="../statics/libs/bootstrap.min.js"></script>
<script src="../statics/libs/axios.min.js"></script>
<script src="../statics/myjs/TimeUtil.js"></script>
<script type="text/javascript">
    let vm=new Vue({
        el:`#rrapp`,
        data:{
            username:'',
            password:'',
            captcha:'',
            src:'/kaptcha',
            error:false,
            errorMsg:''
        },
        beforeCreate(){
            console.log("创建前：");
            console.log(this.$el);
            console.log(this.$data);
        },
        created(){
            console.log("创建完成：");
            console.log(this.$el);
            console.log(this.$data);
        },
        beforeMount(){
            console.log("挂载前：");
            console.log(this.$el);
            console.log(this.$data);
        },
        mounted(){
            console.log(this.$el);
            console.log(this.$data);
            /*向后台请求数据,请求验证码*/
            let formatDate1 =TimeUtil.formatDateToStr(new Date(),'yyyy-MM-dd hh:mm:ss');
            console.log("挂载完成,时间:",formatDate1);
            axios.get(`/captcha?ID=123456`)
                .then((response) =>{
                    console.log('成功啦');
                    console.log(response);
                })
                .catch((error)=> {
                    console.log('失败啦');
                    console.log(error);
                })
        },
        methods:{
            refreshCode:function (){
                //获取验证码
                this.src = "captcha.jpg?t=" + $.now();
                console.log("caonima:",this.src)
            },
            login:function (){
                axios.get('/user?ID=12345')
                    .then(function (response) {
                        console.log('成功啦');
                        console.log(response);
                    })
                    .catch(function (error) {
                        console.log('失败啦');
                        console.log(error);
                    })
            }
        }
    });
</script>
</body>
</html>