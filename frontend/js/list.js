import {
    removeBodyContent,
    createTextCell,
    createBtnCellWithListener,
    createBtnCellWithOnClick
} from '/js/table.js';
import {addDclListener} from '/js/load.js';
import {getObject} from '/js/api.js';
import {getApiUrl} from "/js/config.js";


addDclListener(() => getMetadata());


function getMetadata() {
    getObject('metadata', displayMetadata);
}

function displayMetadata(list) {
    let tabBody = document.getElementById('tabBody');
    removeBodyContent(tabBody);
    list.metadataList.forEach(a => {
        tabBody.appendChild(createMetadataRow(a));
    })
}

function createMetadataRow(metadata) {
    let tr = document.createElement('tr');
    tr.appendChild(createTextCell(metadata.id));
    tr.appendChild(createTextCell(metadata.filename));
    tr.appendChild(createTextCell(metadata.size));
    tr.appendChild(createTextCell(metadata.uploadTime));
    tr.appendChild(createBtnCellWithListener("download",
        () => downloadFile(metadata.id, metadata.filename)));
    tr.appendChild(createBtnCellWithOnClick('details',
        "window.location.href = '/subpages/details.html?id=" + metadata.id + "'"));
    return tr;
}

function downloadFile(id, filename) {
    const request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (request.readyState === 4) {
            if (request.status === 200 && request.response) {
                const a = document.createElement('a');
                a.href = window.URL.createObjectURL(request.response);
                a.download = filename;
                a.style.display = "none";
                document.body.appendChild(a);
                a.click();
            } else {
                alert("Download failed: " + request.status);
            }
        }
    };
    request.open("GET", getApiUrl() + 'downloads/' + id, true);
    request.responseType = 'blob';
    request.send();
}




