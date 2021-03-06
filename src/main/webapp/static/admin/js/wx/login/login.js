+(function () {
  var login = new Vue({
    el: '#loginCont',
    http: {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    },
    data: {
      form: {
        username: '',
        password: ''
      },
      submitting: false
    },
    methods: {
      submit: function () {
        var self = this;
        var data = {
          username: self.form.username,
          password: self.form.password
        };
        self.submitting = true;
        self.$http.post(ctx + '/api/wx/login', $.param(data)).then(function (res) {
          if (res.data.code == 1) {
            window.location.href = ctx + "/index";
          } else {
            Vue.toastr.error(res.data.message);
          }
        }).catch(function () {

        }).finally(function () {
          self.submitting = false;
        });
      }
    },
    created: function () {
    },
    ready: function () {
    }
  });
})();