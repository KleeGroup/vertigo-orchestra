import history from 'focus-core/history';

//TODO : à revoir avec Pierre et Nicolas => comment je connais le type de ligne ???
export default function onLineClick(data) {
    let url = '#';

    const {proId} = data;
    //console.log(data, '\n', isMovie,'\n', isPerson,'\n', code);
    url = `#definitions/${proId}`;

    history.navigate(url, true);
    window.scrollTo(0, 0);
}
