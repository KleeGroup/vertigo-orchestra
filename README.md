**Orchestra** is a vertigo's extension for managing jobs and batchs.
It schedules and triggers processes with a defined strategy and provides an api for monitoring and managing these processes.
It also provides nice built-in features :
- Multi node support (with load management)
- Dead node detection
- Recovery of missed scheduled processes
- Logging (File + Db) (A user can easily access to logs even in an HMI)
- Asynchronous delegation of a treatment to another server

#What's a process in Orchestra ?
- A process is a sequential list of activities
- A process can be scheduled

#So what's an activity ?
- An activty is the execution's unit. It holds the code needed to run
- An activity can determine it's outcome
- All activities of a process' execution share a common workspace 

#How to use?
Orchestra comes as 3 jars : 
- **vertigo-orchestra-api** : Provides the api for managing processes
- **vertigo-orchestra-impl** : Provides implementations
      Two are provided :
       - DB with monitoring and log support (Only PostgreSQL is actually) supported 
       - Memory
- **vertigo-orchestra-webapi** : Provides Webservices for :
      - Managing processes
      - Monitoring executions
      - Scheduling processes

So you can use orchestra as a dependency of your project or build a stand alone app with only orchestra (see vertigo-orchestra-ui)
For configuring your app you can use the OrchestraFeatures in Java or write your config with XML (If you need help check the config of the *vertigo-orchestra-ui* project.

#What does it looks like in the code?

## Write an ActivityEngine
We have seen that an activity hold the code. The code is held in the ActivityEngine of an activity.
*ActivityEngine* is an interface that must be implemented by all activity engines.
**AbstractActivityEngine** is an abstract class that implements *ActivityEngine* and which add the necessary code for handling Logging and other services. 
=> You are encouraged to extends this abstract class to build your activity Engine.

So your first ActivityEngine that we will name MyFirstActivityEngine looks like this 
```
package io.vertigo.orchestra.execution.engine;

import io.vertigo.orchestra.impl.process.execution.AbstractActivityEngine;
import io.vertigo.orchestra.process.execution.ActivityExecutionWorkspace;

public class MyFirstActivityEngine extends AbstractActivityEngine {

	/** {@inheritDoc} */
	@Override
	public ActivityExecutionWorkspace execute(final ActivityExecutionWorkspace workspace) {
		return workspace;
	}

}
```

To handle the outcome of the process you must use the workspace state
For example set the workspace to sucess.

```
/** {@inheritDoc} */
@Override
public ActivityExecutionWorkspace execute(final ActivityExecutionWorkspace workspace) {
  workspace.setSuccess();
	return workspace;
}
```
If an activityExecution in a success then the next activty of the process is launched and has as an input the output workspace of the previous activity. With this mechanism you can share data through activities.

When writing an activity engine keep in mind to make it reusable since a process is a arrangement of activities ;-)

Now we have our activy engine we will use it in our first Process

##Define a new process
To create a new Process we need to create a new **ProcessDefinition**.
To build a ProcessDefinition you must use the **ProcessDefinitionBuilder**

Here's our First ProcessDefinition

```
final ProcessDefinition myFirstProcessDefinition = new ProcessDefinitionBuilder("MY_FIRST_ONE", "My first process")
				.addActivity("ACTIVITY", "First activy", MyFirstActivityEngine.class)
				.build();
```
You can see that our process has one activy that uses the engine *MyFirstActivityEngine*

With this builder you can configure your process (You can rely on Javadoc for the available options)
An important option is the availability to schedule automaticaly your process with a cron expression.

Once you've created you definition you must create it. For that purpose you must use the **ProcessDefinitionManager** (Inject it in your component)

```
processDefinitionManager.createOrUpdateDefinitionIfNeeded(myFirstProcessDefinition);
```

Since the process' definition is registered you can :
- Monitor executions
- Schedule new executions
through the **ProcessManager** 

Important note : The execution is always triggered by the scheduler. If you want to execute something now use the scheduleNow method of the *ProcessScheduler*

For example :
```
processManager.getScheduler().scheduleAt(myFirstProcessDefinition, DateUtil.newDate(), Collections.emptyMap());
processManager.getReport().getSummaryByDateAndName(myFirstProcessDefinition, 
		DateUtil.parse("01/01/2017", "dd/MM/yyyy"), DateUtil.parse("31/12/2017", "dd/MM/yyyy"));
```

