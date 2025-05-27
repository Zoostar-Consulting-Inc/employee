package net.zoostar.common.core.workflow;

public interface Action<T extends Workflowable> {
	String getName();
	void execute(T workflowble);
}
