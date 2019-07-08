const path = require('path');

module.exports = {
  chainWebpack: config => {
    config.plugin('html').tap(args => {
      args[0].chunksSortMode = 'none'
      return args
    })
    config.resolve.alias.set('#root', path.join(__dirname))
  },
  configureWebpack: {
    devtool: 'source-map'
  },
}
