<?php
require("config.php"); 
//$commonObj = new Common();
  if(isset($_REQUEST['submit'])){
    $commonObj->validateLogin();
  }
?>


<!DOCTYPE html>
<html lang="en">
<head>
  <title>Sistema de Asistencia</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
  <link rel="stylesheet" href="estilos.css">
  <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>
<body background="src/xd.png">
<div class="sidenav">
    <div class="login-main-text">
       <h2>Proyecto<br>"Asistencia RFID"</h2>
    </div>
 </div>
 <div class="main">
    <div class="col-md-6 col-sm-12">
        <?php echo $commonObj->getSuccessMsg();
            echo $commonObj->getErrorMsg();
            $commonObj->unsetMessage();
        ?>
       <div class="login-form">
          <form method='post'>
             <div class="form-group">
                <label for="email">Correo:</label>
                <input type="email" class="form-control" id="email" placeholder="Correo" name="username">
             </div>
             <div class="form-group">
                <label for="pwd">Contraseña:</label>
                <input type="password" class="form-control" id="pwd" placeholder="Contraseña" name="password">
             </div>
             <button type="submit" class="btn btn-black" name="submit" value="login">Enviar</button>
          </form>
       </div>
    </div>
 </div>
</body>
</html>