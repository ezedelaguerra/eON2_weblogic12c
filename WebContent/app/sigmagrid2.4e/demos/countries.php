<?php

$no = $_GET['no'];
$array = array(no=>$no, country=>array($no . "USA", $no . "Spainish", $no . "Franch"), employee=>array($no . "Tom", $no . "David", $no . "Mike"), customer=>$no . 'somebody');
/*$term = $_GET['term'];
if ($_GET['type'] == 'country') 
{
   foreach($country_list as $country){
       if(strpos(strtoupper($country), strtoupper($term)) === 0){
           $array[] = array('id'=>$country, 'label'=>$country, 'value'=>$country );
       }            
   }
} elseif ($_GET['type'] == 'employee') {
    $country = $_GET['country'];
    foreach($name_list as $name){
       if(strpos(strtoupper($name), strtoupper($term)) === 0){
           $array[] = array('id'=>$country . "-" . $name, 'label'=>$country . "-" . $name, 'value'=>$country . "-" . $name );
       }            
   }
    
}*/
header('Content-type: application/json');
echo json_encode($array);
?>

