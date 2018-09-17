import axios from 'axios'
//import './../node_modules/tiny-cookie'
//import * as Cookies from 'tiny-cookie'
var config = require('../../../config')
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
export default {
  name: 'treebook',
  data () {
    return {
      users: [],
      newUsername: '',
      newPassword: '',
      errorusers: '',
      response: []
    }
  },
  created: function () {
  // Initializing participants from backend
    AXIOS.get(`/users`)
    .then(response => {
      // JSON responses are automatically parsed.
      this.users = response.data
    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errorusers = errorMsg
    })
  },
  methods: {
    login: function (name,password) {
      AXIOS.post(`/login?name=` + name + `&password=` + password, {}, {})
    .then(response => {
    // JSON responses are automatically parsed.
     // alert(response.data)
      if(response.data){//response.data is a boolean gotten from the post if username exists with the corresponding password
        this.$cookie.set('logged',name,{ expires: '2h' })

        alert("Successfully logged in")
        window.location.replace('http://ecse321-8.ece.mcgill.ca:8087/#/treebookhome')
      }
      else{
        alert("Invalid login credentials")
      }
      this.users.push(response.data)
      this.newUsername = ''
      this.newPassword = ''
      this.errorusers = ''

    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      alert(errorMsg)
      this.errorusers = errorMsg
    })
    }
  }


}

// From some method in one of your Vue components
 //     this.$cookie.set('test', 'Hello world!', 1);
// This will set a cookie with the name 'test' and the value 'Hello world!' that expires in one day

// To get the value of a cookie use
  //    this.$cookie.get('test');
