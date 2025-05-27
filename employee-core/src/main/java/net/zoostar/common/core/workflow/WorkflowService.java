package net.zoostar.common.core.workflow;

import net.zoostar.common.core.Transformer;

public interface WorkflowService<T extends Workflowable> {
	T process(Transformer<T> transformer);
}
