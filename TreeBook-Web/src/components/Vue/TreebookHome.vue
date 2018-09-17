<template>
  <div id="Home">
	<table id="HomeHeader">
	
		<tr style=" background: linear-gradient(#5f9025,#7aae30); ">
			<td colspan="5" style="font-size: 100px; font-weight:bold;color: #eaffe3">
				<img src="./../../assets/TreeBookTitle.jpg" style="height: 150px;">	
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

	</table>
	<table id="forestpic">
 		<tr>
 			<td height="150px">
 				<input type="text" v-model="newUsername" placeholder="Username" style=" width: 450px">
 			</td>
		</tr>
 		<tr>
 			<td height="100px">
 				<input type="password" v-model="newPassword" placeholder="Password" style=" width: 450px">
 			</td>
 		</tr>
 		<tr>
 			<td height="100px">
 			<button @click="login(newUsername, newPassword)" id="loginButton" class="generalButton">Login</button>
 			</td>
 		</tr>
		<tr>
			<td height="50px" style="padding:0 0 0 30px;font-size:20px;text-align: left;">
				<span style="color: white; font-weight: bold;text-shadow: -0.5px 0 black, 0 0.5px black, 0.5px 0 black, 0 -0.5px black;">No Account? Register </span>
				<a href='./#/userregistration' style="color: #ffff33; font-weight: bold; text-shadow: -0.5px 0 black, 0 0.5px black, 0.5px 0 black, 0 -0.5px black;"> Here </a>
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
  #forestpic{
	  background-image: url("./../../assets/Treebook header photo.jpg");
	  margin:10px auto;
	  width: 98.5%;
	  min-height: 400px;
	  height: 400px;
	  background-repeat: no-repeat;
	  background-size: cover;
	  border-style: solid;
  }
  #Home {
    background-color: #e4f4cd;
    color: #ddf7bd;
    font-family: Times New Roman;
  }

  td button {
  	width: 150px;
  }
</style>
<script src="./../Javascript/UserLogin.js">
</script>