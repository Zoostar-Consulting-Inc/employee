package net.zoostar.common.core.workflow.timesheet.state;

import java.util.Objects;

import com.zoostarinc.timesheet.model.Timesheet;

import lombok.AllArgsConstructor;
import lombok.ToString;
import net.zoostar.common.core.workflow.State;

@ToString
@AllArgsConstructor
public abstract class AbstractTimesheetState implements State<Timesheet> {

	public static final State<Timesheet> STATE_NEW = new StateNew();
	
	private String name;

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AbstractTimesheetState)) {
			return false;
		}
		AbstractTimesheetState other = (AbstractTimesheetState) obj;
		return Objects.equals(name, other.name);
	}
	
}
