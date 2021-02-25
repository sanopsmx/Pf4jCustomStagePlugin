package com.opsmx.plugin.stage.custom;

import com.netflix.spinnaker.kork.plugins.api.PluginComponent;
import com.netflix.spinnaker.orca.api.pipeline.Task;
import com.netflix.spinnaker.orca.api.pipeline.TaskResult;
import com.netflix.spinnaker.orca.api.pipeline.models.ExecutionStatus;
import com.netflix.spinnaker.orca.api.pipeline.models.StageExecution;
import org.jetbrains.annotations.NotNull;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Extension
@PluginComponent
public class CustomStageTask implements Task {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private CustomStageConfig config;

    public CustomStageTask(CustomStageConfig config) {
        this.config = config;
    }

    @NotNull
    @Override
    public TaskResult execute(@NotNull StageExecution stage) {

        log.info( " CustomStageTask execute ");
        CustomStageContext context = stage.mapTo(CustomStageContext.class);
        String vmDetails = context.getVmDetails() != null ?
                context.getVmDetails() : config.getDefaultVmDetails();
        String filename = context.getFilename() != null ?
                context.getFilename() : config.getDefaultFilename();
        String gitAccount = context.getGitAccount() != null ?
                context.getGitAccount() : config.getDefaultGitAccount();
        log.info( " vmDetails : " + vmDetails);
        log.info( " filename : " + filename);
        log.info( " gitAccount : " + gitAccount);
        Map<String, Object> outputs = new HashMap<>();
        Map<String, Object> contextMap = new HashMap<>();
        return TaskResult.builder(ExecutionStatus.SUCCEEDED)
                .context(contextMap)
                .outputs(outputs)
                .build();
    }
}
