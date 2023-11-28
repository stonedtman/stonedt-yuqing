const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    static:{
      publicPath: "./public",
    },
    proxy: {
      "/api": {
        // target: 'http://192.168.71.19:8084',
        target: 'http://192.168.71.52:35210',
        changeOrigin: true,
        pathRewrite: {
          [`^/api`]: ''
        }
      }
    }
  },
  publicPath: "/opinion_screen/",
  outputDir: "opinion_screen",
  chainWebpack: config => {
    config.module
      .rule("scss")
      .test(/\.scss$/)
      .oneOf("vue")
      .use("px2rem-loader")
      .loader("px2rem-loader")
      .before("postcss-loader") // this makes it work.
      .options({ remUnit: 192, remPrecision: 8 })
      .end();
  }
})
