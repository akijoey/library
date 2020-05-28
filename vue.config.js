const path = require('path');

function resolve(dir) {
  return path.join(__dirname, dir)
}

module.exports = {
  devServer: {
    port: 8228
  },
  outputDir: 'src/main/resources/static',
  configureWebpack: {
    entry: './src/main/webapp/main.js'
  },
  chainWebpack: (config) => {
    config.resolve.alias
      .set('@', resolve('src/main/webapp'))
  }
}