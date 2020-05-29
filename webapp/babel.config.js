module.exports = {
  presets: [
    '@vue/cli-plugin-babel/preset',
    "@babel/preset-env"
  ],

  plugins: [
    ["babel-plugin-module-resolver", {
      "alias": {
        "@": "./src/"
      }
    }]
  ],
}
