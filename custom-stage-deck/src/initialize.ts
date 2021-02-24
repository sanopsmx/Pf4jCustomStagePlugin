import { HelpContentsRegistry } from '@spinnaker/core';

/*
  This is a contrived example of how to use an `initialize` function to hook into arbitrary Deck services. 
  This `initialize` function provides the help field text for the `CustomStageConfig` stage form.

  You can hook into any service exported by the `@spinnaker/core` NPM module, e.g.:
   - CloudProviderRegistry
   - DeploymentStrategyRegistry

  When you use a registry, you are diving into Deck's implementation to add functionality. 
  These registries and their methods may change without warning.
*/
export const initialize = () => {
  HelpContentsRegistry.register('opsmx.customStage.vmDetails', 'Details of the remote server where script needs to be executed.');
  HelpContentsRegistry.register('opsmx.customStage.gitAccount', 'Git account details from where artifact can be fetched.');
  HelpContentsRegistry.register('opsmx.customStage.filename', 'Scripts of this filename to be executed on the vm');
};
