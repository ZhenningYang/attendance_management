<template>
  <div id="app">
    <StagewiseToolbar :config="stagewiseConfig" />
    <header class="main-header">
      <img src="./assets/logo.png" class="logo" />
      <span class="title">智能考勤系统</span>
    </header>
    <div class="main-content">
      <div class="nav-bar">
        <button :class="{active: page==='list'}" @click="page='list'">员工打卡</button>
        <button :class="{active: page==='admin'}" @click="page='admin'">管理面板</button>
      </div>
      <div class="content-card">
        <EmployeeList v-if="page==='list'" />
        <AdminPanel v-else />
      </div>
    </div>
  </div>
</template>

<script>
import EmployeeList from './components/EmployeeList.vue';
import AdminPanel from './components/AdminPanel.vue';
import { adminGetCurrentDate } from './api';
import { StagewiseToolbar } from '@stagewise/toolbar-vue';
import VuePlugin from '@stagewise-plugins/vue';

export default {
  components: {
    EmployeeList,
    AdminPanel,
    StagewiseToolbar
  },
  data() {
    return {
      page: 'list',
      currentDate: ''
    }
  },
  mounted() {
    this.fetchCurrentDate();
  },
  methods: {
    async fetchCurrentDate() {
      try {
        const res = await adminGetCurrentDate();
        this.currentDate = res;
      } catch (e) {
        this.currentDate = '获取失败';
      }
    }
  },
  computed: {
    stagewiseConfig() {
      return { plugins: [VuePlugin] }
    }
  }
}
</script>

<style>
body {
  background: linear-gradient(135deg, #e0eafc 0%, #cfdef3 100%);
  min-height: 100vh;
  margin: 0;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  color: #2c3e50;
  min-height: 100vh;
}
.main-header {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 48px 0 24px 0;
  background: transparent;
}
.logo {
  height: 64px;
  margin-right: 24px;
}
.title {
  font-size: 3rem;
  font-weight: bold;
  color: #409eff;
  letter-spacing: 2px;
}
.main-content {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.nav-bar {
  margin-bottom: 32px;
}
.nav-bar button {
  padding: 14px 32px;
  border: none;
  border-radius: 6px;
  font-size: 20px;
  cursor: pointer;
  margin: 0 18px 0 18px;
  background: #f5f7fa;
  color: #409eff;
  transition: background 0.2s;
  box-shadow: 0 2px 8px rgba(64,158,255,0.10);
}
.nav-bar button.active, .nav-bar button:hover {
  background: #409eff;
  color: #fff;
}
.content-card {
  background: #fff;
  border-radius: 18px;
  box-shadow: 0 8px 32px rgba(64,158,255,0.13);
  padding: 56px 48px;
  min-width: 600px;
  max-width: 1600px;
  margin-bottom: 60px;
  font-size: 1.25rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
:deep(.el-card__body), :deep(.el-table), :deep(.el-table th), :deep(.el-table td) {
  font-size: 1.15rem !important;
}
:deep(.el-button) {
  font-size: 1.1rem !important;
  padding: 10px 24px !important;
}
</style>
