<template>
  <div id="userregistration">
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
	<table id="forestpic2">
 		<tr>
 			<td height="100px">
 				<input type="text" v-model="newUsername" placeholder="Username" style=" width: 450px">
 			</td>
		</tr>
 		<tr>
 			<td height="100px">
 				<input type="text" v-model="newPhoneNumber" placeholder="Phone Number" style=" width: 450px">
 			</td>
 		</tr>
 		<tr>
 			<td height="100px">
 				<input type="password" v-model="newPassword" placeholder="Password" style=" width: 450px">
 			</td>
 		</tr>
		<tr>
 			<td height="100px">
 				<input type="password" v-model="newScientistPassword" placeholder="Scientist Password [optional]" style=" width: 450px">
 			</td>
 		</tr>
 		<tr>
 			<td height="100px">
				<button @click="createUser(newUsername, newPhoneNumber, newPassword,newScientistPassword)" class="generalButton"> Register</button> 
			</td>
 		</tr>
	</table>
	<p>
      <span v-if="errorusers" style="color:red">Error: {{errorusers}} </span>
    </p>
  </div>
</template>
<style>
  #userregistration {
  	background-color: #e4f4cd;
    color: #e4f4cd;
    font-family: Times New Roman;
  }
  #loginTable{
 	background: linear-gradient(#79d279, #996600);
 	margin:0 auto;
 	border-style: none;
 	border-width: none;
 	width: 45%;
 	height:500px;
 	border-radius: 25px;
  }
    #HomeHeader{
   margin:0 auto;
   width: 100%; 
  
  }
    #forestpic2{
	  background-image: url("./../../assets/Registration.jpg");
	  margin:10px auto;
	  width: 98.5%;
	  min-height: 500px;
	  height: 500px;
	  background-repeat: no-repeat;
	  background-size: cover;
	  border-style: solid;
  }
  
  
</style>
<script src="./../Javascript/UserRegistration.js">
</script>