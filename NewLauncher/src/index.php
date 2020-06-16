<?php

$fileDeleterExcempt = array(
    "logs/",
    "launcher.properties",
);

$path = "files";
$listFiles = scandir($path);
for($i = 2; $i < count($listFiles); ++$i) {
    $fullPath = $path . "/" . $listFiles[$i];
    if(is_dir($fullPath)){
        listDir(scandir($fullPath), $fullPath);
    }else{
        echo substr ($fullPath,6) . ":" . md5_file($fullPath) . ":" . isActive($fullPath) . "\n";
    }
}

function listDir($files, $path){
    for($i = 2; $i < count($files); ++$i) {
        $fullPath = $path . "/" . $files[$i];
        if(is_dir($fullPath)){
            listDir(scandir($fullPath), $fullPath);
        }else{
            echo substr ($fullPath,6) . ":" . md5_file($fullPath) . ":" . isActive($fullPath) . "\n";
        }
    }
}

function isActive($pathFile){
    $dontUpdate = array(
        "files/options.txt",
        "files/optionsof.txt"
    );
    if(in_array($pathFile, $dontUpdate)) return 0;
    else return 1;
}

?>