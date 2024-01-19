<template>
    <div>
        <!-- 实时流量图表区域 -->
        <div class="card mb-4">
            <div class="card-header">
                实时流量曲线图
            </div>
            <div class="card-body">
                <canvas id="trafficChart"></canvas>
            </div>
        </div>

        <!-- 流量统计 -->
        <div class="card mb-4">
            <div class="card-header">
                流量统计
            </div>
            <div class="card-body">
                <canvas id="attackTypeChart"></canvas>
            </div>
        </div>
    </div>
</template>
  
<script>
import { ref, onMounted } from 'vue';
import Chart from 'chart.js';

export default {
    name: "DataCom",
    setup() {
        const trafficChart = ref(null);
        const attackTypeChart = ref(null);

        onMounted(() => {
            // 初始化图表
            initTrafficChart();
            initAttackTypeChart();

            // 模拟实时数据更新
            setInterval(() => {
                // 更新实时流量图表和流量统计图表数据
                updateTrafficChartData();
                updateAttackTypeChartData();
            }, 5000); // 每5秒更新一次数据
        });

        const initTrafficChart = () => {
            const ctx = document.getElementById('trafficChart').getContext('2d');
            trafficChart.value = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: [], // 时间轴标签
                    datasets: [
                        {
                            label: '入站流量',
                            borderColor: 'blue',
                            data: [],
                        },
                        {
                            label: '出站流量',
                            borderColor: 'green',
                            data: [],
                        },
                    ],
                },
            });
        };

        const initAttackTypeChart = () => {
            const ctx = document.getElementById('attackTypeChart').getContext('2d');
            attackTypeChart.value = new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: ['DDoS', 'SQL注入', 'XSS', '其他'],
                    datasets: [
                        {
                            data: [0, 0, 0, 0],
                            backgroundColor: ['#FF5733', '#FFD700', '#36A2EB', '#AAAAAA'],
                        },
                    ],
                },
            });
        };

        const updateTrafficChartData = () => {
            // 模拟获取实时流量数据（假设为随机数据）
            const inboundTraffic = Math.floor(Math.random() * 100);
            const outboundTraffic = Math.floor(Math.random() * 100);

            // 更新图表数据
            trafficChart.value.data.labels.push(new Date().toLocaleTimeString());
            trafficChart.value.data.datasets[0].data.push(inboundTraffic);
            trafficChart.value.data.datasets[1].data.push(outboundTraffic);

            // 限制显示的数据点数量，避免图表过长
            const maxDataPoints = 10;
            if (trafficChart.value.data.labels.length > maxDataPoints) {
                trafficChart.value.data.labels.shift();
                trafficChart.value.data.datasets[0].data.shift();
                trafficChart.value.data.datasets[1].data.shift();
            }

            // 更新图表
            trafficChart.value.update();
        };

        const updateAttackTypeChartData = () => {
            // 模拟获取流量统计数据（假设为随机数据）
            const ddosCount = Math.floor(Math.random() * 10);
            const sqlInjectionCount = Math.floor(Math.random() * 10);
            const xssCount = Math.floor(Math.random() * 10);
            const otherCount = Math.floor(Math.random() * 10);

            // 更新图表数据
            attackTypeChart.value.data.datasets[0].data = [ddosCount, sqlInjectionCount, xssCount, otherCount];

            // 更新图表
            attackTypeChart.value.update();
        };

        return {
            trafficChart,
            attackTypeChart,
        };
    },
};
</script>
  
<style>
/* 样式可以根据实际需求进行调整 */
.card {
    margin-bottom: 20px;
}
</style>
  