<template>
    <div class="container mt-5">
        <h2 id="b-title">策略列表</h2>
        <div class="col-qw">
            <div class="card" style="margin-top: 20px;">
                <div class="card-header">
                    <span style="font-size: 130%">固定策略</span>
                    <!-- 策略列表 -->
                    <table class="table table-striped mt-4">
                        <thead>
                            <tr>
                                <th>策略名称</th>
                                <th>描述</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- 示例数据，实际数据应从后端动态获取并展示 -->
                            <tr v-for="defence in defences" :key="defence.id">
                                <td>{{ defence.title }}</td>
                                <td>{{ defence.description }}</td>
                                <td> {{ defence.start === 1 ? "开启" : "关闭" }}</td>
                                <td>
                                    <div class="form-check form-switch">
                                        <input v-if="defence.start === 1" class="form-check-input" type="checkbox"
                                            role="switch" id="switch1" @click=update_defence(defence) checked>
                                        <input v-else class="form-check-input" type="checkbox" role="switch" id="switch1"
                                            @click=update_defence(defence)>
                                    </div>

                                </td>
                            </tr>

                            <!-- 更多策略行可以继续添加 -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div v-if="showPopup" class="popup-container">
            <div class="popup">
                <div class="popup-header">
                    <div class="alert">警报：遭受攻击</div>
                    <div>
                        <button type="button" class="btn btn-primary" @click="closePopup">我已知晓</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, reactive } from 'vue'
import $ from 'jquery'
import { useStore } from 'vuex'
import { onMounted, onUnmounted } from 'vue'
export default {
    name: "DefenceComponent",
    components: {

    },
    setup() {


        // 如果您有更多的开关和标签，可以继续按照这种模式进行扩展。

        const store = useStore();
        const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.id}`;
        let socket = null;
        let showPopup = ref(false);
        let defences = ref([]);
        const defenceadd = reactive({
            title: "",
            description: "",
            error_message: "",
        });
        const refresh_defence = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/defence/defence/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    defences.value = resp;
                }
            })
        }



        refresh_defence();


        const update_defence = (defence) => {
            defenceadd.error_message = "";
            $.ajax({
                url: "http://127.0.0.1:3000/defence/defence/update/",
                type: "post",
                data: {
                    id: defence.id,
                    title: defence.title,
                    description: defence.description,
                    start: defence.start ^ 1,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {

                    if (resp.error_message === "success") {

                        refresh_defence();
                    }
                }
            })
        }

        onMounted(() => {
            socket = new WebSocket(socketUrl);
            socket.onopen = () => {
                console.log("connected!");
            }
            socket.onmessage = msg => {
                const data = JSON.parse(msg.data);
                console.log(data);
                if (data == true) {
                    console.log("执行");
                    openPopup();
                }
            }

            socket.onclose = () => {
                console.log("disconnected!");
            }



        });

        onUnmounted(() => {

            if (socket) {
                socket.close();
            }
        })

        const openPopup = () => {

            showPopup.value = true;
        };

        const closePopup = () => {
            showPopup.value = false;
        };

        return {
            showPopup,
            defences,
            openPopup,
            closePopup,


            refresh_defence,
            update_defence,
        }
    }
}
</script>

<style scoped>
#b-title {
    font: bolder;
}

#c-title {
    color: blue;


}

/* 自定义样式将开关颜色设置为绿色 */
.form-switch .form-check-input:checked {
    background-color: green !important;
    /* 背景色为绿色 */
    border-color: green !important;
    /* 边框颜色为绿色 */
}

.form-switch .form-check-input:checked+.form-check-label {
    color: green !important;
    /* 文本颜色为绿色 */
}

div.error-message {
    color: red;
}

.popup-container {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    /* 半透明背景，可以根据需要调整 */
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
    /* 设置一个较高的层级，确保在其他元素之上 */
}

.popup {
    background: #fff;
    padding: 20px;
    border-radius: 8px;
    width: 300px;
    /* 根据需要调整弹窗宽度 */
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    /* 阴影效果，根据需要调整 */
}

.popup-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.alert {
    font-weight: bold;
    color: red;
    /* 警告颜色，根据需要调整 */
}

.btn-primary {
    background-color: blue;
    /* 按钮颜色，根据需要调整 */
    color: #fff;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
}

.popup-container {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    /* 半透明背景，可以根据需要调整 */
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
    /* 设置一个较高的层级，确保在其他元素之上 */
}

.popup {
    background: #fff;
    padding: 20px;
    border-radius: 8px;
    width: 300px;
    /* 根据需要调整弹窗宽度 */
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    /* 阴影效果，根据需要调整 */
}

.popup-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.alert {
    font-weight: bold;
    color: red;
    /* 警告颜色，根据需要调整 */
}

.btn-primary {
    background-color: blue;
    /* 按钮颜色，根据需要调整 */
    color: #fff;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
}</style>