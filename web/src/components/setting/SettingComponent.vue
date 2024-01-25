<template>
    <div class="container mt-5">
        <div class="col-qw">
            <div class="card shadow" style="margin-top: 20px;">
                <div class="card-header bg-secondary text-white">
                    <span style="font-size: 130%">配置</span>
                </div>
                <div class="card-body">
                    <div class="container">
                        <div class="mb-3">
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="selectBox" class="form-label">模型选择:</label>

                                    <select class="form-select" aria-label="Default select example" v-model="selectedModel">
                                        <option v-for="model in modelOptions" :key="model.value" :value="model.value">{{
                                            model.label }}</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="alarmConfig" class="form-label">报警配置：</label>
                            <input type="text" id="alarmConfig" class="form-control" placeholder="配置报警条件：严重警告次数阈值"
                                v-model="threshold">
                        </div>

                        <div class="mb-3">
                            <label for="alarmConfig" class="form-label">防火墙配置：</label>
                            <input type="text" id="alarmConfig" class="form-control" placeholder="配置报警条件：严重警告次数阈值"
                                v-model="nodethreshold">
                        </div>
                        <div class="mb-3">
                            <label for="notificationMethod" class="form-label">选择通知方式:</label>

                            <select id="notificationMethod" name="notificationMethod" class="form-select" required
                                v-model="selectnotice">

                                <option value="1">电子邮件</option>
                                <option value="2">系统弹窗</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="notificationRecipient" class="form-label">电子邮件通知接收者:</label>
                            <div class="input-group">
                                <input v-model="newRecipient" type="text" id="notificationRecipient"
                                    name="notificationRecipient" class="form-control" required>
                                <button @click="addRecipient" class="btn btn-outline-primary">添加</button>
                            </div>
                        </div>

                        <div id="recipientTags" class="mb-3">
                            <div v-for="(recipient, index) in recipients" :key="index"
                                class="recipientTag bg-light p-2 rounded">
                                <span>{{ recipient.email }}</span>
                                <span @click="removeRecipient(recipient, index)"
                                    class="removeRecipient text-danger cursor-pointer">删除</span>
                            </div>
                        </div>

                        <button type="button" class="btn btn-primary" @click="saveChanges">保存修改</button>
                    </div>
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
import { ref } from 'vue';
import $ from 'jquery'
import { useStore } from 'vuex'
import { onMounted, onUnmounted } from 'vue'
export default {
    name: "SettingComponent",
    setup() {
        const store = useStore();

        const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.id}`;
        let socket = null;
        let showPopup = ref(false);



        let newRecipient = ref("");
        let recipients = ref([]);
        let setting = ref();
        let selectedModel = ref();
        let noticeModel = ref();
        let selected = ref();
        let threshold = ref();
        let nodethreshold = ref();
        let selectnotice = ref();
        let recOpera = ref([]);

        const modelOptions = ref([
            { value: 1, label: '决策树' },
            { value: 2, label: '朴素贝叶斯' },
            { value: 3, label: '随机森林' },
            { value: 4, label: 'ZeroR' }
            // 可以根据需要添加更多的模型选项
        ]);
        const noticeOptions = ref([
            { value: 1, label: '电子邮件' },
            { value: 2, label: '系统弹窗' }
            // 可以根据需要添加更多的模型选项
        ]);
        function addRecipient() {
            const trimmedRecipient = newRecipient.value.trim();
            if (trimmedRecipient !== "") {
                recipients.value.push({ id: 0, email: trimmedRecipient });
                newRecipient.value = "";



                //
            }
        }

        function removeRecipient(recipient, index) {
            recipients.value.splice(index, 1);
            if (recipient.id != 0) recOpera.value.push({ id: recipient.id, email: recipient.email });

        }
        const refresh_setting = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/setting/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    setting.value = resp[0];

                    selectedModel.value = setting.value.ml;
                    selected.value = modelOptions.value.find(model => model.value === setting.value.ml).label;
                    selectnotice.value = noticeOptions.value.find(notice => notice.value === setting.value.notice).value;
                    threshold.value = setting.value.threshold;
                    nodethreshold.value = setting.value.node;
                    console.log("阈值", threshold.value);
                    console.log("选择的是", selected.value);
                    console.log("选择的是", selectnotice.value);
                    console.log("ce:", noticeOptions.value.find(notice => notice.value === selectnotice.value).value);
                }
            })
        }


        const refresh_recipient = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/recipient/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    recipients.value = resp;
                    console.log(recipients.value);

                }
            })
        }
        const update_setting = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/setting/update/",
                type: "post",
                data: {
                    ml: modelOptions.value.find(model => model.value == selectedModel.value).value,
                    threshold: threshold.value,
                    node: nodethreshold.value,
                    notice: noticeOptions.value.find(notice => notice.value == selectnotice.value).value
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {

                    console.log(resp);
                }
            })
        }
        refresh_setting();
        refresh_recipient();
        const add_recipient = (recipient) => {
            $.ajax({
                url: "http://127.0.0.1:3000/recipient/add/",
                type: "post",
                data: {
                    email: recipient.email,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);
                }
            })
        }

        const remove_recipient = (recipient) => {
            $.ajax({
                url: "http://127.0.0.1:3000/recipient/remove/",
                type: "post",
                data: {
                    id: recipient.id,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);
                }
            })
        }
        const update_recipient = () => {
            for (let i = 0; i < recOpera.value.length; i++) {
                remove_recipient(recOpera.value[i])

            }
            for (let i = 0; i < recipients.value.length; i++) {
                if (recipients.value[i].id === 0) {
                    add_recipient(recipients.value[i]);
                }
            }

            recOpera.value = "";
        }

        const saveChanges = () => {
            // 在这里处理保存修改的逻辑
            // 可以通过 recipients.value 获取已添加的通知接收者列表
            // 和其他输入框的值一起提交或处理
            update_setting();
            update_recipient();

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
            setting,
            newRecipient,
            recipients,
            selectedModel,
            threshold,
            selectnotice,
            noticeOptions,
            selected,
            noticeModel,
            modelOptions,
            recOpera,
            showPopup,
            nodethreshold,
            openPopup,
            closePopup,

            refresh_recipient,
            addRecipient,
            removeRecipient,
            saveChanges,
            refresh_setting,
            add_recipient,
            update_setting,
            remove_recipient,
            update_recipient,

        };
    }
};
</script>
  
<style scoped>
#recipientTags {
    display: flex;
    flex-wrap: wrap;
}

.recipientTag {
    display: flex;
    align-items: center;
    background-color: #f8f9fa;
    border: 1px solid #dee2e6;
    border-radius: 0.25rem;
    padding: 0.375rem 0.75rem;
    margin: 0.125rem;
}

.recipientTag span {
    margin-right: 0.5rem;
}

.removeRecipient {
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
}
</style>
  