import React from 'react';
import {translate} from 'focus-core/translation';

const links = [
    { reference:'countries', href: '#admin/masterdata/countries', title: 'view.admin.masterdata.countries'},
    { reference:'movietype', href: '#admin/masterdata/movietype', title: 'view.admin.masterdata.movietype'},
    { reference:'gender', href: '#admin/masterdata/gender', title: 'view.admin.masterdata.gender'}
];

function masterdataMenu({reference}) {
    return (
        <ul className="mdl-list">
        {
            links.map(link => {
                const otherProps = { className: 'mdl-list__item' };
                if(link.reference === reference) {
                    otherProps['data-active'] = true;
                }
                return (
                    <li key={link.reference} {...otherProps}>
                        <a href={link.href}>
                            <span className="mdl-list__item-primary-content">{translate(link.title)}</span>
                        </a>
                    </li>
                );
            })
        }
        </ul>
    );
}

masterdataMenu.displayName = 'MasterdataMenu';
export default masterdataMenu;
