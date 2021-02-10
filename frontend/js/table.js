
export function removeBodyContent(tbody) {
    while (tbody.firstChild) {
        tbody.removeChild(tbody.firstChild);
    }
}

export function createTextCell(str) {
    const td = document.createElement('td');
    td.appendChild(document.createTextNode(str));
    return td;
}

export function createLinkCell(str, url) {
    const a = document.createElement('a');
    a.appendChild(document.createTextNode(str));
    a.href = url;
    const td = document.createElement('td');
    td.appendChild(a);
    return td;
}

export function createBtnCellWithListener(str, listener) {
    const button = document.createElement('button');
    button.appendChild(document.createTextNode(str));
    button.addEventListener('click', listener);
    const td = document.createElement('td');
    td.appendChild(button);
    return td;
}

export function createBtnCellWithOnClick(str, onClickStr) {
    const button = document.createElement('button');
    button.appendChild(document.createTextNode(str));
    button.setAttribute('onclick', onClickStr);
    const td = document.createElement('td');
    td.appendChild(button);
    return td;
}

export function createKeyValueRow(key, value) {
    const tdKey = document.createElement('td');
    tdKey.appendChild(document.createTextNode(key));
    const tdVal = document.createElement('td');
    tdVal.appendChild(document.createTextNode(value));
    const tr = document.createElement('tr');
    tr.appendChild(tdKey);
    tr.appendChild(tdVal);
    return tr;
}
