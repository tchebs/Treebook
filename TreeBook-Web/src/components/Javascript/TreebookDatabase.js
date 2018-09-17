import axios from 'axios'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
var reversed = false
export default {
  name: 'treebook',
  data () {
    return {
      trees: [],
      newtree: '',
      errortrees: '',
      reverser: '',
      comparator: '',
      newInputType: '',
      newInputValue: '',
      response: []
    }
  },
  created: function () {
  // Initializing trees from backend
    AXIOS.get(`/trees`)
    .then(response => {
      // JSON responses are automatically parsed.
      this.trees = response.data
	//alert(this.trees)
    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errortrees = errorMsg
    })
  },
  methods: {
	  Delete: function (id) {
	       name = this.$cookie.get('logged')
	   // Initializing participants from backend

	     AXIOS.delete(`/trees/remove?id=` + id + `&username=` + name)
	     .then(response => {

	       // JSON responses are automatically parsed.
	       //this.trees = response.data

	     })
	     .catch(e => {
	       var errorMsg = e.response.data.message
	       console.log(errorMsg)
	       this.errortrees = errorMsg
	     })
	   },
  Search: function (inputType, inputValue) {
var input = inputType + ' ' + inputValue

	//defaulting all the values
	this.species = 'null',
	this.searchingForChopped = false,
	this.toBeChopped = false,
	this.status = 'null',
	this.minHeight = -1,
	this.maxHeight = -1,
	this.minDiameterOfCanopy = -1,
	this.maxDiameterOfCanopy = -1,
	this.municipality = 'null',
	this.landUse = 'null'
	
	//setting the values
	if(inputType == 'Species'){
		this.species = inputValue
	}
	if(inputType == 'To Be Chopped'){
		this.searchingForChopped = true
		this.toBeChopped = inputValue
	}
	if(inputType == 'Status'){
		this.status = inputValue
	}
	if(inputType == 'Minimum Height'){
		this.minHeight = inputValue
	}
	if(inputType == 'Maximum Height'){
		this.maxHeight = inputValue
	}
	if(inputType == 'Minimum Diameter of Canopy'){
		this.minDiameterOfCanopy = inputValue
	}
	if(inputType == 'Maximum Diameter of Canopy'){
		this.maxDiameterOfCanopy = inputValue
	}
	if(inputType == 'Municipality'){
		this.municipality = inputValue
	}
	if(inputType == 'Land Use'){
		this.landUse = inputValue
	}

	var params2 = `/trees/filtered?species=` + this.species+ `&searchingForChopped=` + this.searchingForChopped+ `&toBeChopped=`+ this.toBeChopped + `&stringStatus=` + this.status + `&minHeight=` + this.minHeight + `&maxHeight=` + this.maxHeight + `&minDiameterOfCanopy=` + this.minDiameterOfCanopy + `&maxDiameterOfCanopy=` + this.maxDiameterOfCanopy + `&municipality=` + this.municipality + `&stringLandUse=` + this.landUse
	//alert(params2)
  // get new sorted tree list from backend
    AXIOS.get(params2)
    .then(response => {
      // JSON responses are automatically parsed.
      this.trees = response.data
	//alert(JSON.stringify(this.trees))
    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errortrees = errorMsg
    })
  },
	  Sort: function (comparator) {
	 
	   // Initializing participants from backend
	     AXIOS.get(`/trees/sortBy?comparator=` + comparator + `&reverse=` + reversed)
	     .then(response => {
	       // JSON responses are automatically parsed.
	       this.trees = response.data
	 	reversed = !reversed
	 	//alert(JSON.stringify(this.trees))
	     })
	     .catch(e => {
	       var errorMsg = e.response.data.message
	       console.log(errorMsg)
	       this.errortrees = errorMsg
	     })
	   },
  cutDown: function (treeId, name) {
      name = this.$cookie.get('logged')
      if(name != null){
      var params = `/trees/cutDown?id=` + treeId + `&username=` + name
      // alert(params)


      AXIOS.post(params, {}, {})
      .then(response => {
        // JSON responses are automatically parsed.
        //this.trees.push(response.data)
        this.errortrees = ''
      })
      .catch(e => {
        var errorMsg = e.response.data.message
        console.log(errorMsg)
	alert(errorMsg)
        this.errortrees = errorMsg
      });
      AXIOS.get(`/trees`)
      .then(response => {
        // JSON responses are automatically parsed.
        this.trees = response.data
      })
      .catch(e => {
        var errorMsg = e.response.data.message
        console.log(errorMsg)
        this.errortrees = errorMsg
      })
      }
      else{
        alert("not logged in")
        window.location.replace('http://ecse321-8.ece.mcgill.ca:8087/#/treebookhome')
      }
    }
  }
}
