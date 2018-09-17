<template>
  <div >
  
  	<div style=" position: fixed; top: 0; height: 150px; background-color: #ccffcc; width: 100%; ">
	<table id="HomeHeader">
		<tr style=" background: linear-gradient(#5f9025,#7aae30); ">
			<td colspan="2" style="font-size: 100px; font-weight:bold; color: #eaffe3">
				<a href='./#/treebookhome'><img src="./../../assets/TreeBookTitle.jpg" style="height: 150px;"></a>
			</td>
		</tr>
		<tr style="background: #7aae30">
			<td colspan="2" style="color: #7d3532; font-family: 'Times New Roman';font-size: 20px;">
				All the world's trees, all in one place
			</td>
		</tr>
		<tr style="background-color: #c1e09d; height: 100px;">
			<td style="width: 50%;">		
				<select v-model="newInputType">
					<option disabled value="">Select search criteria</option>
					<option>Species</option>
					<option>To Be Chopped</option>
					<option>Status</option>
					<option>Minimum Height</option>
					<option>Maximum Height</option>
					<option>Minimum Diameter of Canopy</option>
					<option>Maximum Diameter of Canopy</option>
					<option>Municipality</option>
					<option>Land Use</option>						
				</select>
				<input type="text" v-model="newInputValue" placeholder="Search value">
				<button style="width: 120px;" @click="Search(newInputType,newInputValue)" class="generalButton">Search</button>
			</td>
			<td style="width: 50%;">
				Sort by:
				<button style="width: 120px;" @click="Sort('species')" class="generalButton">Species</button>
				<button style="width: 120px;" @click="Sort('landUse')" class="generalButton">Land Use</button>
				<button style="width: 120px;" @click="Sort('municipality')" class="generalButton">Municipality</button>
				<button style="width: 120px;" @click="Sort('status')" class="generalButton">Status</button>
			</td>
		</tr>	
		<tr>
			<td colspan="2" style="background-color: #e4f4cd;border-bottom: 1px solid #c6c6c6;">
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
  	<div id="database">
	    <table style="margin: 340px auto; width: 100%;">
			<tr v-for="tree in trees" style="border-bottom: 1px solid #c6c6c6;">
				<td style="padding-top: 17px; vertical-align: top; width: 10%;">{{ tree.id }}</td>			
			    <td style="padding-top: 17px; vertical-align: top; width: 10%;">{{ tree.species }}</td>
				<td style="padding-top: 17px; vertical-align: top; width: 10%;">{{ tree.landUse }}</td>
				<td style="padding-top: 17px; vertical-align: top; width: 10%;">{{ tree.municipality }}</td>
				<td style="padding-top: 17px; vertical-align: top; width: 10%;">{{ tree.height }}</td>
				<td style="padding-top: 17px; vertical-align: top; width: 10%;">{{ tree.treeSustainability.co2Absorption }}</td>
				<td style="padding-top: 17px; vertical-align: top; width: 10%;">{{ tree.treeSustainability.biodiversityIndex }}</td>
				<td style="padding-top: 17px; vertical-align: top; width: 10%;">{{ tree.status }}</td>	
				<td><button @click="cutDown(tree.id,'')" class="redButton">Cut Down</button></td> 
				<td><button @click="Delete(tree.id)" class="redButton">Delete</button></td> 
				
			</tr>
	    </table>
    </div>
    <p>
      <span v-if="errortrees" style="color:red">Error: {{errortrees}} </span>
    </p>
  </div>
</template>
<script src="./../Javascript/TreebookDatabase.js">
</script>
<style>
  #database {
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
