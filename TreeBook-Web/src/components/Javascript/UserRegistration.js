import axios from 'axios'
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
      newPhoneNumber: '',
      newPassword: '',
      newScientistPassword: '',
      errorusers: '',
      response: []
    }
  },
  created: function () {
  // Initializing users from backends
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
    createUser: function (name, phoneNumber, password,scientistpassword) {

	if(scientistpassword != 'TreeBook'){
      AXIOS.post(`/users/` + name + `?phoneNumber=` + phoneNumber + `&password=` + password, {}, {})
    .then(response => {
    // JSON responses are automatically parsed.
      this.users.push(response.data)
      this.newUsername = ''
      this.newPhoneNumber = ''
      this.newPassword = ''
      this.errorusers = ''
      alert("Successfully registered")
      window.location.replace('http://ecse321-8.ece.mcgill.ca:8087/#/treebookhome')

    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errorusers = errorMsg
    })
    }
	else{
      AXIOS.post(`/usersSuper/` + name + `?phoneNumber=` + phoneNumber + `&password=` + password, {}, {})
    .then(response => {
    // JSON responses are automatically parsed.
      this.users.push(response.data)
      this.newUsername = ''
      this.newPhoneNumber = ''
      this.newPassword = ''
      this.errorusers = ''
      alert("Successfully registered")
      window.location.replace('http://ecse321-8.ece.mcgill.ca:8087/#/treebookhome')

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
}

