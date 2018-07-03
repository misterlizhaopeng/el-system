${hello}


${student.id}-${student.name}<br/>

<table>
<#list stus as stu>
	<tr>
		<td>
			${stu_index}
		</td>
		<td>
			${stu.id}
		</td>
		<td>
			${stu.name}
		</td>
	</tr>
</#list>
</table>
