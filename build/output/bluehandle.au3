##########################################################################
# Copyright (c) 2011 cnfree.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
# cnfree - initial API and implementation
##########################################################################
#Region ;
#AutoIt3Wrapper_Compression=4
#AutoIt3Wrapper_UseUpx=n
#AutoIt3Wrapper_UseX64=n
#AutoIt3Wrapper_Res_Description=蓝色手柄客户端
#AutoIt3Wrapper_Res_LegalCopyright=whyun.com
#AutoIt3Wrapper_Res_Fileversion=1.4
#AutoIt3Wrapper_Res_Field=ProductName|蓝色手柄客户端
#AutoIt3Wrapper_Res_Field=ProductVersion|1.4
#AutoIt3Wrapper_Res_Language=2052
#EndRegion ;
$javahome = ""
$jrehome = ""
$requiredversion="1.6"
$processor_architecture = EnvGet("processor_architecture")
$regnode = ""
If($processor_architecture = "x86") Then
	$regnode="HKEY_LOCAL_MACHINE\SOFTWARE"
Else
	$regnode="HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node"
EndIf
$currentversion = RegRead($regnode&"\JavaSoft\Java Development Kit", "CurrentVersion")
If @error == 0 AND $currentversion >= $requiredversion Then
	$javahome = RegRead($regnode&"\JavaSoft\Java Development Kit\" & $currentversion, "JavaHome")
EndIf
$currentversion = RegRead($regnode&"\JavaSoft\Java Runtime Environment", "CurrentVersion")
If @error == 0 AND $currentversion >= $requiredversion Then
	$jrehome = RegRead($regnode&"\JavaSoft\Java Runtime Environment\" & $currentversion, "JavaHome")
EndIf
If Not FileExists( @scriptdir&'\jre\bin\javaw.exe' ) AND Not FileExists( $javahome&'\bin\javaw.exe' ) AND Not FileExists( $jrehome&'\bin\javaw.exe' ) Then
	MsgBox(0+16,"Error", "The bluehandle executable launcher was unable to locate available Java Runtime Environment.")
	Exit
EndIf
$vmargs = ""
$programargs = ""
If $CmdLine[0] > 0 Then
	For $I = 1 To $CmdLine[0]
		For $J = 0 To StringLen($CmdLine[$I])
			$ch = StringMid($CmdLine[$I], $J, 1)
			If $ch == " " Then
				$ch = ('"'&" "&'"')
			ElseIf $ch == '"' Then
				$ch = ('"'&'"'&'"')
			EndIf
			$programargs = $programargs&$ch
		Next
		$programargs = $programargs&" "
	Next
EndIf
Run(@ComSpec & ' /C cd /d "'&@scriptdir&'" & if exist "'&$javahome&'\bin\javaw.exe" ("'&$javahome&'\bin\javaw.exe" -cp ".\app\bluetoothHandle.jar;" my.sunny.ui.MainClientUI '&$programargs&' ) else if exist "'&$jrehome&'\bin\javaw.exe" ("'&$jrehome&'\bin\javaw.exe" -cp ".\app\bluetoothHandle.jar;" my.sunny.ui.MainClientUI '&$programargs&' ) else (".\jre\bin\javaw.exe" -cp ".\app\bluetoothHandle.jar;" my.sunny.ui.MainClientUI '&$programargs&' )', '', @SW_HIDE)
