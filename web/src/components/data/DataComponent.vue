<template>
    <div class="container mt-4">
        <div class="row">
            <!-- 左侧按钮列 -->
            <div class="col-md-2">
                <button :class="is_capture ? 'btn btn-danger' : 'btn btn-primary'" @click="toggleCapture">
                    {{ is_capture ? "停止收集" : "开始本地收集" }}
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
                        <canvas id="trafficChart" width="400" height="130"></canvas>
                    </div>
                </div>

                <!-- 流量统计 -->
                <div class="card mb-4">
                    <div class="card-header">
                        流量统计
                    </div>
                    <div class="card-body">
                        <canvas id="attackTypeChart" width="400" height="200"></canvas>
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
        let is_capture = ref(false);
        let trafficChart = null;
        const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.id}`;
        let socket = null;
        onMounted(() => {


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
                updateTrafficChart(data);
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
            if (socket) {
                socket.close();
            }
        })

        const toggleCapture = () => {
            // 切换 is_capture 的值

            is_capture.value = !is_capture.value;
            console.log(is_capture.value)
            // 根据 is_capture 调用相应的方法
            if (is_capture.value) {
                handleButtonClick();
                console.log("开始1");
            } else {

                stopButtonClick();
            }
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








        // 将数据和方法返回给模板
        return {
            is_capture,
            trafficChart,
            handleButtonClick,
            stopButtonClick,
            toggleCapture,
            updateTrafficChart

        };
    },
};
</script>

<style scoped>
/* 可以在这里添加样式，以定制按钮或其他元素的外观 */
</style>