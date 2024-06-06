<template>
    <div><!-- On tables -->
        <div class="container mt-5">
            <h3 id="b-title">日志查询</h3>
            <div class="col-qw">
                <div class="card">
                    <div class="card-header">
                        <span style="font-size: 100%">流量历史记录</span>
                        <!-- 策略列表 -->
                        <table class="table table-bordered mt-4  table-hover">
                            <thead>

                                <tr>
                                    <td class="table-dark">编号</td>
                                    <td class="table-primary">监测时间</td>
                                    <td class="table-secondary">数据包数目</td>
                                    <td class="table-info">监测详情</td>
                                    <td class="table-danger">主要攻击类型</td>
                                    <td class="table-warning">警告类型</td>


                                </tr>
                            </thead>
                            <tbody>
                                <!-- 示例数据，实际数据应从后端动态获取并展示 -->

                                <!-- 示例数据，实际数据应从后端动态获取并展示 -->
                                <tr v-for="data in datas.records" :key="data.id">
                                    <td>{{ data.id }}</td>
                                    <td>{{ data.predicttime }}</td>
                                    <td> {{ data.packagenum }}</td>
                                    <td>{{ data.description }}</td>
                                    <td>{{ data.mainattack }}</td>

                                    <td
                                        :class="{ 'table-success': data.alerttype === '正常', 'table-warning': data.alerttype === '普通', 'table-danger': data.alerttype === '严重', 'table-secondary': data.alerttype === '轻微' }">
                                        {{ data.alerttype }}</td>
                                </tr>


                                <!-- 更多策略行可以继续添加 -->


                                <!-- 更多策略行可以继续添加 -->
                            </tbody>
                            <div class="pagination-container">
                                <ul class="pagination">
                                    <li v-if="datas.currentPage > 1" @click="changePage(datas.currentPage - 1)">
                                        <a href="#" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    <li v-for="page in pages" :key="page"
                                        :class="{ active: page === datas.currentPage }" @click="changePage(page)">
                                        <a href="#">{{ page }}</a>
                                    </li>


                                    <li v-if="datas.currentPage < datas.total"
                                        @click="changePage(datas.currentPage + 1)">
                                        <a href="#" aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>



                        </table>

                    </div>
                </div>
            </div>
        </div>
        <div class="container mt-3">
            <div class="card">
                <div class="card-header">
                    <!-- 表头 -->
                    <span style="font-size: 100%">系统历史记录</span>
                    <!-- 策略列表 -->
                    <table class="table table-striped table-hover">
                        <!-- 表头 -->
                        <thead>
                            <!-- 表头内容 -->
                            <tr>
                                <th>编号</th>
                                <th>创建时间</th>
                                <th>类型</th>
                                <th>详情</th>
                            </tr>
                        </thead>
                        <!-- 表体 -->
                        <tbody>
                            <!-- 示例数据，实际数据应从后端动态获取并展示 -->

                            <!-- 示例数据，实际数据应从后端动态获取并展示 -->
                            <tr v-for="operate in operates.records" :key="operate.id">
                                <td>{{ operate.id }}</td>
                                <td>{{ operate.createtime }}</td>
                                <td>{{ operate.type }}</td>
                                <td> {{ operate.description }}</td>
                            </tr>

                        </tbody>
                        <!-- 分页 -->
                        <div class="pagination-container">
                            <ul class="pagination">
                                <li v-if="operates.currentPage > 1" @click="changePage_o(operates.currentPage - 1)">
                                    <a href="#" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                                <li v-for="page in pages_o" :key="page"
                                    :class="{ active: page === operates.currentPage }" @click="changePage_o(page)">
                                    <a href="#">{{ page }}</a>
                                </li>


                                <li v-if="operates.currentPage < operates.total"
                                    @click="changePage_o(operates.currentPage + 1)">
                                    <a href="#" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
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

import { ref, } from 'vue'
import $ from 'jquery'
import { useStore } from 'vuex'
import { onMounted, onUnmounted } from 'vue'
export default {
    name: "RecordComponent",
    components: {

    },
    setup() {



        // 如果您有更多的开关和标签，可以继续按照这种模式进行扩展。

        const store = useStore();
        const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.id}`;
        let socket = null;
        let showPopup = ref(false);
        let datas = ref({
            records: [],
            total: 0,
            pageSize: 3, // 假设默认页面大小为 10
            currentPage: 1,
        });
        let operates = ref({
            records: [],
            total: 0,
            pageSize: 3, // 假设默认页面大小为 10
            currentPage: 1,
        });


        let pages = ref([]);
        let pages_o = ref([]);
        const refresh_data = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/record/data/getlist/",
                type: "get",
                data: {
                    pageNum: datas.value.currentPage,
                    pageSize: datas.value.pageSize,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);
                    datas.value.records = resp.records;
                    datas.value.total = Math.ceil(resp.total / datas.value.pageSize);
                    pages.value = Array.from({ length: datas.value.total }, (_, index) => index + 1);

                    // Optionally, you can update the total pages or other pagination information here
                },
            });
        };
        const refresh_operate = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/record/operate/getlist/",
                type: "get",
                data: {
                    pageNum: operates.value.currentPage,
                    pageSize: operates.value.pageSize,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);
                    operates.value.records = resp.records;
                    operates.value.total = Math.ceil(resp.total / operates.value.pageSize);
                    pages_o.value = Array.from({ length: operates.value.total }, (_, index) => index + 1);

                    // Optionally, you can update the total pages or other pagination information here
                },
            });
        };
        const changePage = (page) => {
            if (page >= 1 && page <= datas.value.total) {
                datas.value.currentPage = page;
                // 触发数据刷新等操作

            }
            refresh_data();
        };
        const changePage_o = (page) => {
            if (page >= 1 && page <= datas.value.total) {
                operates.value.currentPage = page;
                // 触发数据刷新等操作
            }
            refresh_operate();
        };


        refresh_data();
        refresh_operate();




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


            pages,
            pages_o,
            datas,
            operates,
            openPopup,
            closePopup,
            showPopup,
            changePage,
            changePage_o,
            refresh_operate,
            refresh_data,
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


</style>