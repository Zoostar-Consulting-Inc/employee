package net.zoostar.common.core.workflow;

import java.util.Map;

public interface State<T extends Workflowable> {
	String getName();
	Map<String, Action<T>> getActions();
}
