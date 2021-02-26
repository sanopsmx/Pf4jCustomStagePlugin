package com.opsmx.plugin.stage.custom;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.netflix.spinnaker.kork.artifacts.model.Artifact;
import com.netflix.spinnaker.kork.plugins.api.PluginComponent;
import com.netflix.spinnaker.orca.api.pipeline.Task;
import com.netflix.spinnaker.orca.api.pipeline.TaskResult;
import com.netflix.spinnaker.orca.api.pipeline.models.ExecutionStatus;
import com.netflix.spinnaker.orca.api.pipeline.models.StageExecution;
import com.netflix.spinnaker.orca.clouddriver.OortService;
import com.opsmx.plugin.stage.custom.services.internal.ClouddriverApi;
import com.opsmx.plugin.stage.custom.services.internal.RestOk3Client;
import org.jetbrains.annotations.NotNull;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit.client.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Extension
@PluginComponent
public class CustomStageTask implements Task {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private CustomStageConfig config;

    private ClouddriverApi oort = new RestOk3Client().getClient();

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    public CustomStageTask(){}

    public CustomStageTask(CustomStageConfig config) {
        this.config = config;
    }

    @NotNull
    @Override
    public TaskResult execute(@NotNull StageExecution stage) {

        log.info( " CustomStageTask execute start ");
        CustomStageContext context = stage.mapTo(CustomStageContext.class);
        String vmDetails = context.getVmDetails() != null ?
                context.getVmDetails() : config.getDefaultVmDetails();
        String payload = context.getPayload() != null ?
                context.getPayload() : config.getDefaultGitAccount();
        log.info( " vmDetails : " + vmDetails);
        log.info( " payload : " + payload);

        Map<String, Object> githubArtifact = null;
        try {
            githubArtifact =
                    objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {});
            prepareArtifact(githubArtifact);
        } catch (IOException e) {
            log.warn("Failure parsing githubArtifact from {}", githubArtifact, e);
            throw new IllegalStateException(e); // forces a retry
        }

        Map<String, Object> outputs = new HashMap<>();
        Map<String, Object> contextMap = new HashMap<>();
        log.info( " CustomStageTask execute end ");
        return TaskResult.builder(ExecutionStatus.SUCCEEDED)
                .context(contextMap)
                .outputs(outputs)
                .build();
    }

    private Artifact prepareArtifact(Map<String, Object> artifact) {

        log.info( " prepareArtifact start ");
        Artifact githubArtifact = Artifact.builder().name("testCustomStage").
                type((String) artifact.get("type")).
                artifactAccount((String) artifact.get("artifactAccount")).
                version((String) artifact.get("version")).
                reference((String) artifact.get("reference")).build();
        log.info( " prepareArtifact end ");
        return githubArtifact;
    }

    private String fetchGithubArtifact(Artifact artifact) {

        log.info( " fetchGithubArtifact ");
        Response response = oort.fetchArtifact(artifact);
        InputStream artifactInputStream;
        try {
            artifactInputStream = response.getBody().in();
        } catch (IOException e) {
            log.warn("Failure fetching script.sh from {}", artifact, e);
            throw new IllegalStateException(e); // forces a retry
        }
        try (InputStreamReader rd = new InputStreamReader(artifactInputStream)) {
            return CharStreams.toString(rd);
        } catch (IOException e) {
            log.warn("Failure fetching script.sh from {}", artifact, e);
            throw new IllegalStateException(e); // forces a retry
        }
    }
}
