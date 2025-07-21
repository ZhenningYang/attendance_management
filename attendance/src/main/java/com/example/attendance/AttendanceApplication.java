package com.example.attendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Bean;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// import org.springframework.boot.CommandLineRunner;
// import com.example.attendance.entity.Employee;
// import com.example.attendance.entity.AttendanceRecord;
// import com.example.attendance.repository.EmployeeRepository;
// import com.example.attendance.repository.AttendanceRecordRepository;
// import java.time.LocalDate;
// import java.time.LocalDateTime;

@SpringBootApplication
public class AttendanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttendanceApplication.class, args);
	}

	// // 全局 CORS 配置
	// @Bean
	// public WebMvcConfigurer corsConfigurer() {
	// 	return new WebMvcConfigurer() {
	// 		@Override
	// 		public void addCorsMappings(CorsRegistry registry) {
	// 			registry.addMapping("/**")
	// 					.allowedOrigins("*")
	// 					.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	// 					.allowedHeaders("*");
	// 		}
	// 	};
	// }

	// @Bean
	// public CommandLineRunner dataLoader(EmployeeRepository employeeRepository, AttendanceRecordRepository attendanceRecordRepository) {
	// 	return args -> {
	// 		// 生成30个员工
	// 		for (int i = 1; i <= 30; i++) {
	// 			String jobNumber = String.format("10%02d", i);
	// 			Employee emp = new Employee();
	// 			emp.setJobNumber(jobNumber);
	// 			emp.setName("测试员工" + i);
	// 			employeeRepository.save(emp);
	// 			// 每个员工生成10条考勤记录
	// 			for (int j = 0; j < 10; j++) {
	// 				AttendanceRecord record = new AttendanceRecord();
	// 				record.setEmployeeId(jobNumber);
	// 				record.setSignDate(LocalDate.now().minusDays(j));
	// 				record.setSignInTime(LocalDateTime.now().minusDays(j).withHour(9).withMinute(0));
	// 				record.setSignOutTime(LocalDateTime.now().minusDays(j).withHour(18).withMinute(0));
	// 				attendanceRecordRepository.save(record);
	// 			}
	// 		}
	// 	};
	// }
}
