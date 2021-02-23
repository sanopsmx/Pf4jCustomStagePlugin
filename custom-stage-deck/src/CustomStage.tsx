import React from 'react';
import {
  ExecutionDetailsTasks,
  IStageTypeConfig,
} from '@spinnaker/core';

import { CustomStageExecutionDetails } from './CustomStageExecutionDetails';
import { CustomStageConfig, validate } from './CustomStageConfig';

/*
  Define Spinnaker Stages with IStageTypeConfig.
  Required options: https://github.com/spinnaker/deck/master/app/scripts/modules/core/src/domain/IStageTypeConfig.ts
  - label -> The name of the Stage
  - description -> Long form that describes what the Stage actually does
  - key -> A unique name for the Stage in the UI; ties to Orca backend
  - component -> The rendered React component
  - validateFn -> A validation function for the stage config form.
 */
export const customStage: IStageTypeConfig = {
  key: 'customStage',
  label: `Custom Stage`,
  description: 'Stage that waits a random amount of time up to the max input',
  component: CustomStageConfig, // stage config
  executionDetailsSections: [CustomStageExecutionDetails, ExecutionDetailsTasks],
  validateFn: validate,
};
