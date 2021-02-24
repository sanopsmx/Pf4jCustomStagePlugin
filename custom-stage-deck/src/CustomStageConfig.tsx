import React from 'react';
import {
  ExecutionDetailsSection,
  ExecutionDetailsTasks,
  FormikFormField,
  FormikStageConfig,
  FormValidator,
  HelpContentsRegistry,
  HelpField,
  IExecutionDetailsSectionProps,
  IStage,
  StageConfigField,
  TextInput,
  IStageConfigProps,
  IStageTypeConfig,
  NumberInput,
  Validators,
} from '@spinnaker/core';

import './CustomStage.less';

/*
  IStageConfigProps defines properties passed to all Spinnaker Stages.
  See IStageConfigProps.ts (https://github.com/spinnaker/deck/blob/master/app/scripts/modules/core/src/pipeline/config/stages/common/IStageConfigProps.ts) for a complete list of properties.
  Pass a JSON object to the `updateStageField` method to add the `maxWaitTime` to the Stage.

  This method returns JSX (https://reactjs.org/docs/introducing-jsx.html) that gets displayed in the Spinnaker UI.
 */
export function CustomStageConfig(props: IStageConfigProps) {
  return (
    <div className="CustomStageConfig">
      <StageConfigField label="Remote vm details">
        <TextInput
           type="text"
           className="form-control"
           onChange={props.updateStage}
           value="2"
        />
      </StageConfigField>
      <StageConfigField label="Git account">
        <TextInput
          type="text"
          className="form-control"
          onChange={props.updateStage}
          value="3"
        />
      </StageConfigField>
      <StageConfigField label="Filename">
        <TextInput
          type="text"
          className="form-control"
          onChange={props.updateStage}
          value="3"
          />
      </StageConfigField>
    </div>
  );
}

export function validate(stageConfig: IStage) {
  const validator = new FormValidator(stageConfig);

  validator
    .field('maxWaitTime')
    .required()
    .withValidators((value, label) => (value < 0 ? `${label} must be non-negative` : undefined));

  return validator.validateForm();
}

