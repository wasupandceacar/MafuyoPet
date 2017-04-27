Sub bat
echo off & cls
start wscript -e:vbs "%~f0"
Exit Sub 
End Sub

Set WshShell = WScript.CreateObject("WScript.Shell")
strDesktop = WshShell.SpecialFolders("Startup")
Set objFSO = CreateObject("Scripting.FileSystemObject") 
objFSO.DeleteFile(strDesktop & "\Mafuyo.lnk")