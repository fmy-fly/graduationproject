<template>
  <div>
    <h1>设备拓扑可视化</h1>
    <div class="device-container">
      <!-- 设备节点 -->
      <div v-for="device in devices" :key="device.id" class="device" @click="selectDevice(device)">
        {{ device.name }}
      </div>
    </div>

    <!-- 配置面板 -->
    <div v-if="selectedDevice">
      <h2>配置 {{ selectedDevice.name }}</h2>
      <form @submit.prevent="saveConfig">
        <label>
          设备名称:
          <input v-model="selectedDevice.name" required />
        </label>
        <button type="submit">保存</button>
      </form>
    </div>
  </div>
</template>
  
<script>
export default {
  name: "NodesShowView",
  data() {
    return {
      devices: [
        { id: 1, name: '设备1' },
        { id: 2, name: '设备2' },
        // 添加更多设备
      ],
      selectedDevice: null
    };
  },
  methods: {
    selectDevice(device) {
      this.selectedDevice = device;
    },
    saveConfig() {
      // 这里可以添加保存配置的逻辑，例如发送到后端
      console.log('保存配置:', this.selectedDevice);
    }
  }
};
</script>
  
<style scoped>
.device {
  width: 100px;
  height: 100px;
  background-color: blue;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  cursor: pointer;
  margin: 10px;
}

.device-container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
}
</style>
  