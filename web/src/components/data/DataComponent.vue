<template>
    <div class="container mt-4">
        <div class="row">
            <!-- 左侧按钮列 -->
            <div class="col-md-2">
                <button :class="is_capture ? 'btn btn-danger' : 'btn btn-primary'" @click="toggleCapture">
                    {{ is_capture ? "停止收集" : "开始本地收集" }}
                </button>
                <button :class="is_predict ? 'btn btn-danger' : 'btn btn-primary'" @click="togglePredict">
                    {{ is_predict ? "停止监测" : "开始监测" }}
                </button>

            </div>
            <!-- 右侧内容列 -->
            <div class="col-md-10">
                <!-- 实时流量图表区域 -->
                <div class="card mb-4">
                    <div class="card-header">
                        实时流量曲线图
                    </div>
                    <div class="card-body ">
                        <canvas id="trafficChart" width="400" height="100"></canvas>
                    </div>
                </div>

                <!-- 流量统计 -->
                <div class="card mb-3">
                    <div class="card-header">
                        流量类型统计
                    </div>
                    <div class="row">
                        <!-- 左侧：饼状图 -->
                        <div class="col-md-4">
                            <canvas id="pieChart" width="200" height="80"></canvas>
                        </div>

                        <!-- 右侧：柱状图 -->
                        <div class="col-md-8">
                            <canvas id="barChart" width="200" height="80"></canvas>
                        </div>
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

import $ from 'jquery'
import { useStore } from 'vuex'
import { ref } from 'vue'
import { onMounted, onUnmounted } from 'vue'
import Chart from 'chart.js/auto';  // 确保正确导入Chart.js

export default {
    name: "DataComponent",
    setup() {
        // 在 setup 中使用 ref 创建响应式数据
        const store = useStore();
        let showPopup = ref(false);
        let is_capture = ref(false);
        let is_predict = ref(false);
        let trafficChart = null;
        let pieChart = null;
        let barChart = null;
        const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.id}`;
        let socket = null;
        let mlType = "tree"; // 您可以根据需要设置初始值
        let button_state = ref();
        const modelOptions = ref([
            { value: 1, label: 'tree' },
            { value: 2, label: 'nb' },
            { value: 3, label: 'rf' },
            { value: 4, label: 'zero' }
            // 可以根据需要添加更多的模型选项
        ]);
        onMounted(() => {
            refresh_button();
            refresh_setting();
            pieChart = new Chart(document.getElementById('pieChart').getContext('2d'), {
                type: 'pie',
                data: {
                    labels: ['Normal', 'Dos', 'Probing', "R2l", "U2R"],
                    datasets: [{
                        data: [],
                        backgroundColor: ['red', 'blue', 'green', 'orange', 'purple'],
                    }],
                },
                options: {
                    // 根据需要设置其他图表选项
                    tooltips: {
                        callbacks: {
                            label: function (tooltipItem, data) {
                                var dataset = data.datasets[tooltipItem.datasetIndex];
                                var total = dataset.data.reduce(function (previousValue, currentValue) {
                                    return previousValue + currentValue;
                                });
                                var currentValue = dataset.data[tooltipItem.index];
                                var percentage = parseFloat((currentValue / total * 100).toFixed(1));
                                return percentage + '%';
                            },
                        },
                    },
                },
            });

            // 柱状图
            barChart = new Chart(document.getElementById('barChart').getContext('2d'), {
                type: 'bar',
                data: {
                    labels: ["1", "2", "3"],
                    datasets: [
                        {
                            label: 'Normal',
                            backgroundColor: 'red',
                            data: [],
                        },
                        {
                            label: "Dos",
                            backgroundColor: "blue",
                            data: [],
                        }
                        , {
                            label: "Probing",
                            backgroundColor: "green",
                            data: []

                        },
                        {
                            label: "R2l",
                            backgroundColor: "orange",
                            data: []
                        },
                        {
                            label: "U2R",
                            backgroundColor: "purple",
                            data: [],
                        }

                    ],
                },
                options: {
                    // 根据需要设置其他图表选项
                },
            });
            trafficChart = new Chart(document.getElementById('trafficChart').getContext('2d'), {
                type: 'line',
                data: {
                    labels: [],
                    datasets: [{
                        label: '实时流量曲线',
                        borderColor: 'rgb(75, 192, 192)',
                        data: [],
                    }],
                },
                options: {
                    scales: {
                        x: [{
                            type: 'linear',
                            position: 'bottom',
                            ticks: {
                                stepSize: 5, // 适应你的需求
                            },
                        }],
                        y: [{
                            type: 'linear',
                            position: 'left',
                            ticks: {
                                beginAtZero: true,
                                stepSize: 10, // 适应你的需求
                            },
                        }],
                    },
                },
            });

            socket = new WebSocket(socketUrl);
            socket.onopen = () => {
                console.log("connected!");
            }
            socket.onmessage = msg => {
                const data = JSON.parse(msg.data);
                if (Array.isArray(data)) {
                    // 变量是数组
                    updateTrafficChart(data[5]);
                    updatePieChart(data);
                    updateBarChart(data);

                    // 在这里你可以处理数组
                }
                else if (data == true) {
                    openPopup();
                }
                else {
                    updateTrafficChart(data);
                }

                console.log(data);
            }

            socket.onclose = () => {
                console.log("disconnected!");
            }



        });

        onUnmounted(() => {
            if (trafficChart) {
                trafficChart.destroy();
            }
            if (pieChart) {
                pieChart.destroy();
            }
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
        const togglePredict = () => {
            // 切换 is_predict 的值

            is_predict.value = !is_predict.value;
            update_predict();
            // 根据 is_capture 调用相应的方法
            if (is_predict.value) {
                if (is_capture.value) toggleCapture();
                handleButtonClickPredict();
                console.log("开始1");
            } else {

                stopButtonClickPredict();
            }
        }

        const toggleCapture = () => {
            // 切换 is_capture 的值

            is_capture.value = !is_capture.value;
            console.log(is_capture.value)
            // 根据 is_capture 调用相应的方法
            update_capture();
            if (is_capture.value) {
                if (is_predict.value) togglePredict();
                handleButtonClick();
                console.log("开始1");
            } else {

                stopButtonClick();
            }
        }
        const refresh_button = () => {

            $.ajax({
                url: "http://127.0.0.1:3000/button/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {

                    is_capture.value = resp[0].state === 0 ? false : true;
                    is_predict.value = resp[1].state === 0 ? false : true;
                    console.log(resp);

                }
            })
        }
        const update_capture = () => {

            $.ajax({
                url: "http://127.0.0.1:3000/button/update/",
                type: "post",
                data: {
                    id: 1,
                    state: is_capture.value === false ? 0 : 1,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {


                    console.log(resp);

                }
            })
        }
        const update_predict = () => {

            $.ajax({
                url: "http://127.0.0.1:3000/button/update/",
                type: "post",
                data: {
                    id: 2,
                    state: is_predict.value == false ? 0 : 1,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {


                    console.log(resp);

                }
            })
        }
        const handleButtonClick = () => {

            $.ajax({
                url: "http://127.0.0.1:3000/api/capture/start",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);

                }
            })
        }

        const handleButtonClickPredict = () => {

            $.ajax({
                url: `http://127.0.0.1:3000/api/ml/start/${mlType}`,
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);

                }
            })
        }

        const stopButtonClick = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/api/capture/stop",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);

                }
            })
        }
        const stopButtonClickPredict = () => {
            $.ajax({
                url: `http://127.0.0.1:3000/api/ml/stop/${mlType}`,
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);

                }
            })
        }

        const updateTrafficChart = (packetSize) => {
            const labels = trafficChart.data.labels;
            const data = trafficChart.data.datasets[0].data;

            labels.push(labels.length);
            data.push(packetSize);

            if (labels.length > 10) { // 控制显示的数据点数量
                labels.shift();
                data.shift();
            }

            trafficChart.update();
        };

        const updatePieChart = (data) => {
            pieChart.data.datasets[0].data = data.slice(0, 5); // 类型数量
            // 更新图表
            pieChart.update();


        };

        const updateBarChart = (data) => {
            if (barChart.data.datasets[0].data.length >= 3) {
                barChart.data.datasets[0].data.shift();
                barChart.data.datasets[1].data.shift();
                barChart.data.datasets[2].data.shift();
                barChart.data.datasets[3].data.shift();
                barChart.data.datasets[4].data.shift();
            }
            barChart.data.datasets[0].data.push(data[0]);

            barChart.data.datasets[1].data.push(data[1]);

            barChart.data.datasets[2].data.push(data[2]);

            barChart.data.datasets[3].data.push(data[3]);

            barChart.data.datasets[4].data.push(data[4]);
            // 更新图表
            barChart.update();


        };

        const refresh_setting = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/setting/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    let t = resp[0].ml;
                    mlType = modelOptions.value.find(model => model.value === t).label;
                    console.log(mlType);
                }
            })
        };



        // 将数据和方法返回给模板
        return {
            is_capture,
            is_predict,
            trafficChart,
            pieChart,
            barChart,
            mlType,
            button_state,
            showPopup,
            modelOptions,
            openPopup,
            closePopup,
            refresh_button,
            updateBarChart,
            handleButtonClick,
            stopButtonClick,
            toggleCapture,
            togglePredict,
            updateTrafficChart,
            updatePieChart,
            stopButtonClickPredict,
            handleButtonClickPredict,
            update_capture,
            update_predict,
            refresh_setting,

        };
    },
};
</script>

<style scoped>
/* 可以在这里添加样式，以定制按钮或其他元素的外观 */
button {
    margin-top: 30px;
    /* 或者设置合适的间距值 */
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