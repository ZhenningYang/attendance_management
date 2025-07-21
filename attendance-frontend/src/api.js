import axios from 'axios';
  
const BASE_URL = 'http://localhost:8081';

// 设置axios全局默认带cookie
axios.defaults.withCredentials = true;

// 管理员相关
export function adminGetEmployees() {
  return axios.get(`${BASE_URL}/admin/employees`).then(res => res.data);
}

export function adminAddEmployee(data) {
  return axios.post(`${BASE_URL}/admin/employee`, data).then(res => res.data);
}

export function adminInitData() {
  return axios.post(`${BASE_URL}/admin/init`).then(res => res.data);
}

export function adminGetAttendanceRecordsByDate(date) {
  return axios.get(`${BASE_URL}/admin/attendance/records`, { params: { date } }).then(res => res.data);
}

export function adminSetDate(date) {
  return axios.post(`${BASE_URL}/admin/set-date`, null, { params: { date } }).then(res => res.data);
}

export function adminGetCurrentDate() {
  return axios.get(`${BASE_URL}/admin/current-date`).then(res => res.data);
}

// 员工相关
export function signIn(jobNumber) {
  return axios.post(`${BASE_URL}/attendance/sign-in`, null, { params: { jobNumber } }).then(res => res.data);
}

export function signOut(jobNumber) {
  return axios.post(`${BASE_URL}/attendance/sign-out`, null, { params: { jobNumber } }).then(res => res.data);
}

export function getAttendanceRecords(jobNumber) {
  return axios.get(`${BASE_URL}/attendance/records`, { params: { jobNumber } }).then(res => res.data);
}

export function getAttendanceValidDays(jobNumber) {
  return axios.get(`${BASE_URL}/attendance/valid-days`, { params: { jobNumber } }).then(res => res.data);
}

export function getEmployeeName(jobNumber) {
  return axios.get(`${BASE_URL}/admin/employee-name`, { params: { jobNumber } }).then(res => res.data);
}

// 分页获取员工列表
export function getEmployeePage(page = 0, size = 10) {
  return axios.get(`${BASE_URL}/employees/page`, { params: { page, size } }).then(res => res.data);
}
// 分页获取考勤明细
export function getAttendanceRecordsPage(jobNumber, page = 0, size = 10) {
  return axios.get(`${BASE_URL}/attendance/records/page`, { params: { jobNumber, page, size } }).then(res => res.data);
}
