import axios from 'axios'
import Vue from 'vue'
import * as VueGoogleMaps from 'vue2-google-maps'
Vue.use(VueGoogleMaps, {
  load: {
    key: 'AIzaSyCl_dR9ncdMi4L6jC4zrTyJJCHjjIsFPDU',
  }
})
var config = require('../../../config')
var id;
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
var tester = '{position: {lat: 45.5047, lng: -73.5771}}, {position: {lat: 45.5049, lng: -73.5773}}'
//alert(tester)
var positionObjArr = []
var positionObjArr2 = []

export default {
  name: 'treebook',
  data () {
    return {
      errorusers: '',
      test: '',
      center: {lat: 45.5048, lng: -73.5772},
      markers: [],
      requestedmarkers: [],
      infoWindowPos: '',
      infoContent: '',
      infoWinOpen: '',
      currentMidx: '',
      newLat: '',
      newRequestedLatitude: '',
      requestedtrees: '',
      newLong: '',
      newRequestedLongitude: '',
      RequestCounter: '',
      TreeCounter: '',
      response: []
    }
  },
methods: {
//need to default species,status,municipality,landuse
//the button only passes latitude/longitude. have to default everything else
    confirmTree: function (latitude, longitude) {
	window.scrollTo(0, document.body.scrollHeight);
	this.newLat = latitude
	this.newLong = longitude
    },
    createConfirmTree: function (species, toBeChopped, status, height, diameterOfCanopy, municipality, diameterOfTrunk, landUse, whenPlanted, reports, whenCutDown, lastSurvey, latitude, longitude, name, monetaryValue, biodiversityIndex) {

      name = this.$cookie.get('logged') // get the name of the user

      if(name != null){

      if(toBeChopped == 'True'){
        toBeChopped = true
      }
      else{
        toBeChopped = false
      }
	//alert(height)
      if(height == undefined){
        height = 0
      }
      if(diameterOfTrunk == undefined){
        diameterOfTrunk = 0
      }
	//alert(diameterOfCanopy)
      if(diameterOfCanopy == undefined){
        diameterOfCanopy = 0
      }
	//alert(longitude)
      if(latitude == undefined){
        latitude = 0
      }
	//alert(latitude)
      if(longitude == undefined){
        longitude = 0
      }
      var date = new Date()
      var year = date.getFullYear()
      var month = date.getMonth()
      var day = date.getDate()
      whenPlanted = year + '-' + month + '-' + day
      whenCutDown = year + '-' + month + '-' + day
      lastSurvey = year + '-' + month + '-' + day
      var params = `/trees/confirmTree?species=` + species + `&toBeChopped=` + toBeChopped + `&stringStatus=` + status + `&height=` + height + `&diameterOfCanopy=` + diameterOfCanopy + `&municipality=` + municipality + `&diameterOfTrunk=` + diameterOfTrunk + `&stringLandUse=` + landUse + `&whenPlanted=` + whenPlanted + `&reports=` + reports + `&whenCutDown=` + whenCutDown + `&lastSurvey=` + lastSurvey + `&longitude=` + longitude + `&latitude=` + latitude + `&username=` + name 
    //alert(params);
      if(this.$cookie.get('logged') == name){

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
      this.newlandUse= ''
      this.newWhenPlanted= ''
      this.newWhenCutDown= ''
      this.newLat= ''
      this.newLong= ''

        alert("Successfully added tree")

      })
      .catch(e => {
        var errorMsg = e.response.data.message
        console.log(errorMsg)
        this.errortrees = errorMsg
        alert(errorMsg)
      })
    }  
    }
    else{
      alert("You Must Login")
      window.location.replace('http://ecse321-8.ece.mcgill.ca:8087/#/treebookhome')
    }

    },
	rejectTree: function(latitude, longitude,username){
		var username = this.$cookie.get('logged')
		if(username != null){
			AXIOS.post(`/trees/rejectTree?latitude=` + latitude + `&longitude=` + longitude + `&username=` + username,{},{})
			.then(response => {
				this.newLatitude = '',
				this.newLongitude = '',
				alert("Successfully Rejected")

				location.reload()
			})
			.catch(e => {
			var errorMsg = e.response.data.message
			console.log(errorMsg)
			alert(errorMsg)
			this.errorusers = errorMsg
			})
		}
		else{
			alert("You Must Login")
	     		window.location.replace('http://ecse321-8.ece.mcgill.ca:8087/#/treebookhome')
		}
	},
	Request: function(requestedlatitude, requestedlongitude){
		var username = this.$cookie.get('logged')
		if(username != null){
			if(latitude == '' || longitude == ''){
				alert("You Must Input Valid Coordinates")
			}
			else{

				AXIOS.post(`/trees/requestTree?latitude=` + requestedlatitude + `&longitude=` + requestedlongitude + `&username=` + username,{},{})
				.then(response => {
					this.newRequestedLatitude = '',
					this.newRequestedLongitude = '',
					alert("Successfully Requested")

					location.reload()
				})
				.catch(e => {
				var errorMsg = e.response.data.message
				console.log(errorMsg)
				alert(errorMsg)
				this.errorusers = errorMsg
				})
			}
		}
		else{
			alert("You Must Login")
	     		window.location.replace('http://ecse321-8.ece.mcgill.ca:8087/#/treebookhome')
		}
	},
          toggleInfoWindow: function(marker, idx) {

            this.infoWindowPos = marker.position;
	    //this.infoWindowPos.lat += 0.00001
	    //alert(JSON.stringify(this.infoWindowPos))
            this.infoContent = marker.info;

	   // alert(JSON.stringify(this.infoContent))
            //check if its the same marker that was selected if yes toggle
            if (this.currentMidx == idx) {
              this.infoWinOpen = !this.infoWinOpen;
            }
            //if different marker set infowindow to open and reset current marker index
            else {
              this.infoWinOpen = true;
              this.currentMidx = idx;

            }
          }

        },

  created: function () {
  // Initializing trees from backend
    AXIOS.get(`/trees`)
    .then(response => {
      // JSON responses are automatically parsed.
      this.trees = (response.data)
//alert(JSON.stringify(this.trees[0]))
	this.TreeCounter = 0
	for (var i in this.trees){//add objects to list of markers
	this.TreeCounter ++
		var positionObj = {position: {
			lat: this.trees[i].coordinates.latitude, 
			lng: this.trees[i].coordinates.longitude},
			info: 'Species: ' + this.trees[i].species + ', Status: ' + this.trees[i].status + ', Land Use: ' + 
this.trees[i].landUse + ', Municipality: ' + this.trees[i].municipality + ', Latitude: ' + this.trees[i].coordinates.latitude + ', Longitude: ' + this.trees[i].coordinates.longitude}
		positionObjArr.push(positionObj)
	}

	this.markers = positionObjArr
	//alert(JSON.stringify(this.markers))

    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errorusers = errorMsg
    })
  //initialize request trees from backend
    AXIOS.get(`/requestedTrees`)
    .then(response => {
      // JSON responses are automatically parsed.
      this.requestedtrees = (response.data)
//alert(JSON.stringify(this.requestedtrees))
	this.RequestCounter = 0
	for (var j in this.requestedtrees){//add objects to list of markers
		this.RequestCounter ++;
		var positionObj2 = {position: {
			lat: this.requestedtrees[j].latitude, 
			lng: this.requestedtrees[j].longitude},
			info: 'Latitude: ' + this.requestedtrees[j].latitude + ', Longitude: ' + this.requestedtrees[j].longitude}

		positionObjArr2.push(positionObj2)
	}
//alert(this.RequestCounter)
	this.requestedmarkers = positionObjArr2
	//alert(JSON.stringify(this.requestedmarkers))

    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errorusers = errorMsg
    })
  },
}

