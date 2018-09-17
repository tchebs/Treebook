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
      trees: [],
      newtree: '',
      errortrees: '',
      newSpecies: '',
      newStatus: '',
      newHeight: '',
      newdiameterOfCanopy: '',
      newMunicipality: '',
      newdiameterOfTrunk: '',
      newlandUse: '',
      newWhenPlanted: '',
      newWhenCutDown: '',
      newLatitude: '',
      newLongitude: '',
      newID: '',
      file: '',
      response: []
    }
  },
  methods: {

      submitFile: function(fileloc){

      name = this.$cookie.get('logged') // get the name of the user
var fileparams = `/file/?fileLocation=` + fileloc + this.file.name + `&username=` + name
//alert(fileparams)
            AXIOS.post( fileparams, {},{}
            ).then(response =>{
          alert('Successfully Uploaded File');
        })
      .catch(e => {
        var errorMsg = e.response.data.message
        console.log(errorMsg)
        this.errortrees = errorMsg
        //alert(e)
      })
      },

      handleFileUpload(){
        this.file = this.$refs.file.files[0];
      },

    createTree: function (species, toBeChopped, status, height, diameterOfCanopy, municipality, diameterOfTrunk, landUse, whenPlanted, reports, whenCutDown, lastSurvey, latitude, longitude, name, monetaryValue, biodiversityIndex) {
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
      if(diameterOfTrunk == ''){
        diameterOfTrunk = 0
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

      var params = `/trees/newTree?species=` + species + `&toBeChopped=` + toBeChopped + `&stringStatus=` + status + `&height=` + height + `&diameterOfCanopy=` + diameterOfCanopy + `&municipality=` + municipality + `&diameterOfTrunk=` + diameterOfTrunk + `&stringLandUse=` + landUse + `&whenPlanted=` + whenPlanted + `&reports=` + reports + `&whenCutDown=` + whenCutDown + `&lastSurvey=` + lastSurvey + `&longitude=` + longitude + `&latitude=` + latitude + `&username=` + name + `&monetaryValue=` + monetaryValue + `&BiodiversityIndex=` + biodiversityIndex
   // alert(params);
    //  if(this.$cookie.get('logged') == name){
        AXIOS.post(params, {}, {})
      .then(response => {
      // JSON responses are automatically parsed.
      this.trees.push(response.data)
      this.newtree= ''
      this.errortrees= ''
      this.newSpecies= ''
      this.newStatus=''
      this.newHeight= ''
      this.newdiameterOfCanopy= ''
      this.newdiameterOfTrunk= ''
      this.newMunicipality= ''
      this.newlandUse= ''
      this.newWhenPlanted= ''
      this.newWhenCutDown= ''
      this.newLatitude= ''
      this.newLongitude= ''

        alert("Successfully added tree")

      })
      .catch(e => {
        var errorMsg = e.response.data.message
        console.log(errorMsg)
        this.errortrees = errorMsg
        alert(errorMsg)
      })
    }  
    else{
      alert("You must Login")
      window.location.replace('http://ecse321-8.ece.mcgill.ca:8087/#/treebookhome')
    }

    },
    editTree: function (id, species, toBeChopped, status, height, diameterOfCanopy, municipality, diameterOfTrunk, landUse, whenPlanted, reports, whenCutDown, lastSurvey, latitude, longitude, name, monetaryValue, biodiversityIndex) {
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
      if(diameterOfTrunk == ''){
        diameterOfTrunk = 0
      }
	//alert(diameterOfCanopy)
      if(diameterOfCanopy == ''){
        diameterOfCanopy = 0
      }
     if(diameterOfTrunk == ''){
        diameterOfTrunk = 0
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

      var params = `/trees/editTree?id=` + id + `&species=` + species + `&toBeChopped=` + toBeChopped + `&stringStatus=` + status + `&height=` + height + `&diameterOfCanopy=` + diameterOfCanopy + `&municipality=` + municipality + `&diameterOfTrunk=` + diameterOfTrunk + `&stringLandUse=` + landUse + `&whenPlanted=` + whenPlanted + `&reports=` + reports + `&whenCutDown=` + whenCutDown + `&lastSurvey=` + lastSurvey + `&longitude=` + longitude + `&latitude=` + latitude + `&username=` + name + `&monetaryValue=` + monetaryValue + `&BiodiversityIndex=` + biodiversityIndex
   // alert(params);
    //  if(this.$cookie.get('logged') == name){

        AXIOS.post(params, {}, {})
      .then(response => {
      // JSON responses are automatically parsed.
      this.trees.push(response.data)
      this.newtree= ''
      this.errortrees= ''
      this.newSpecies= ''
      this.newStatus=''
      this.newHeight= ''
      this.newdiameterOfCanopy= ''
      this.newMunicipality= ''
      this.newdiameterOfTrunk= ''
      this.newlandUse= ''
      this.newWhenPlanted= ''
      this.newWhenCutDown= ''
      this.newLatitude= ''
      this.newLongitude= ''
      this.newID= ''

        alert("Successfully edited tree")

      })
      .catch(e => {
        var errorMsg = e.response.data.message
        console.log(errorMsg)
        this.errortrees = errorMsg
        alert(errorMsg)
      })
    }  
    else{
      alert("You must be a Scientist")
      window.location.replace('http://ecse321-8.ece.mcgill.ca:8087/#/treebookhome')
    }

    }

  }
}
