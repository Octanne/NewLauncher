<style>
body{
	text-align: center;
}
#passLabel {
	margin-bottom: 3px;
	font-size: 20px;
}
#logs{
	margin-top: 10px;
	border: 1.5px black solid;
	display: inline-table;
	font-size: 16px;
	
}
#button {
	margin-top: 10px;
	font-size: 16px;
}
</style>

<body>
	<h1>Launcher | Update System</h1>
	<div id="passLabel">Password</div>
	<form method="POST" action="/update.php">
	<input name="password" type="password"/>
	<div><button id="button" >Start Scan</button></div>
	</form>
	<h1>Logs</h1>
	<section id="logs">
		<div id="LogLegend">PATH : MD5 HASH : UPDATE STATUT</div>
		<?php
		if(isSet($_POST["password"]) && $_POST["password"] == "Karting1465"){
			ini_set('display_errors', 1);
			ini_set('display_startup_errors', 1);
			error_reporting(E_ALL);

			// Var
			$fileInfoFiles = fopen("allInfosFiles.txt", "w") or die("Unable to open file!");
			$fileDeleterExcempt = array(
				"logs/",
				"launcher.properties",
			);
			$path = "files";
			$listFiles = scandir($path);
			// Functions
			function listDir($files, $path, $dataFile) {
				for($i = 2; $i < count($files); ++$i) {
					$fullPath = $path . "/" . $files[$i];
					if(is_dir($fullPath)){
						listDir(scandir($fullPath), $fullPath, $dataFile);
					}else{
						$line = substr ($fullPath,6) . ":" . md5_file($fullPath) . ":" . isActive($fullPath) . "\n";
						?> <a id="logLine"> <?php echo $line . "<br>"; ?> </a> <?php
						fwrite($dataFile, $line);
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
			// Script Begin 
			for($i = 2; $i < count($listFiles); ++$i) {
				$fullPath = $path . "/" . $listFiles[$i];
				if(is_dir($fullPath)){
					listDir(scandir($fullPath), $fullPath, $fileInfoFiles);
				}else{
					$line = substr ($fullPath,6) . ":" . md5_file($fullPath) . ":" . isActive($fullPath) . "\n";
					?> <a id="logLine"> <?php echo $line . "<br>"; ?> </a> <?php
					fwrite($fileInfoFiles, $line);
				}
			}
			fclose($fileInfoFiles);
		}
		?>
	</section>
</body>