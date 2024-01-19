<template>
    <div class="container mt-4">
        <div class="row">
            <!-- 左侧按钮列 -->
            <div class="col-md-2">
                <button :class="is_capture ? 'btn btn-danger' : 'btn btn-primary'" @click="toggleCapture">
                    {{ is_capture ? "停止收集" : "开始收集" }}
                </button>
            </div>
            <!-- 右侧内容列 -->
            <div class="col-md-10">
                <!-- 实时流量图表区域 -->
                <div class="card mb-4">
                    <div class="card-header">
                        实时流量曲线图
                    </div>
                    <div class="card-body">
                        <canvas id="trafficChart" width="400" height="260"></canvas>
                    </div>
                </div>

                <!-- 流量统计 -->
                <div class="card mb-4">
                    <div class="card-header">
                        流量统计
                    </div>
                    <div class="card-body">
                        <canvas id="attackTypeChart" width="400" height="260"></canvas>
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
export default {
    name: "DataComponent",
    setup() {
        // 在 setup 中使用 ref 创建响应式数据
        const store = useStore();
        let is_capture = ref(false);
        const toggleCapture = () => {
            // 切换 is_capture 的值

            is_capture.value = !is_capture.value;
            console.log(is_capture.value)
            // 根据 is_capture 调用相应的方法
            if (is_capture.value) {
                handleButtonClick();
            } else {
                console.log("开始停止");
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
        // 将数据和方法返回给模板
        return {
            is_capture,
            handleButtonClick,
            stopButtonClick,
            toggleCapture
        };
    },
};
</script>

<style scoped>
/* 可以在这里添加样式，以定制按钮或其他元素的外观 */
</style>