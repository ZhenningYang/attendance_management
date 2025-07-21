<template>
  <div class="detail-container" v-if="employee">
    <button class="back-btn" @click="$emit('back')">返回</button>
    <h2>{{ displayName }} 的考勤详情</h2>
    <div class="info">
      <span>有效打卡天数：{{ validDays }}</span>
    </div>
    <h3>考勤明细</h3>
    <table class="attendance-table">
      <thead>
        <tr>
          <th>日期</th>
          <th>签到时间</th>
          <th>签退时间</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="rec in records" :key="rec.id">
          <td>{{ rec.signDate }}</td>
          <td>{{ rec.signInTime ? rec.signInTime.replace('T', ' ') : '-' }}</td>
          <td>{{ rec.signOutTime ? rec.signOutTime.replace('T', ' ') : '-' }}</td>
        </tr>
      </tbody>
    </table>
    <el-pagination
      v-if="total > 0"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      layout="total, prev, pager, next, jumper"
      @current-change="handlePageChange"
      style="margin-top: 12px; text-align: center;"
    />
  </div>
</template>

<script>
import { getAttendanceRecordsPage, getAttendanceValidDays, getEmployeeName } from '../api';

export default {
  props: ['employee'],
  data() {
    return {
      records: [],
      validDays: 0,
      displayName: '',
      // 分页相关
      currentPage: 1,
      pageSize: 10,
      total: 0
    }
  },
  mounted() {
    this.fetchRecordsPage();
    this.fetchValidDays();
    this.fetchDisplayName();
  },
  watch: {
    employee: {
      handler() {
        this.fetchRecordsPage();
        this.fetchValidDays();
        this.fetchDisplayName();
      },
      deep: true
    }
  },
  methods: {
    fetchRecordsPage(page = 1) {
      if (!this.employee || !this.employee.id) return;
      getAttendanceRecordsPage(this.employee.id, page - 1, this.pageSize)
        .then(res => {
          this.records = Array.isArray(res.content)
            ? res.content.map(r => ({ ...r, jobNumber: r.employeeId }))
            : [];
          this.total = res.totalElements;
          this.currentPage = page;
        });
    },
    handlePageChange(page) {
      this.fetchRecordsPage(page);
    },
    fetchValidDays() {
      if (!this.employee || !this.employee.id) return;
      getAttendanceValidDays(this.employee.id)
        .then(res => {
          this.validDays = res;
        });
    },
    fetchDisplayName() {
      if (!this.employee || !this.employee.id) {
        this.displayName = '';
        return;
      }
      getEmployeeName(this.employee.id)
        .then(res => {
          this.displayName = res || this.employee.id;
        })
        .catch(() => {
          this.displayName = this.employee.id;
        });
    }
  }
}
</script>

<style scoped>
.detail-container {
  max-width: 600px;
  margin: 40px auto;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.08);
  padding: 32px 24px 24px 24px;
}
.back-btn {
  margin-bottom: 16px;
  background: #409eff;
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 6px 16px;
  cursor: pointer;
}
.info {
  margin-bottom: 18px;
  font-size: 16px;
  color: #409eff;
}
.attendance-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 12px;
}
.attendance-table th, .attendance-table td {
  border: 1px solid #eee;
  padding: 8px 6px;
  text-align: center;
}
.attendance-table th {
  background: #f5f7fa;
}
</style>
