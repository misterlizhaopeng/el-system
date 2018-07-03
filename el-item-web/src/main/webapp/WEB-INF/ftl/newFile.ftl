<#include "hello.ftl">

adafaa

如果myval是空值的话，默认输出abc

${myval!"abc"}

<#if myval??>
不为空值
<#else>
为空值
</#if> 

${date?date}  --   ${date?string("yyyy-MM/dd HH:mm:ss")}