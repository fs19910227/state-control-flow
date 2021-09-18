package com.doubao.statemachine.web.controller.vo;

import java.util.List;

import com.doubao.statemachine.extend.model.MachineMetaInfo;
import com.doubao.statemachine.extend.support.ActionDesc;
import com.doubao.statemachine.extend.support.EventDesc;
import com.doubao.statemachine.extend.support.GuardDesc;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author doubao
 * @date 2021/05/29 00:18
 */
@Data
@Accessors(chain = true)
public class MachineDetailView {
	private MachineMetaInfo metaInfo;
	private StatesView stateView;
	private UmlView umlView;
	private List<EventDesc> eventDescList;
	private List<GuardDesc> guardDescList;
	private List<ActionDesc> actionDescList;
}
