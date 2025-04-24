package net.zoostar.common.core.workflow;

public interface Action<T extends Workflowable<T>> {
	void execute(T workflowble);
}
