!function () {
    const NOCALLBACK='nocallback';
    class Router {
        constructor() {
            this.hashList = new Map();
            this.key = '!';
            this.index = null
        }

        /**
         * hash监听，监听到就要执行回调函数，修改iframe的src值
         */
        changeLoad(){
            let hashAddr = window.location.hash.replace('#' + this.key, '');
            let callback = this.getCallBack(hashAddr);
            if(callback!=NOCALLBACK){
                let add=hashAddr.split('/');
                add.shift();
                callback.apply(this,add);
            }else {
                this.index && this.justGo(hashAddr);
            }

        }

        /**
         * 添加路由列表
         * @param addr url地址
         * @param callback 回调函数
         */
        addHashList(addr,callback){
            hashList.set(addr,callback)
        }

        /**
         * 获取回调函数
         * @param addr url
         * @returns {*} 回调函数对象
         */
        getCallBack(addr){
            let callb = hashList.get(addr);
            if(!callb||callb==null){
                return NOCALLBACK;
            }
        }

        /**
         * 删除路由
         * @param addr
         */
        deleRouter(addr){
            this.hashList.delete(addr);
        }

        justGo(addr){
            window.location.hash = '#' + this.key + addr;
        }

    }
    window.MyRouter=Router;
}();