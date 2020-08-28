<?php 
$fileInfoFiles = fopen("allInfosFiles.txt", "r") or die("Unable to open file!");
while(! feof($fileInfoFiles))
  {
  echo fgets($fileInfoFiles);
  }
fclose($fileInfoFiles);
?>