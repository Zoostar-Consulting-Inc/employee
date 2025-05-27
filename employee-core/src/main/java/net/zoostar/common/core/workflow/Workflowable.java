package net.zoostar.common.core.workflow;

public interface Workflowable {
	<T extends Workflowable> State<T> getState();
}
