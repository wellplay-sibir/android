<?php
if (isset($_POST["jjj"])){
  $phone = $_POST['jjj'];
$curl = curl_init();

curl_setopt_array($curl, array(
  CURLOPT_URL => "http://astercon.ru:81/password",
  CURLOPT_RETURNTRANSFER => true,
  CURLOPT_HTTPHEADER => array('Content-Type: application/json'),
  CURLOPT_ENCODING => "",
  CURLOPT_MAXREDIRS => 10,
  CURLOPT_TIMEOUT => 0,
  CURLOPT_FOLLOWLOCATION => true,
  CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
  CURLOPT_CUSTOMREQUEST => "POST",
  CURLOPT_POSTFIELDS =>"{\r\n    \"number_phone\": \"" . $phone ."\"\r\n}",
));

$response = curl_exec($curl);

curl_close($curl);
echo $response;

}else{
    // echo "no";
}


//proverka
if (isset($_POST["proverka"])){
  $proverka = $_POST['proverka'];
$curl = curl_init();

curl_setopt_array($curl, array(
  CURLOPT_URL => "http://astercon.ru:81/auth",
  CURLOPT_RETURNTRANSFER => true,
  CURLOPT_HTTPHEADER => array('Content-Type: application/json'),
  CURLOPT_ENCODING => "",
  CURLOPT_MAXREDIRS => 10,
  CURLOPT_TIMEOUT => 0,
  CURLOPT_FOLLOWLOCATION => true,
  CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
  CURLOPT_CUSTOMREQUEST => "POST",
  CURLOPT_POSTFIELDS => $proverka,
));

$response = curl_exec($curl);

curl_close($curl);
echo $response;

}else{
    // echo "no";
}


// $curl = curl_init();

// curl_setopt_array($curl, array(
//   CURLOPT_URL => "{{url}}/documents",
//   CURLOPT_RETURNTRANSFER => true,
//   CURLOPT_ENCODING => "",
//   CURLOPT_MAXREDIRS => 10,
//   CURLOPT_TIMEOUT => 0,
//   CURLOPT_FOLLOWLOCATION => true,
//   CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
//   CURLOPT_CUSTOMREQUEST => "GET",
//   CURLOPT_HTTPHEADER => array(
//     "UserToken: {{UserToken}}"
//   ),
// ));

// $response = curl_exec($curl);

// curl_close($curl);
// echo $response;







?>