<!--
<template>
  <div id="userlogin">
	<table id="HomeHeader">
		<tr style=" background: linear-gradient(#5f9025,#7aae30); ">
			<td></td>
			<td colspan="3" style="font-size: 100px; font-weight:bold;color: #eaffe3">
				<a href='./#/treebookhome'><img src="./../../assets/TreeBookTitle.jpg" style="height: 150px;"></a>
			</td>
			<td></td>
			<td></td>
		</tr>
		<tr style="background: #7aae30">
			<td style="color: #7d3532; font-family: 'Times New Roman';" colspan="5">Just like Facebook, but for trees</td>
			<td></td>
		</tr>
		<tr style="background: #7aae30">
			<td style="padding-bottom: 15px;"><button onclick="location.href='./#/treebookregistration'" class="generalButton">Add Tree</button></td>
			<td style="padding-bottom: 15px;"><button onclick="location.href='./#/database'" class="generalButton">Database</button></td>
			<td style="padding-bottom: 15px;"><button onclick="location.href='./#/'" class="generalButton">Forecast</button></td>
			<td style="padding-bottom: 15px;"><button onclick="location.href='./#/treebookmap'" class="generalButton">Map</button></td>
			<td style="padding-bottom: 15px;"><button onclick="location.href='./#/userlogin'" class="loginbutton">Login</button></td>
			<td> </td>
		</tr>
		<tr style="background-color: #c1e09d; height: 20px;">
		<td colspan="6"></td>
		</tr>
	</table>
	<table id="loginTable">
		<tr>
			<td>
				<span style="text-shadow: #ffffff 0 0px 0;font-size:40px;font-weight:bold;text-align:center;">Login</span>
			</td>
		</tr>
		<tr>
			<td style="padding:0 0 0 30px;font-size:30px;color: #000000;text-shadow: #ffffff 0 0 0; text-align: left;">
				Username
			</td>
		</tr>
 		<tr>
 			<td>
 				<input type="text" v-model="newUsername" placeholder="Username" style=" width: 450px">
 			</td>
		</tr>
 		<tr>
	 		<td style="padding:0 0 0 30px;font-size:30px;color: #000000;text-shadow: #ffffff 0 0 0;text-align: left;">
	 			Password
	 		</td>
 		</tr>
 		<tr>
 			<td>
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
				<span style="color: white">No Account? Register </span>
				<a href='./#/userregistration' style="color: rgb(121, 210, 121)"> Here </a>
			</td>
		</tr>
  	</table>
	
	<p>
      <span v-if="errorusers" style="color:red">Error: {{errorusers}} </span>
    </p>	
  </div>
</template>
<style>
  button.registerButton{
    font-size:20px;
    text-align:left;
    background:none;
    border:none;
    color: #1047a4;
  }
  #userlogin {
    background-color: #e4f4cd;
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
  
</style>

-->