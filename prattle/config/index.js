module.exports = {
  port: 9090,
  expire_timeout: 5000,
  key: 'peerjs',
  path: '/myapp',
  concurrent_limit: 5000,
  allow_discovery: false,
  proxied: true,
  cleanup_out_msgs: 1000,
  ssl: {
    key: '',
    cert: ''
  }
};
