<template>
  
  <div class="mt-5">
        <h3>编辑/添加策略</h3>
        <form>
            <div class="form-group">
                <label for="policyName">策略名称</label>
                <input type="text" class="form-control" id="policyName" placeholder="输入策略名称">
            </div>
            <div class="form-group">
                <label for="policyDescription">策略描述</label>
                <textarea class="form-control" id="policyDescription" rows="3" placeholder="输入策略描述"></textarea>
            </div>
            <div class="form-group">
                <label for="policyStatus">策略状态</label>
                <select class="form-control" id="policyStatus">
                    <option>启用</option>
                    <option>禁用</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">保存</button>
        </form>
    </div>
</template>

<script>
export default {
    name:"AddNewDefenceView",
    components:{


    }
}
</script>

<style scoped>

</style>