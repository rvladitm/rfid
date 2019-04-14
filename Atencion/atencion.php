<?php
include_once("header.php");
$attData = array();
$totDays = 0;
if(isset($_REQUEST['submit'])){
	$totDays = cal_days_in_month(CAL_GREGORIAN,$_REQUEST['month'],$_REQUEST['year']);
	$attData = $commonObj->getAttendanceData($_REQUEST['month'],$_REQUEST['year']);
	//print_r($attData);
	//print_r(date("d",strtotime($attData[0]['punch_time'])));
}
?>


<div class="container" style="background-color: #D6D6D6;">
<div class="row">
	<div class="col-sm-12">
	  <h4>Administrador de Asistencia</h4>
	  	  <h5>Por defecto se muestra el mes y año actual</h5>

	  	  	  
	  <form name="showAtt" method="post">
		<div class="col-sm-4"><label>Seleccionar mes</label>
			<select name="month" class="form-control">
			  	<?php for($i=date("n");$i>=1;$i--){?>
			  		<option value="<?php echo $i;?>"><?php echo $i;?></option>
			  	<?php }?>
			</select>	  	
	  	</div>

		<div class="col-sm-4"><label>Seleccionar año</label>
		  	<select name="year" class="form-control">
			  	<?php for($i=date("Y");$i>=2017;$i--){?>
			  		<option value="<?php echo $i;?>"><?php echo $i;?></option>
			  	<?php }?>
			</select>
	  	</div>
	  	<div class="col-sm-4">
	  	<p>&nbsp;</p>
	  		<input type="submit" value="Mostrar" name="submit" class="btn btn-info">
	  	</div>
	  </form>
	</div>
</div>
<div class="row">
	<div class="col-sm-2 " style="width: 32.5%; margin-left: 14px;">
		<label class="btn btn-success btn-sm" style="margin-bottom: 10px; margin-top: 5px;">Mes:<?php echo @$_REQUEST['month'];?></label>
	</div>
	<div class="col-sm-2"><label class="btn btn-success btn-sm" style="margin-top: 5px;">Año: <?php echo @$_REQUEST['year'];?></label></div>
</div>
<div class="row">
	<div class="col-sm-12" style="overflow: scroll;">
		<?php if($totDays>0){?>

		<ul class="list-group" style="    margin-bottom: -1px; padding: 10px 15px;  display: block; position: relative; width:25%;">
			<li class="list-group-item list-group-item-info">Dias del mes</li>
			<li class="list-group-item list-group-item-success">Días Marcados</li>

		</ul>
		<table class="table table-dark" style="background-color: white; vertical-align: middle;">
			<tr>
				<th>
					Nombre
				</th>
				<?php for($i=1;$i<=$totDays;$i++){?>
				<th>
					<?php echo $i;?>
				</th>
				<?php }?>

				<?php foreach($attData as $attk=>$attv){
					$punchin = $commonObj->getTimeOfDate($attData[$attk]['punchin']);
							$punchout = $commonObj->getTimeOfDate($attData[$attk]['punchout']);
					?>
				<tr>
					<th class="secondary" style="vertical-align: middle;">
						<?php echo $attv['name'];?>
					</th>
					<?php for($i=1;$i<=$totDays;$i++){?>
					
						<?php if($commonObj->getDayOfDate($attData[$attk]['punch_time']) == $i){
							
							echo "<td class='success' id='att_$i'>".$punchin.'-'.$punchout;?>
							<table class="table table-responsive"style="display: none; position:relative;min-width:100px;max-width:200px; margin-top: 10px;  " id="<?php echo "det_att_".$i;?>">
								<tr>
								<td>Horas:</td>
								<td><?php echo $commonObj->getHoursBetweenDates($attData[$attk]['punchin'],$attData[$attk]['punchout']);?>
								</td></tr>
								<tr>
									<td>UID:</td>
									<td><?php echo $attData[$attk]['rfid_uid'];?></td>
								</tr>
							</table>
							<?php
							}else {echo "<td class='info'>-";}?>
					</td>
					<?php }?>
				</tr>
				<?php }?>
			</tr>
		</table>
		<?php }?>
	</div>
</div>
</div>
</body>
</html>
<script>
$(document).ready(function(){
	$("td").hover( 
		
		function () {
			var id = "#det_"+this.id;
                  $(id).css({"display":"block"});
               }, 
				
               function () {
               	var id = "#det_"+this.id;
                  $(id).css({"display":"none"});
               
		
	});

});
</script>
