package com.antgroup.openspg.builder.core.strategy.fusing;

import com.antgroup.openspg.builder.core.runtime.BuilderContext;
import com.antgroup.openspg.builder.model.exception.BuilderException;
import com.antgroup.openspg.builder.model.exception.FusingException;
import com.antgroup.openspg.builder.model.record.BaseAdvancedRecord;
import java.util.List;

public interface SubGraphFusing {

  void init(BuilderContext context) throws BuilderException;

  List<BaseAdvancedRecord> subGraphFusing(BaseAdvancedRecord advancedRecord) throws FusingException;
}
