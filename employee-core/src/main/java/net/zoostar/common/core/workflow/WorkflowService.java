package net.zoostar.common.core.workflow;

import net.zoostar.common.core.Transformer;

public interface WorkflowService<T extends Workflowable> {
	State<T> getState(String state);
	Action<T> getAction(String action);
	T process(Transformer<T> transformer);
}
