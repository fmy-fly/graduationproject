<template>
  <footer class="bg-dark text-white py-4 fixed-bottom">
    <div class="container">
      <div class="row justify-content-center align-items-center text-center">
        <!-- 版权信息 -->
        <div class="col">
          <p class="mb-1">&copy; 基于流量特征的物联网攻击检测与防御系统的设计与实现,属<a style="text-decoration: none"
              href="http://www.suda.edu.cn/" target="_blank">苏州大学</a>版权所有</p>
        </div>

        <!-- 链接 -->
        <div class="col info">
          <a href="#" class="text-white">关于我们</a>
        </div>
      </div>
    </div>
  </footer>
</template>
  
<script>
export default {
  name: "FooterComponent",
  components: {}
}
</script>
  
<style scoped>
/* 如果你还想调整宽度或其他样式，可以在此添加更多样式 */
div.info {
  /* 例如，如果你想调整与其他元素之间的距离，可以使用 margin */
  margin-left: 20px;
}
</style>