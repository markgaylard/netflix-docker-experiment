<!DOCTYPE html>
<html lang="en">
<head>
</head>
 
<#assign keys = env?keys>
<table>
<#list keys as key><tr><td>${key} = ${env[key]}</td></tr></#list> 
</table>
<#assign keys2 = prop?keys>
<table>
<#list keys2 as key><tr><td>${key} = ${prop[key]}</td></tr> </#list> 
</table>
</body>
</html>