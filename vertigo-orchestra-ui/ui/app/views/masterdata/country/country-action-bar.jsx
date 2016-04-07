import React from 'react';
const ALPHABET ='abcdefghijklmnopqrstuvwxyz'

export default function CountryActionBar({onLetterClick, criteria}) {
    return (
        <ul data-demo='country-list-action-bar'>
            {
              ALPHABET.split('').map( l => {
                const colorClass = l === criteria ? 'mdl-button--colored': '';
                return (
                  <li key={l}>
                    <button className={`mdl-button mdl-button--raised ${colorClass}`} onClick={() => onLetterClick(l)}>
                      {l.toUpperCase()}
                    </button>
                  </li>
                );
              })
          }
        </ul>
    );
};
