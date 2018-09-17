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
      forecastTrees: [],
      trees: [],
      newtree: '',
      errortrees: '',
      reverser: '',
      comparator: '',
      newInputType: '',
      newInputValue: '',
      newSpecies: '',
      newStatus: '',
      newHeight: '',
      newdiameterOfCanopy: '',
      newMunicipality: '',
      newlandUse: '',
      newWhenPlanted: '',
      newWhenCutDown: '',
      newLatitude: '',
      newLongitude: '',
      response: []
    }
  },
  methods: {
	cutDownForecast: function(id){
      name = this.$cookie.get('logged') // get the name of the user
    AXIOS.post(`/forecast/` + name + `/cutDown/?id=` + id + `&name=` + name)
    .then(response => {
	//alert(JSON.stringify(response.data))
      // JSON responses are automatically parsed.
      //this.forecastTrees = response.data
	//alert(JSON.stringify(this.forecastTrees))
    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errortrees = errorMsg
    })

    AXIOS.get(`/getForecast/` + name )
    .then(response => {

      // JSON responses are automatically parsed.
      this.forecastTrees = response.data
	//alert(JSON.stringify(this.forecastTrees))
    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errortrees = errorMsg
    })
  },

	InitForecast: function(){
      name = this.$cookie.get('logged') // get the name of the user
    AXIOS.get(`/forecast/` + name)
    .then(response => {

      // JSON responses are automatically parsed.
      this.forecastTrees = response.data
	//alert(JSON.stringify(this.forecastTrees))
    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errortrees = errorMsg
    })
  },

	PlantForecast: function (species, toBeChopped, status, height, diameterOfCanopy, municipality, diameterOfTrunk, landUse, whenPlanted, reports, whenCutDown, lastSurvey, latitude, longitude, name, monetaryValue, biodiversityIndex) {

      name = this.$cookie.get('logged') // get the name of the user

      if(name != null){
      if(toBeChopped == 'True'){
        toBeChopped = true
      }
      else{
        toBeChopped = false
      }
	//alert(height)
      if(height == ''){
        height = 0
      }
	//alert(diameterOfCanopy)
      if(diameterOfCanopy == ''){
        diameterOfCanopy = 0
      }
	//alert(longitude)
      if(latitude == ''){
        latitude = 0
      }
	//alert(latitude)
      if(longitude == ''){
        longitude = 0
      }
      var date = new Date()
      var year = date.getFullYear()
      var month = date.getMonth()
      var day = date.getDate()
      whenPlanted = year + '-' + month + '-' + day
      whenCutDown = year + '-' + month + '-' + day
      lastSurvey = year + '-' + month + '-' + day

      var params = `/forecast/`+ name + `/plantTree?species=` + species + `&toBeChopped=` + toBeChopped + `&stringStatus=` + status + `&height=` + height + `&diameterOfCanopy=` + diameterOfCanopy + `&municipality=` + municipality + `&diameterOfTrunk=` + diameterOfTrunk + `&stringLandUse=` + landUse + `&whenPlanted=` + whenPlanted + `&reports=` + reports + `&whenCutDown=` + whenCutDown + `&lastSurvey=` + lastSurvey + `&longitude=` + longitude + `&latitude=` + latitude + `&username=` + name + `&monetaryValue=` + monetaryValue + `&BiodiversityIndex=` + biodiversityIndex
//alert(params)
      AXIOS.post(params, {}, {})
    .then(response => {
    // JSON responses are automatically parsed.
      alert('Successfully added to forecast')
      this.forecastTrees.push(response.data)
      this.newtree= ''
      this.errortrees= ''
      this.newSpecies= ''
      this.newStatus=''
      this.newHeight= ''
      this.newdiameterOfCanopy= ''
      this.newMunicipality= ''
      this.newlandUse= ''
      this.newWhenPlanted= ''
      this.newWhenCutDown= ''
      this.newLatitude= ''
      this.newLongitude= ''
     name = this.$cookie.get('logged') // get the name of the user

  // Initializing trees from backend
    AXIOS.get(`/getForecast/` + name )
    .then(response => {
      // JSON responses are automatically parsed.
      this.forecastTrees = response.data
	//alert(JSON.stringify(this.forecastTrees))
    })

    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errorusers = errorMsg
    })
    }
else{
	alert('Must log in to use forecast')
	window.location.replace('http://ecse321-8.ece.mcgill.ca:8087/#/treebookhome')
	}

  }
}
}

