package net.zoostar.common.core.workflow;

public interface Workflowable<T extends Workflowable<T>> {
	State<T> getState();
}
