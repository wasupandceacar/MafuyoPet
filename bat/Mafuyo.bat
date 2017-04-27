Sub bat
echo off & cls
start wscript -e:vbs "%~f0"
Exit Sub 
End Sub

Set WshShell = WScript.CreateObject("WScript.Shell")
strDesktop = WshShell.SpecialFolders("Startup")
set oShellLink = WshShell.CreateShortcut(strDesktop & "\Mafuyo.lnk")
oShellLink.TargetPath = "G:\MafuyoPet\MafuyoPet.exe"
oShellLink.WindowStyle = 1
oShellLink.IconLocation = "G:\MafuyoPet\MafuyoPet.exe, 0"
oShellLink.WorkingDirectory = "G:\MafuyoPet"
oShellLink.Save