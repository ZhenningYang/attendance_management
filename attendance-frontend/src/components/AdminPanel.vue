<template>
  <el-card class="admin-panel">
    <div style="display:flex;justify-content:space-between;align-items:center;">
      <h2>管理面板</h2>
    </div>
    <div style="margin-bottom: 12px; font-size: 16px; color: #409eff;">
      当前打卡日期：{{ currentDate }}
    </div>
    <div style="margin-bottom:18px;">
      <el-button type="danger" @click="initData">初始化数据</el-button>
      <el-date-picker v-model="newDate" type="date" placeholder="选择考勤日期" style="margin-left:12px;" />
      <el-button type="primary" @click="setDate" style="margin-left:8px;">切换日期</el-button>
      <!-- 移除当前考勤日期显示 -->
    </div>
    <el-divider>员工管理</el-divider>
    <div style="margin-bottom:18px; display: flex; align-items: center; justify-content: center; gap: 12px;">
      <el-input v-model="newEmployeeJobNumber" placeholder="请输入工号" style="width:140px;" />
      <el-input v-model="newEmployeeName" placeholder="请输入员工姓名" style="width:160px;" />
      <el-button type="success" @click="addEmployee">新增员工</el-button>
    </div>
    <el-table :data="pagedEmployees" stripe style="width: 100%;margin-bottom:18px;">
      <el-table-column prop="jobNumber" label="工号" width="120" />
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="validDays" label="有效打卡天数" width="120" />
    </el-table>
    <el-pagination
      :current-page="employeePage"
      :page-size="employeePageSize"
      :total="(employees ? employees.length : 0)"
      layout="total, prev, pager, next, jumper"
      @current-change="handleEmployeePageChange"
      style="margin-bottom:18px; text-align:center;"
    />
    <el-divider>考勤明细（{{ currentDate }}）</el-divider>
    <el-table :data="pagedRecords" stripe style="width: 100%">
      <el-table-column prop="jobNumber" label="工号" width="120" />
      <el-table-column label="员工姓名" width="160">
        <template #default="scope">
          {{ getEmployeeName(scope.row.jobNumber) }}
        </template>
      </el-table-column>
      <el-table-column prop="signDate" label="日期" width="160" />
      <el-table-column prop="signInTime" label="签到时间" width="220" />
      <el-table-column prop="signOutTime" label="签退时间" width="220" />
    </el-table>
    <el-pagination
      :current-page="recordPage"
      :page-size="recordPageSize"
      :total="(records ? records.length : 0)"
      layout="total, prev, pager, next, jumper"
      @current-change="handleRecordPageChange"
      style="margin-top:12px; text-align:center;"
    />
    <el-alert v-if="msg" :title="msg" type="success" show-icon style="margin-top:12px;" />
  </el-card>
</template>

<script>
import {
  adminInitData,
  adminGetEmployees,
  adminAddEmployee,
  adminGetAttendanceRecordsByDate,
  adminSetDate,
  adminGetCurrentDate
} from '../api';
import dayjs from 'dayjs';

export default {
  data() {
    return {
      msg: '',
      employees: [], // 保证为数组
      newEmployeeJobNumber: '',
      newEmployeeName: '',
      records: [],   // 保证为数组
      currentDate: '',
      newDate: '',
      // 分页相关
      employeePage: 1,
      employeePageSize: 100,
      recordPage: 1,
      recordPageSize: 100
    }
  },
  computed: {
    pagedEmployees() {
      const start = (this.employeePage - 1) * this.employeePageSize;
      return (this.employees || []).slice(start, start + this.employeePageSize);
    },
    pagedRecords() {
      const start = (this.recordPage - 1) * this.recordPageSize;
      return (this.records || []).slice(start, start + this.recordPageSize);
    }
  },
  mounted() {
    this.fetchCurrentDate();
    this.fetchEmployees();
    this.fetchRecords();
  },
  methods: {
    logout() {
      localStorage.removeItem('user');
      this.$router.replace('/login');
    },
    fetchCurrentDate() {
      adminGetCurrentDate().then(res => {
        this.currentDate = res;
        this.newDate = res;
      });
    },
    fetchEmployees() {
      adminGetEmployees().then(res => {
        console.log('员工数据接口返回:', res);
        this.employees = Array.isArray(res) ? res : [];
        this.employeePage = 1;
        console.log('this.employees:', this.employees);
      });
    },
    fetchRecords() {
      adminGetAttendanceRecordsByDate(this.currentDate).then(res => {
        const arr = Array.isArray(res) ? res : [];
        this.records = arr.map(r => ({
          ...r,
          jobNumber: r.employeeId
        }));
        this.recordPage = 1;
        console.log('this.records:', this.records);
      });
    },
    initData() {
      adminInitData().then(() => {
        this.msg = '初始化成功';
        this.fetchEmployees();
        this.fetchRecords();
        setTimeout(() => this.msg = '', 2000);
      }).catch(() => {
        this.msg = '初始化失败';
      });
    },
    setDate() {
      if (!this.newDate) return;
      const dateStr = dayjs(this.newDate).format('YYYY-MM-DD');
      adminSetDate(dateStr).then(() => {
        this.msg = '切换日期成功';
        this.currentDate = dateStr;
        this.fetchRecords();
        setTimeout(() => this.msg = '', 2000);
      }).catch(() => {
        this.msg = '切换日期失败';
      });
    },
    addEmployee() {
      if (!this.newEmployeeJobNumber.trim()) {
        this.msg = '工号不能为空';
        return;
      }
      if (!this.newEmployeeName.trim()) {
        this.msg = '姓名不能为空';
        return;
      }
      adminAddEmployee({ jobNumber: this.newEmployeeJobNumber, name: this.newEmployeeName })
        .then(() => {
          this.msg = '新增员工成功';
          this.newEmployeeJobNumber = '';
          this.newEmployeeName = '';
          this.fetchEmployees();
          this.fetchRecords(); // 新增：同步刷新考勤明细
          setTimeout(() => this.msg = '', 2000);
        })
        .catch(err => {
          this.msg = err.response && err.response.data ? err.response.data : '新增员工失败';
        });
    },
    getEmployeeName(jobNumber) {
      const emp = this.employees.find(e => e.jobNumber === jobNumber);
      return emp ? emp.name : jobNumber;
    },
    handleEmployeePageChange(page) {
      this.employeePage = page;
    },
    handleRecordPageChange(page) {
      this.recordPage = page;
    }
  },
  watch: {
    currentDate() {
      this.fetchRecords();
    }
  }
}
</script>

<style scoped>
.admin-panel {
  max-width: 900px;
  margin: 40px auto;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.08);
  padding: 32px 24px 24px 24px;
  text-align: center;
}
:deep(.el-table th), :deep(.el-table td) {
  white-space: nowrap;
}
</style> 