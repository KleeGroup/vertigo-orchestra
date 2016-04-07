// seearch services
import service from '../../../../services/search';

//search configurations
import cartridgeConfiguration from './cartridge';
import lineMapper from '../../lines/mapper';
import onLineClick from '../../lines/line-click';
import {scopesConfig} from '../../../../config/scopes';

export const configuration = {
    onLineClick,
    isSelection: true,
    cartridgeConfiguration,
    service,
    lineComponentMapper: lineMapper,
    groupMaxRows: 5, // ne fonctionne pas, n'est pas renvoyé dans la config du service search.
    scopesConfig: scopesConfig
};
