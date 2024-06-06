<template>
    <ContentField v-if="!$store.state.user.pulling_info">
        <div class="title">基于流量特征的物联网攻击检测与防御系统的设计与实现</div>
        <div class="row justify-content-md-center register">
            <div class="col-8">
                <form @submit.prevent="login">
                    <div class="mb-3">
                        <label for="username" class="form-label">用户名</label>
                        <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">密码</label>
                        <input v-model="password" type="password" class="form-control" id="password"
                            placeholder="请输入密码">
                    </div>
                    <div class="error-message">{{ error_message }}</div>
                    <div class="d-grid gap-2 col-8 mx-auto">
                        <button class="btn btn-primary" type="submit">登录</button>
                    </div>

                </form>
                <p><router-link
                        class="nav-link link-primary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover"
                        :to="{ name: 'register' }">还没账号？点我注册</router-link></p>

            </div>
        </div>
    </ContentField>
</template>

<script>
import ContentField from '@/components/ContentField.vue';
import { useStore } from 'vuex';
import { ref } from 'vue';
import router from '../../src/router/index.js';

export default {
    name: "LoginComponent",
    components: {
        ContentField
    },
    setup() {





        const store = useStore();
        let username = ref('');
        let password = ref('');
        let error_message = ref('');

        const jwt_token = localStorage.getItem("jwt_token");
        if (jwt_token) {
            store.commit("updateToken", jwt_token);
            store.dispatch("getinfo", {
                success() {

                    router.push({ name: "home" });
                    store.commit("updatePullingInfo", false);
                },
                error() {
                    store.commit("updatePullingInfo", false);
                }
            })
        } else {
            store.commit("updatePullingInfo", false);
        }

        const login = () => {
            error_message.value = "";
            store.dispatch("login", {
                username: username.value,
                password: password.value,
                success() {
                    store.dispatch("getinfo", {
                        success() {
                            router.push({ name: 'home' });
                        }
                    })
                },
                error() {
                    error_message.value = "用户名或密码错误";
                }
            })
        }

        return {
            username,
            password,
            error_message,
            login,
        }
    }
}
</script>

<style scoped>
 div.title {
     font-size: 1rem;
     /* 设置字体大小 */
     font-weight: bolder;
     /* 设置加粗 */
     color: blue;
     /* 设置颜色 */
     text-align: center;
     /* 居中对齐 */
     padding: 20px 0;
     /* 上下内边距 */
     margin-bottom: 30px;
     /* 下边距 */
     background-color: #f7f7f7;
     /* 背景色 */
     border-radius: 8px;
     /* 圆角边框 */
     box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
     /* 阴影效果 */


 }

 div.error-message {
     color: red;
 }

 button.btn btn-primary {
     margin: 100px;
     /* 这将使按钮在其容器内水平居中 */
     display: block;
     /* 使按钮成为块级元素，以便使用 margin: 0 auto; 居中 */

 }

 a.link-primary {
     position: relative;
     top: 10px;
     /* 距离底部的距离 */
     left: 200px;
     /* 距离右侧的距离 */
     padding: 10px;
     /* 可选：为链接添加一些内边距 */
     font-size: 0.8em;
 }

 button {
     width: 100%;
 }

 div.error-message {
     color: red;
 }
</style>