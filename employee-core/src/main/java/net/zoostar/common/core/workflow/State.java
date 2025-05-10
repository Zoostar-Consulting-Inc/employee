package net.zoostar.common.core.workflow;

import java.util.Map;

public interface State<T extends Workflowable<T>> {
	Map<String, Action<T>> getActions();
}
