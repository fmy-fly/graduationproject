<template>
    <div><!-- On tables -->
        <div class="container mt-5">
            <h2 id="b-title">日志查询</h2>
            <div class="col-qw">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-header">
                        <span style="font-size: 130%">历史记录</span>
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
                                <tr>
                                    <td>编号</td>
                                    <td>监测时间</td>
                                    <td>数据包数目</td>
                                    <td>监测详情</td>
                                    <td>主要攻击类型</td>
                                    <td>警告类型</td>
                                </tr>

                                <!-- 更多策略行可以继续添加 -->


                                <!-- 更多策略行可以继续添加 -->
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

export default {
    name: "RecordComponent"
}
</script>

<style></style>