const { defineConfig } = require('@vue/cli-service')
  module.exports = {
  devServer: {
    client: {
      overlay: false,
    },
    proxy: {
      '/api': {
        target: 'http://localhost:8081', // Spring Boot 后端地址
        changeOrigin: true
      }
    },
    historyApiFallback: true // 新增，解决刷新 404
  }
}
