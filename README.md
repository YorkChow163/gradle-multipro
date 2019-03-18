# gradle-multipro(gradle聚合工程示例)
gradle聚合工程的官方文档件见[这里](https://docs.gradle.org/current/userguide/multi_project_builds.html)
## 建立父工程目录
## 建立子工程
## 将子工程包含进来
## 修改父工程的build.gradle
### 全文示例

```text

```
### 关于`subprojects` 和`allprojects`的区别
这两个功能非常相似，前者指定包括rootprorject在内都要执行的task,后者只在子工程内执行task.可以参看[这里](https://www.jianshu.com/p/84ac62747e59)
### 关于`buildscript`的repositories和`allprojects`的repositories的区别
* buildscript中的声明是gradle脚本自身需要使用的资源。可以声明的资源包括依赖项、第三方插件、maven仓库地址等,而这些插件和类库不是项目直接使用的。
* allprojects里面的依赖是项目的共同依赖，从指定的maven仓库地址获取
## 修改子工程的build.gradle
