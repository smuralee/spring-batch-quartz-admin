<#import "/spring.ftl" as spring />
<#escape x as x?html>

<div id="quartzJob">
	
	<#include "quartzJobSchedule.ftl">
		
	<#if quartzJobInfo?? && quartzJobInstances?? && quartzJobInstances?size!=0>
		
			<br/>
			<h2>Job Instances for Job (${quartzJobInfo.name})</h2>
			
			<table title="Jobs Instances" class="bordered-table">
				<thead>
					<tr>
						<th>ID</th>
						<th>&nbsp;</th>
						<th>JobExecution Count</th>
						<th>Last JobExecution</th>
						<th>Last JobExecution Date</th>
						<th>Last JobExecution Start</th>
						<th>Last JobExecution Duration</th>
						<th>Last JobExecution Parameters</th>
					</tr>
				</thead>
				<tbody>
					<#list quartzJobInstances as quartzJobInstanceInfo>
						<#if quartzJobInstanceInfo_index % 2 == 0>
							<#assign rowClass="name-sublevel1-even"/>
						<#else>
							<#assign rowClass="name-sublevel1-odd"/>
						</#if>
						<#assign executions_url><@spring.url relativeUrl="${servletPath}/jobs/${quartzJobInfo.name}/${quartzJobInstanceInfo.id?c}"/></#assign>
						<tr class="${rowClass}">
							<td>${quartzJobInstanceInfo.id}</td>
							<td><a href="${executions_url}">executions</a></td>
							<td>${quartzJobInstanceInfo.jobExecutionCount}</td>
							<#if quartzJobInstanceInfo.lastJobExecution??>
								<#assign execution_url><@spring.url relativeUrl="${servletPath}/jobs/executions/${quartzJobInstanceInfo.lastJobExecution.id?c}"/></#assign>
								<td><a href="${execution_url}">${quartzJobInstanceInfo.lastJobExecution.status}</a></td>
								<td>${quartzJobInstanceInfo.lastJobExecutionInfo.startDate}</td>
								<td>${quartzJobInstanceInfo.lastJobExecutionInfo.startTime}</td>
								<td>${quartzJobInstanceInfo.lastJobExecutionInfo.duration}</td>
								<td>${quartzJobInstanceInfo.lastJobExecution.jobParameters}</td>
							<#else>
								<td>?</td>
								<td>?</td>
								<td>?</td>
								<td>?</td>
								<td>?</td>
							</#if>
						</tr>
					</#list>
				</tbody>
			</table>
			<ul class="controlLinks">
				<li>Rows: ${startQuartzJobInstance}-${endQuartzJobInstance} of ${totalQuartzJobInstances}</li> 
				<#assign job_url><@spring.url relativeUrl="${servletPath}/jobs/${quartzJobInfo.name}"/></#assign>
				<#if nextQuartzJobInstance??><li><a href="${job_url}?startJobInstance=${nextQuartzJobInstance?c}&pageSize=${pageSize!20}">Next</a></li></#if>
				<#if previousQuartzJobInstance??><li><a href="${job_url}?startJobInstance=${previousQuartzJobInstance?c}&pageSize=${pageSize!20}">Previous</a></li></#if>
				<!-- TODO: enable pageSize editing -->
				<li>Page Size: ${pageSize!20}</li>
			</ul>
	
			<p>The table above shows instances of this job with an indication of the status of the last execution.  
			If you want to look at all executions for <a href="${executions_url}">see here</a>.</p>
	
	<#else>
		<#if quartzJobName??>
			<@spring.bind path="quartzJobName" />
			<@spring.showErrors separator="<br/>" classOrStyle="error" /><br/>
		<#else>
			<p>There are no job instances for this job.</p>
		</#if>
	</#if>
	
</div><!-- jobs -->
</#escape>