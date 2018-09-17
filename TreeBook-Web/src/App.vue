<template>
  <div id="app">
    	<!-- <img src="./assets/TreeBookLogo.png"> -->
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
.generalButton {
	-moz-box-shadow: 0px 10px 14px -7px #3e7327;
	-webkit-box-shadow: 0px 10px 14px -7px #3e7327;
	box-shadow: 0px 10px 14px -7px #3e7327;
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #9acc81), color-stop(1, #72b352));
	background:-moz-linear-gradient(top, #9acc81 5%, #72b352 100%);
	background:-webkit-linear-gradient(top, #9acc81 5%, #72b352 100%);
	background:-o-linear-gradient(top, #9acc81 5%, #72b352 100%);
	background:-ms-linear-gradient(top, #9acc81 5%, #72b352 100%);
	background:linear-gradient(to bottom, #9acc81 5%, #72b352 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#9acc81', endColorstr='#72b352',GradientType=0);
	background-color:#9acc81;
	-moz-border-radius:4px;
	-webkit-border-radius:4px;
	border-radius:4px;
	border:1px solid #4b8f29;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family: Times New Roman;;
	font-size:15px;
	font-weight:bold;
	padding:9px 25px;
	text-decoration:none;
	text-shadow:0px 1px 0px #5b8a3c;
}
.loginbutton {
	-moz-box-shadow: 0px 10px 14px -7px #3e7327;
	-webkit-box-shadow: 0px 10px 14px -7px #3e7327;
	box-shadow: 0px 10px 14px -7px #3e7327;
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #9acc81), color-stop(1, #72b352));
	background:-moz-linear-gradient(top, #9acc81 5%, #72b352 100%);
	background:-webkit-linear-gradient(top, #9acc81 5%, #72b352 100%);
	background:-o-linear-gradient(top, #9acc81 5%, #72b352 100%);
	background:-ms-linear-gradient(top, #9acc81 5%, #72b352 100%);
	background:linear-gradient(to bottom, #9acc81 5%, #72b352 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#9acc81', endColorstr='#72b352',GradientType=0);
	background-color:#9acc81;
	-moz-border-radius:4px;
	-webkit-border-radius:4px;
	border-radius:4px;
	border:1px solid #4b8f29;
	display:inline-block;
	cursor:pointer;
	color:#7d3532;
	font-family: Times New Roman;;
	font-size:15px;
	font-weight:bold;
	padding:9px 25px;
	text-decoration:none;
	text-shadow:0px 1px 0px #5b8a3c;
}
.loginbutton:hover {
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #72b352), color-stop(1, #9acc81));
	background:-moz-linear-gradient(top, #72b352 5%, #9acc81 100%);
	background:-webkit-linear-gradient(top, #72b352 5%, #9acc81 100%);
	background:-o-linear-gradient(top, #72b352 5%, #9acc81 100%);
	background:-ms-linear-gradient(top, #72b352 5%, #9acc81 100%);
	background:linear-gradient(to bottom, #72b352 5%, #9acc81 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#72b352', endColorstr='#9acc81',GradientType=0);
	background-color:#72b352;
}
.generalButton:hover {
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #72b352), color-stop(1, #9acc81));
	background:-moz-linear-gradient(top, #72b352 5%, #9acc81 100%);
	background:-webkit-linear-gradient(top, #72b352 5%, #9acc81 100%);
	background:-o-linear-gradient(top, #72b352 5%, #9acc81 100%);
	background:-ms-linear-gradient(top, #72b352 5%, #9acc81 100%);
	background:linear-gradient(to bottom, #72b352 5%, #9acc81 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#72b352', endColorstr='#9acc81',GradientType=0);
	background-color:#72b352;
}
.generalButton:active {
	position:relative;
	top:1px;
}
.redButton {
	-moz-box-shadow:inset 0px 1px 0px 0px #cf866c;
	-webkit-box-shadow:inset 0px 1px 0px 0px #cf866c;
	box-shadow:inset 0px 1px 0px 0px #cf866c;
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #94280a), color-stop(1, #d13617));
	background:-moz-linear-gradient(top, #94280a 5%, #d13617 100%);
	background:-webkit-linear-gradient(top, #94280a 5%, #d13617 100%);
	background:-o-linear-gradient(top, #94280a 5%, #d13617 100%);
	background:-ms-linear-gradient(top, #94280a 5%, #d13617 100%);
	background:linear-gradient(to bottom, #94280a 5%, #d13617 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#94280a', endColorstr='#d13617',GradientType=0);
	background-color:#94280a;
	-moz-border-radius:3px;
	-webkit-border-radius:3px;
	border-radius:3px;
	border:1px solid #994f40;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family: Times New Roman;
	font-size:15px;
	padding:9px 25px;
	text-decoration:none;
	text-shadow:0px 1px 0px #854629;
}
.redButton:hover {
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #d13617), color-stop(1, #94280a));
	background:-moz-linear-gradient(top, #d13617 5%, #94280a 100%);
	background:-webkit-linear-gradient(top, #d13617 5%, #94280a 100%);
	background:-o-linear-gradient(top, #d13617 5%, #94280a 100%);
	background:-ms-linear-gradient(top, #d13617 5%, #94280a 100%);
	background:linear-gradient(to bottom, #d13617 5%, #94280a 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#d13617', endColorstr='#94280a',GradientType=0);
	background-color:#d13617;
}
.redButton:active {
	position:relative;
	top:1px;
}
.form-container {
   border: 0px solid #9acc81;
   
   <!-- background: #ad783e;
   background: -webkit-gradient(linear, left top, left bottom, from(#93cc76), to(#ad783e));
   background: -webkit-linear-gradient(top, #93cc76, #ad783e);
   background: -moz-linear-gradient(top, #93cc76, #ad783e);
   background: -ms-linear-gradient(top, #93cc76, #ad783e);
   background: -o-linear-gradient(top, #93cc76, #ad783e);
   background-image: -ms-linear-gradient(top, #93cc76 0%, #ad783e 100%);-->
   
   background: linear-gradient(45deg,#93cc76,#ad783e);
   background-image: linear-gradient(top, #93cc76 0%, #ad783e 70%, #ad783e 100%);
   -webkit-border-radius: 12px;
   -moz-border-radius: 12px;
   border-radius: 12px;
   -webkit-box-shadow: rgba(000,000,000,0.9) 0 0px 2px, inset rgba(255,255,255,0.4) 0 0px 0;
   -moz-box-shadow: rgba(000,000,000,0.9) 0 0px 2px, inset rgba(255,255,255,0.4) 0 0px 0;
   box-shadow: rgba(000,000,000,0.9) 0 0px 2px, inset rgba(255,255,255,0.4) 0 0px 0;
   <!-- font-family: 'Helvetica Neue',Helvetica,sans-serif;-->
   font-family: Times New Roman;
   text-decoration: none;
   vertical-align: middle;
   min-width:300px;
   padding:20px;
   width:500px;
   }
.form-field {
   border: 1px solid #c9b7a2;
   background: #e4d5c3;
   -webkit-border-radius: 4px;
   -moz-border-radius: 4px;
   border-radius: 4px;
   color: #c9b7a2;
   -webkit-box-shadow: rgba(255,255,255,0.4) 0 0px 0, inset rgba(000,000,000,0.7) 0 1px 1px;
   -moz-box-shadow: rgba(255,255,255,0.4) 0 0px 0, inset rgba(000,000,000,0.7) 0 1px 1px;
   box-shadow: rgba(255,255,255,0.4) 0 0px 0, inset rgba(000,000,000,0.7) 0 1px 1px;
   padding:8px;
   margin-bottom:20px;
   width:450px;
   }
.form-field:focus {
   background: #fff;
   color: #725129;
   }
.form-container h2 {
   text-shadow: #ffffff 0 0px 0;
   font-size:40px;
   margin: 0 0 10px 0;
   font-weight:bold;
   text-align:center;
    }
.form-title {
   margin-bottom:10px;
   font-size:30px;
   color: #000000;
   text-shadow: #ffffff 0 0px 0;
   }
.submit-container {
   margin:8px 0;
   text-align:center;
   }
.submit-button {
   border: 1px solid #447314;
   background: #6aa436;
   background: -webkit-gradient(linear, left top, left bottom, from(#8dc059), to(#6aa436));
   background: -webkit-linear-gradient(top, #8dc059, #6aa436);
   background: -moz-linear-gradient(top, #8dc059, #6aa436);
   background: -ms-linear-gradient(top, #8dc059, #6aa436);
   background: -o-linear-gradient(top, #8dc059, #6aa436);
   background-image: -ms-linear-gradient(top, #8dc059 0%, #6aa436 100%);
   -webkit-border-radius: 4px;
   -moz-border-radius: 4px;
   border-radius: 4px;
   -webkit-box-shadow: rgba(255,255,255,0.4) 0 1px 0, inset rgba(255,255,255,0.4) 0 1px 0;
   -moz-box-shadow: rgba(255,255,255,0.4) 0 1px 0, inset rgba(255,255,255,0.4) 0 1px 0;
   box-shadow: rgba(255,255,255,0.4) 0 1px 0, inset rgba(255,255,255,0.4) 0 1px 0;
   text-shadow: #addc7e 0 1px 0;
   color: #31540c;
   font-family: helvetica, serif;
   padding: 8.5px 18px;
   font-size: 14px;
   text-decoration: none;
   vertical-align: middle;
   }
.submit-button:hover {
   border: 1px solid #447314;
   text-shadow: #31540c 0 1px 0;
   background: #6aa436;
   background: -webkit-gradient(linear, left top, left bottom, from(#8dc059), to(#6aa436));
   background: -webkit-linear-gradient(top, #8dc059, #6aa436);
   background: -moz-linear-gradient(top, #8dc059, #6aa436);
   background: -ms-linear-gradient(top, #8dc059, #6aa436);
   background: -o-linear-gradient(top, #8dc059, #6aa436);
   background-image: -ms-linear-gradient(top, #8dc059 0%, #6aa436 100%);
   color: #fff;
   }
.submit-button:active {
   text-shadow: #31540c 0 1px 0;
   border: 1px solid #447314;
   background: #8dc059;
   background: -webkit-gradient(linear, left top, left bottom, from(#6aa436), to(#6aa436));
   background: -webkit-linear-gradient(top, #6aa436, #8dc059);
   background: -moz-linear-gradient(top, #6aa436, #8dc059);
   background: -ms-linear-gradient(top, #6aa436, #8dc059);
   background: -o-linear-gradient(top, #6aa436, #8dc059);
   background-image: -ms-linear-gradient(top, #6aa436 0%, #8dc059 100%);
   color: #fff;
   }
</style>
