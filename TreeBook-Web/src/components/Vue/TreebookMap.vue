<template>
<div id="Map">
	<table id="HomeHeader">
	
		<tr style=" background: linear-gradient(#5f9025,#7aae30); ">
			<td colspan="5" style="font-size: 100px; font-weight:bold;color: #eaffe3">
				<a href='./#/treebookhome'><img src="./../../assets/TreeBookTitle.jpg" style="height: 150px;"></a>
			</td>

		</tr>
		<tr style="background: #7aae30">
			<td style="color: #7d3532; font-size: 20px; font-family: 'Times New Roman';" colspan="5">All the world's trees, all in one place</td>
		</tr>
		<tr style="background: #7aae30">
			<td style="padding-bottom: 15px;"><button onclick="location.href='./#/treebookregistration'" class="generalButton">Add Tree</button></td>
			<td style="padding-bottom: 15px;"><button onclick="location.href='./#/database'" class="generalButton">Database</button></td>
			<td style="padding-bottom: 15px;"><button onclick="location.href='./#/treebookforecast'" class="generalButton">Forecast</button></td>
			<td style="padding-bottom: 15px;"><button onclick="location.href='./#/treebookmap'" class="generalButton">Map</button></td>
			<td style="padding-bottom: 15px;"><button onclick="location.href='./#/userprofile'" class="generalButton">Profile</button></td>
		</tr>
		<tr style="background-color: #c1e09d; height: 20px;">
		<td colspan="5"></td>
		</tr>
	</table>
	<table style="margin:0 auto;">
	
		<tr>
			<td>
				<gmap-map
			    :center="center"
			    :zoom="3"
			    style="width: 1000px; height: 500px"
			    >
				    <gmap-marker
				      :key="index"
				      v-for="(m, index) in markers"
				      :position="m.position"
				      :clickable="true"
				      :draggable="false"
				      icon="http://maps.google.com/mapfiles/kml/pal2/icon12.png"
				      @click="toggleInfoWindow(m, index)"
				      
				    ></gmap-marker>
				    <gmap-info-window
				    	:options="infoOptions"
				    	:position="infoWindowPos"
				    	:opened="infoWinOpen" 
				    	@closeclick="infoWinOpen=false">
	        			{{infoContent}}
	      			</gmap-info-window>
	      			<gmap-marker
				      :key="index"
				      v-for="(m, index) in requestedmarkers"
				      :position="m.position"
				      :clickable="true"
				      :draggable="false"
				      icon="http://maps.google.com/mapfiles/kml/pal2/icon13.png"
				      @click="toggleInfoWindow(m, index)"
				      
				    ></gmap-marker>
	      			
			    </gmap-map>
			    
			</td>
		</tr>
	</table>
	<table style="margin:0 auto;">
		<th colspan="3">Request a tree</th>
		<tr>
			<td>Latitude:</td>
 			<td><input type="text" v-model="newRequestedLatitude" placeholder="X-Coord" style=" width: 250px"></td>	
 			<td>
 				<img src="http://maps.google.com/mapfiles/kml/pal2/icon13.png">
 				: Requested Trees: 
 				{{RequestCounter}}
 			</td>
		</tr>
		<tr>
			<td>Longitude:</td>
 			<td><input type="text" v-model="newRequestedLongitude" placeholder="Y-Coord" style=" width: 250px"></td>	
 			<td>
 				<img src="http://maps.google.com/mapfiles/kml/pal2/icon12.png">
 				: Regular Trees: 
 				{{TreeCounter}}
 			</td>
 			
		</tr>
		<tr>
			<td colspan="4" style="align: center">
				<button @click="Request(newRequestedLatitude,newRequestedLongitude)" id="loginButton" class="generalButton">Request</button>
			</td>
		</tr>
		<tr>
			<th>Latitude</th>
			<th>Longitude</th>
			<th colspan="2"></th>
		</tr>
		<tr v-for="tree in requestedtrees" style="border-bottom: 1px solid #c6c6c6;">
		    <td style="padding-top: 17px; vertical-align: top;">{{ tree.latitude }}</td>
		    <td style="padding-top: 17px; vertical-align: top;">{{ tree.longitude }}</td>
			<td><button @click="confirmTree(tree.latitude,tree.longitude)" class="generalButton">Confirm</button></td> 
			<td><button @click="rejectTree(tree.latitude,tree.longitude)" class="redButton">Reject</button></td> 
		</tr>
	</table>
		<table id="Confirm" style="margin:0 auto; margin-top: 20px">
		<tr>
			<th colspan="2" style="color: #000000; font-size: 26px; font-family: arial; text-align: center;">Confirm A Tree</th>
		</tr>
		<tr>
			<td style="text-align: right; width: 200px;">
				Species<span style="color: red">*</span>:
			</td>
			<td style="text-align: left; width: 200px;">
				<input type="text" v-model="newSpecies" placeholder="Species" style="width: 100%">
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				Land Use<span style="color: red">*</span>:
			</td>
			<td style="text-align: left;">
				<select v-model="newlandUse" style="width: 100%">
					<option disabled value="">Please select one </option>
					<option>Institutional</option>
					<option>Park</option>
					<option>Municipal</option>
					<option>Residential</option>
				</select>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				Municipality<span style="color: red">*</span>:
			</td>
			<td style="text-align: left;">
				<input type="text" v-model="newMunicipality" placeholder="Municipality" style="width: 100%">	
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				Status<span style="color: red">*</span>:
			</td>
			<td style="text-align: left;">
				<select v-model="newStatus" style="width: 100%">
					<option disabled value="">Please select one </option>
					<option>Planted</option>
					<option>Diseased</option>
					<option>ToBeCutDown</option>
				</select>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				Height (m):
			</td>
			<td style="text-align: left;">
				<input type="number" v-model="newHeight" placeholder="Height" style="width: 100%">
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				Canopy Diameter (m):
			</td>
			<td style="text-align: left;">
				<input type="number" v-model="newdiameterOfCanopy" placeholder="Canopy Diameter" style="width: 100%"> 
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				Planted Date:
			</td>
			<td style="text-align: left;">
				<input type="date" v-model="newWhenPlanted" placeholder="Planted Date" style="width: 100%">
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				Latitude:
			</td>
			<td style="text-align: left;">
				<input id="lat" type="number" v-model="newLat" placeholder="Latitude" style="width: 100%">
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				Longitude:
			</td>
			<td style="text-align: left;">
				<input id="long" type="number" v-model="newLong" placeholder="Longitude" style="width: 100%">
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<button style="width: 100%" @click="createConfirmTree(newSpecies, newtoBeChopped, newStatus, newHeight, newdiameterOfCanopy, newMunicipality, 1, newlandUse, newWhenPlanted, 0, newWhenCutDown, newLastSurvey, newLat, newLong, '',0,0)" class="generalButton">Finalize Tree</button>
			 </td>
		</tr>
		<tr>
			<td colspan="2" style="color: red; text-align: right;"> * mandatory fields </td>
		</tr>
		
    </table>
	
</div>
</template>
<style>
  #HomeHeader{
   margin:0 auto;
   width: 100%; 
  
  }
    #Map {
    background-color: #e4f4cd;
    font-family: Times New Roman;
    
    
  }

</style>
<script src="./../Javascript/TreebookMap.js">
</script>


