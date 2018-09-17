<template>
  <div >
  	<div style=" position: fixed; top: 0; height: 150px; background-color: #ccffcc; width: 100%; ">
	<table id="HomeHeader">
		<tr style=" background: linear-gradient(#5f9025,#7aae30); ">
			<td colspan="5" style="font-size: 100px; font-weight:bold; color: #eaffe3">
				<a href='./#/treebookhome'><img src="./../../assets/TreeBookTitle.jpg" style="height: 150px;"></a>
			</td>
		</tr>
		<tr style="background: #7aae30">
			<td colspan="5" style="color: #7d3532; font-family: 'Times New Roman';font-size: 20px;">
				All the world's trees, all in one place
			</td>
		</tr>
		<tr style="background: #7aae30">
			<td style="padding-bottom: 15px;"><button onclick="location.href='./#/treebookregistration'" class="generalButton">Add Tree</button></td>
			<td style="padding-bottom: 15px;"><button onclick="location.href='./#/database'" class="generalButton">Database</button></td>
			<td style="padding-bottom: 15px;"><button onclick="location.href='./#/treebookforecast'" class="generalButton">Forecast</button></td>
			<td style="padding-bottom: 15px;"><button onclick="location.href='./#/treebookmap'" class="generalButton">Map</button></td>
			<td style="padding-bottom: 15px;"><button onclick="location.href='./#/userprofile'" class="generalButton">Profile</button></td>
		</tr>
		<tr>
			<td colspan="5" style="background-color: #e4f4cd;border-bottom: 1px solid #c6c6c6;">
			    <table style="width: 100%">
					<tr>
						<th width="10%">ID</th>					
						<th width="10%">Species</th>
						<th width="10%">Land Use</th>
						<th width="10%">Municipality</th>
						<th width="10%">Height</th>
						<th width="10%">CO2 Absorption</th>
						<th width="10%">Biodiversity Index</th>
						<th width="10%">Status</th>
						<th width="20%"></th>
					</tr>
				</table>
			</td>
		</tr>	
	</table>
  	</div>
  	<div id="database2">
  	
	    <table style="margin: 300px auto; width: 100%;">	    
			<tr v-for="tree in forecastTrees" style="border-bottom: 1px solid #c6c6c6;">
				<td style="padding-top: 17px; vertical-align: top; width: 10%;">{{ tree.id }}</td>			
			    <td style="padding-top: 17px; vertical-align: top; width: 10%;">{{ tree.species }}</td>
				<td style="padding-top: 17px; vertical-align: top; width: 10%;">{{ tree.landUse }}</td>
				<td style="padding-top: 17px; vertical-align: top; width: 10%;">{{ tree.municipality }}</td>
				<td style="padding-top: 17px; vertical-align: top; width: 10%;">{{ tree.height }}</td>
				<td style="padding-top: 17px; vertical-align: top; width: 10%;">{{ tree.treeSustainability.co2Absorption }}</td>
				<td style="padding-top: 17px; vertical-align: top; width: 10%;">{{ tree.treeSustainability.biodiversityIndex }}</td>
				<td style="padding-top: 17px; vertical-align: top; width: 10%;">{{ tree.status }}</td>	
				<td><button @click="cutDownForecast(tree.id)" class="redButton">Cut Down</button></td> 
			</tr>
			<tr>
				<td colspan="10"><button @click="InitForecast()" class="redButton">Begin Forecast</button></td>
			</tr>
			
	    </table>
  	
	<table style="margin:0 auto; margin-top: 20px">
		<tr>
			<th colspan="2" style="color: #000000; font-size: 26px; font-family: arial; text-align: center;">Add Forecast Tree</th>
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
				<button @click="PlantForecast(newSpecies, newtoBeChopped, newStatus, newHeight, newdiameterOfCanopy,
			 newMunicipality, 1, newlandUse, newWhenPlanted, 0, newWhenCutDown, newLastSurvey, newLatitude, newLongitude, '', 0, 0)" class="generalButton">Create Forecast Tree</button>
			 </td>
		</tr>
		<tr>
			<td colspan="2" style="color: red; text-align: right;"> * mandatory fields </td>
		</tr>
    </table>
    </div>

  </div>
</template>

<script src="./../Javascript/TreeBookForecast.js">
</script>

<style>
  #database2 {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #000000;
	background-color: #e4f4cd;
  }
  .table-header{
   	background: linear-gradient(#79d279,#ffdd99, #c6ecc6);
  	height: 40px;
  	border: 2px inset brown;
  }
    #HomeHeader{
   margin:0 auto;
   width: 100%; 
  
  }
</style>
