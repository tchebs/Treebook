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
      trophies: null,
      myself: '',
      errorusers: '',
      response: []
    }
  },
  created: function () {
	name = this.$cookie.get('logged')
	if (name != 'null'){
  // Initializing participants from backend
    AXIOS.get(`/users`)
    .then(response => {

      // JSON responses are automatically parsed.
      this.users = response.data
      this.trophies = null

	//alert(JSON.stringify(this.users))

	for(var i in this.users){
		if(this.users[i].name == this.$cookie.get('logged')){
			this.trophies = this.users[i].trophies
			this.myself = this.users[i]
		}
	}
	
//need to make variable for each of the 9 trophies to check for them individualy
	//this.plantedtrophy1 = this.trophies[0].name
	//alert(JSON.stringify(this.plantedtrophy1))

	//this.trophies = this.users.trophies
	//alert(this.trophies[0].name)
    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      alert(errorMsg)
      this.errorusers = errorMsg
    })
	}
	else{ this.myself = {"phoneNumber":"","name":"","password":"","treesPlanted":0,"treesCutDown":0,"treesRequested":0,"trophies":[{"name":""},{"name":""},{"name":""},{"name":""},{"name":""},{"name":""},{"name":""},{"name":""},{"name":""}],"isSuper":false}
		this.trophies = [{"name":""},{"name":""},{"name":""},{"name":""},{"name":""},{"name":""},{"name":""},{"name":""},{"name":""}]
	}
  },

}

