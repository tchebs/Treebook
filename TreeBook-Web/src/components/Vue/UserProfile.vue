<template>
  <div id="profile">
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
		
	<table style="margin:0 auto;  width: 100%; background-color: #e4f4cd;border-bottom: 1px solid #c6c6c6;" > 
	<th>User Information</th>
	<tr>
		<td>Username: {{myself.name}}</td>
		<td>Phone Number: {{myself.phoneNumber}}</td>
		<td>Is an Expert: {{myself.isSuper}}</td>
	</tr>
	<tr>
		<td>Trees Planted: {{myself.treesPlanted}}</td>
		<td>Trees Cut Down: {{myself.treesCutDown}}</td>
		<td>Trees Requested: {{myself.treesRequested}}</td>
	</tr>
	<th>Trophy Information</th>
		<tr>
			<td>
				Planted
			</td>
			
			<td v-if="trophies[0].name">
		 		<img src="./../../assets/bronze.png">
			</td>
			
			<td v-else>
				<img src="./../../assets/NoTrophy.png">			
			</td>
			<td v-if="trophies[1].name">
		 		<img src="./../../assets/silver.png">
			</td>
			<td v-else>
				<img src="./../../assets/NoTrophy.png">			
			</td>
			<td v-if="trophies[2].name">
		 		<img src="./../../assets/gold.png">
			</td>
			<td v-else>
				<img src="./../../assets/NoTrophy.png">			
			</td>

		</tr>
		
		<tr>
			<td>
				Request
			</td>
			<td v-if="trophies[3].name">
		 		<img src="./../../assets/bronze.png">
			</td>
			<td v-else>
				<img src="./../../assets/NoTrophy.png">			
			</td>
			<td v-if="trophies[4].name">
		 		<img src="./../../assets/silver.png">
			</td>
			<td v-else>
				<img src="./../../assets/NoTrophy.png">			
			</td>
			<td v-if="trophies[5].name">
		 		<img src="./../../assets/gold.png">
			</td>
			<td v-else>
				<img src="./../../assets/NoTrophy.png">			
			</td>
		
		</tr>
		
		<tr>
			<td>
				Cutdown
			</td>
			<td v-if="trophies[6].name">
		 		<img src="./../../assets/bronze.png">
			</td>
			<td v-else>
				<img src="./../../assets/NoTrophy.png">			
			</td>
			<td v-if="trophies[7].name">
		 		<img src="./../../assets/silver.png">
			</td>
			<td v-else>
				<img src="./../../assets/NoTrophy.png">			
			</td>
			<td v-if="trophies[8].name">
		 		<img src="./../../assets/gold.png">
			</td>
			<td v-else>
				<img src="./../../assets/NoTrophy.png">			
			</td>
		</tr>
		
	</table>
	
  	
  </div>
</template>
<style>
#HomeHeader{
   margin:0 auto;
   width: 100%; 
  
  }
</style>

<script src="./../Javascript/UserProfile.js">
</script>