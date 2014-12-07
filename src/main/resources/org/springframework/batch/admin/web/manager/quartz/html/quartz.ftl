<#import "/spring.ftl" as spring />
<div id="quartz">

	<#if quartzJobs?? && quartzJobs?size!=0>
		
			<h2>Job Names Registered (Click on the Job Name to Schedule)</h2>
	
			<table title="Jobs Names" class="bordered-table">
				<tr>
					<th>Name</th>
					<th>Description</th>
					<th>Execution&nbsp;Count</th>
					<th>Launchable</th>
					<th>Incrementable</th>
				</tr>
				<#list quartzJobs as quartzJob>
					<#if quartzJob_index % 2 == 0>
						<#assign rowClass="name-sublevel1-even"/>
					<#else>
						<#assign rowClass="name-sublevel1-odd"/>
					</#if>
					<tr class="${rowClass}">
						<#assign quartzJob_url><@spring.url relativeUrl="${servletPath}/quartz/${quartzJob.name}"/></#assign>
						<td><a href="${quartzJob_url}">${quartzJob.name}</a></td>
						<td><@spring.messageText code="${quartzJob.name}.description" text="No description"/></td>
						<td>${quartzJob.executionCount}</td>
						<td><#if quartzJob.launchable??>${quartzJob.launchable?string}<#else>?</#if></td>
						<td><#if quartzJob.incrementable??>${quartzJob.incrementable?string}<#else>?</#if></td>
					</tr>
				</#list>
			</table>
			<ul class="controlLinks">
				<li>Rows: ${startQuartzJob}-${endQuartzJob} of ${totalQuartzJobs}</li> 
				<#assign quartzJob_url><@spring.url relativeUrl="${servletPath}/quartz"/></#assign>
				<#if nextQuartzJob??><li><a href="${quartzJob_url}?startJob=${nextQuartzJob?c}&pageSize=${pageSize!20}">Next</a></li></#if>
				<#if previousQuartzJob??><li><a href="${quartzJob_url}?startJob=${previousQuartzJob?c}&pageSize=${pageSize!20}">Previous</a></li></#if>
				<!-- TODO: enable pageSize editing -->
				<li>Page Size: ${pageSize!20}</li>
			</ul>
	
	<#else>
		<p>There are no jobs registered.</p>
	</#if>
	
</div>