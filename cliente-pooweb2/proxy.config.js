const proxy = [
    {
      context: '/api',
      target: 'http://localhost:8081',
      secure: false,
      pathRewrite: {'^/api' : ''}
    }
  ];
module.exports = proxy;