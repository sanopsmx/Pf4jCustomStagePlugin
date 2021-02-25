package com.opsmx.plugin.stage.custom;

import com.netflix.spinnaker.orca.api.pipeline.Task;
import com.netflix.spinnaker.orca.api.pipeline.TaskResult;
import com.netflix.spinnaker.orca.api.pipeline.graph.StageDefinitionBuilder;
import com.netflix.spinnaker.orca.api.pipeline.graph.TaskNode;
import com.netflix.spinnaker.orca.api.pipeline.models.ExecutionStatus;
import com.netflix.spinnaker.orca.api.pipeline.models.StageExecution;
import org.jetbrains.annotations.NotNull;
import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CustomStagePlugin extends Plugin {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Constructor to be used by plugin manager for plugin instantiation.
     * Your plugins have to provide constructor with this exact signature to
     * be successfully loaded by manager.
     *
     * @param wrapper
     */
    public CustomStagePlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    public void start() {
        log.info("CustomStagePlugin.start()");
    }

    public void stop() {
        log.info("CustomStagePlugin.stop()");
    }

    @Extension
    class CustomStageStage implements StageDefinitionBuilder {

        /**
         * This function describes the sequence of substeps, or "tasks" that comprise this
         * stage. The task graph is generally linear though there are some looping mechanisms.
         * <p>
         * This method is called just before a stage is executed. The task graph can be generated
         * programmatically based on the stage's context.
         */
        public void taskGraph(@NotNull StageExecution stage, @NotNull TaskNode.Builder builder) {
            builder.withTask("customStage", CustomStageTask.class);
        }
    }

    @Extension
    class CustomStageTask implements Task {

        private final Logger log = LoggerFactory.getLogger(getClass());
        private CustomStageConfig config;

        public CustomStageTask(CustomStageConfig config) {
            this.config = config;
        }

        @NotNull
        @Override
        public TaskResult execute(@NotNull StageExecution stage) {

            CustomStageContext context = stage.mapTo(CustomStageContext.class);

            String vmDetails = context.getVmDetails() != null ?
                    context.getVmDetails() : config.getVmDetails();
            String filename = context.getFilename() != null ?
                    context.getFilename() : config.getFilename();
            String gitAccount = context.getGitAccount() != null ?
                    context.getGitAccount() : config.getGitAccount();

            Map<String, Object> outputs = new HashMap<>();
            Map<String, Object> contextMap = new HashMap<>();
            return TaskResult.builder(ExecutionStatus.SUCCEEDED)
                    .context(contextMap)
                    .outputs(outputs)
                    .build();
        }
    }
}

