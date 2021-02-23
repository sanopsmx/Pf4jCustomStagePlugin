package io.armory.plugin.stage.custom

/**
 * Context is used within the stage itself and returned to the Orca pipeline execution.
 */
data class CustomStageContext(var maxWaitTime: Int?)
