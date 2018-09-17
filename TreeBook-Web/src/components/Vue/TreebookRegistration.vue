<template>
  <div id="Treeregistration">
	<table id="HomeHeader">
		<tr style=" background: linear-gradient(#5f9025,#7aae30); ">
			<td colspan="5" style="font-size: 100px; font-weight:bold;color: #eaffe3">
				<a href='./#/treebookhome'><img src="./../../assets/TreeBookTitle.jpg" style="height: 150px;"></a>
			</td>
		</tr>
		<tr style="background: #7aae30">
			<td style="color: #7d3532; font-size: 20px;font-family: 'Times New Roman';" colspan="5">All the world's trees, all in one place</td>
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
	<table style="margin:0 auto; margin-top: 20px">
		<tr>
			<th colspan="2" style="color: #000000; font-size: 26px; font-family: arial; text-align: center;">Submit by Data</th>
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
				Trunk Diameter (m):
			</td>
			<td style="text-align: left;">
				<input type="number" v-model="newdiameterOfTrunk" placeholder="Trunk Diameter" style="width: 100%"> 
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
				<input type="number" v-model="newLatitude" placeholder="Latitude" style="width: 100%">
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				Longitude:
			</td>
			<td style="text-align: left;">
				<input type="number" v-model="newLongitude" placeholder="Longitude" style="width: 100%">
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<button style="width: 100%" @click="createTree(newSpecies, newtoBeChopped, newStatus, newHeight, newdiameterOfCanopy, newMunicipality, newdiameterOfTrunk, newlandUse, newWhenPlanted, 0, newWhenCutDown, newLastSurvey, newLatitude, newLongitude, '', 0, 0)" class="generalButton">Create Tree</button>
			 </td>
		</tr>
		<tr>
			<td colspan="2" style="color: red; text-align: right;"> * mandatory fields </td>
		</tr>
		<tr>
			<td style="text-align: right;">
				ID:
			</td>
			<td style="text-align: left;">
				<input type="number" v-model="newID" placeholder="ID Number" style="width: 100%">
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<button style="width: 100%" @click="editTree(newID, newSpecies, newtoBeChopped, newStatus, newHeight, newdiameterOfCanopy, newMunicipality, newdiameterOfTrunk, newlandUse, newWhenPlanted, 0, newWhenCutDown, newLastSurvey, newLatitude, newLongitude, '', 0, 0)" class="generalButton">Edit Tree</button>
			 </td>
		</tr>
		
    </table>
    <hr>
	<table style="margin:0 auto; margin-top: 20px">
		<tr>
			<th colspan="3" style="color: #000000; font-size: 26px; font-family: arial; text-align: center;">Submit by Data</th>
		</tr>
		<tr>
			<td>
				<input type="text" v-model="fileLoc" placeholder="Location" s>
			</td>
			<td>
			  <label>File:
			  		<input type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>
			  </label>
			</td>
			<td>
				<button  v-on:click="submitFile(fileLoc)" class="generalButton">Submit Link</button>
			 </td>
			
		</tr>
    </table>
    <p>
      <span v-if="errortrees" style="color:red">Error: {{errortrees}} </span>
    </p>
	</table>
  </div>
</template>
<script src="./../Javascript/TreebookRegistration.js">
</script>
<style>
  #Treeregistration {
  	background-color: #e4f4cd;
    color: #000000;
    font-family: Times New Roman;
  }
  td button {
  	width: 150px;
  }
    #HomeHeader{
   margin:0 auto;
   width: 100%; 
  
  }
</style>
