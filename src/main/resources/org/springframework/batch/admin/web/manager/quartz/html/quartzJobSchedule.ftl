<#escape x as x?html>
<#if quartzJobInfo?? && quartzJobInfo.launchable>
	<p/>
	<#assign quartz_schedule_url><@spring.url relativeUrl="${servletPath}/quartz/${quartzJobInfo.name}"/></#assign>
	<form id="quartzScheduleRequestForm" name="quartzScheduleRequest" action="${quartz_schedule_url}" method="POST">

		<#if quartzScheduleRequest??>
			<@spring.bind path="quartzScheduleRequest" />
			<@spring.showErrors separator="<br/>" classOrStyle="error" /><br/>
		</#if>
		
		<!-- CRON Expression -->
		<ol>
			<li>
			<label for="cronExpression">Cron Expression</label>
			<input id="cronExpression" type="input" name="cronExpression" class="cronExpression">
				<#if cronExpression??>${cronExpression}</#if>
			</input>
			</li>
		</ol>
		
		<ol>
			<li>
			<label for="quartzJobParameters">Job Parameters (key=value pairs)</label>
			<textarea id="quartzJobParameters" name="quartzJobParameters" class="quartzJobParameters"><#if quartzJobParameters??>${quartzJobParameters}</#if></textarea> 
			(<#if quartzJobInfo.incrementable>Incrementable<#else>Not incrementable</#if>)
			</li>
		</ol>
		
		<!-- Scheduling the cron -->
		<ol>
			<li>
				<label for="Schedule">Job name=${quartzJobInfo.name}</label>
				<input id="Schedule" type="submit" value="Schedule" name="Schedule" />
			</li>
		</ol>

		<br/><#if quartzJobInfo.incrementable>
		<p>If the parameters are marked as "Incrementable" then the launch button launches either the <em>next</em> 
		instance of the job in the sequence defined by the incrementer, or if the last execution failed it restarts it.  
		The old parameters are shown above, and they will passed into the configured incrementer. You can always add 
		new parameters if you want to (but not to a restart).</p>
		<#else>
		<p>If the parameters are marked as "Not incrementable" then the launch button launches an 
		instance of the job with the parameters shown (which might be a restart if the last execution failed).
		You can always add new parameters if you want to (but not if you want to restart).</p>
		</#if>

		<input type="hidden" name="origin" value="quartzJob"/>
	</form>
</#if>
</#escape>
