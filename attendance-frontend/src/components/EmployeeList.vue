<template>
  <div class="employee-container">
    <el-card>
      <div style="display:flex;justify-content:space-between;align-items:center;">
        <h2>员工打卡</h2>
      </div>
      <div style="margin-bottom: 12px; font-size: 16px; color: #409eff;">
        当前打卡日期：{{ currentDate }}
      </div>
      <div style="margin: 18px 0;">
        <el-form :inline="true" @submit.prevent>
          <el-form-item label="工号">
            <el-input v-model="jobNumber" placeholder="请输入工号" style="width: 180px;" />
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="signIn" :disabled="!jobNumber">签到</el-button>
            <el-button type="warning" @click="signOut" :disabled="!jobNumber" style="margin-left:12px;">签退</el-button>
            <el-button @click="showDetail=true" :disabled="!jobNumber" style="margin-left:12px;">查看打卡明细</el-button>
          </el-form-item>
        </el-form>
      </div>
      <el-alert v-if="lastMsg" :title="lastMsg" type="success" show-icon style="margin-bottom:12px;" />
      <EmployeeDetail v-if="showDetail" :employee="{id: jobNumber, name: employeeName, validDays: validDays}" @back="showDetail=false" />
    </el-card>
  </div>
</template>

<script>
import { signIn, signOut, getAttendanceValidDays, getEmployeeName } from '../api';
import EmployeeDetail from './EmployeeDetail.vue';
import { ElMessage } from 'element-plus';

export default {
  components: { EmployeeDetail },
  data() {
    return {
      jobNumber: '',
      employeeName: '',
      validDays: 0,
      showDetail: false,
      lastMsg: '',
      currentDate: ''
    }
  },
  mounted() {
    this.fetchCurrentDate();
  },
  methods: {
    fetchCurrentDate() {
      import('../api').then(api => {
        api.adminGetCurrentDate().then(res => {
          this.currentDate = res;
        });
      });
    },
    signIn() {
      signIn(this.jobNumber)
        .then(res => {
          ElMessage.success(res);
          this.lastMsg = res;
          this.fetchValidDays();
          this.fetchEmployeeName();
        })
        .catch(() => {
          ElMessage.error('签到失败');
        });
    },
    signOut() {
      signOut(this.jobNumber)
        .then(res => {
          ElMessage.success(res);
          this.lastMsg = res;
          this.fetchValidDays();
          this.fetchEmployeeName();
        })
        .catch(err => {
          if (err && err.response && err.response.data) {
            ElMessage.error(err.response.data);
            this.lastMsg = err.response.data;
          } else {
            ElMessage.error('签退失败');
          }
        });
    },
    fetchValidDays() {
      getAttendanceValidDays(this.jobNumber)
        .then(res => {
          this.validDays = res;
        });
    },
    fetchEmployeeName() {
      this.employeeName = '';
      getEmployeeName(this.jobNumber)
        .then(res => {
          this.employeeName = res;
        })
        .catch(() => {
          this.employeeName = '';
        });
    }
  }
}
</script>

<style scoped>
.employee-container {
  max-width: 700px;
  margin: 40px auto;
}
</style>
