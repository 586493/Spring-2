import {
    removeBodyContent,
    createKeyValueRow
} from "./table.js";
import {addDclListener} from "/js/load.js";
import {getObject} from "/js/api.js";
import {getParameter} from '/js/query.js'


addDclListener(() => getMetadata());


function getMetadata() {
    getObject('metadata/' + getParameter('id'), displayMetadata);
}

function modifyKey(key) {
    if(key === "uploadTime") {
        return "upload time";
    } else if(key === "size") {
        return "size [B]";
    } else if (key === "id") {
        return "ID";
    } else {
        return key;
    }
}

function displayMetadata(obj) {
    let tabBody = document.getElementById('tabBody');
    removeBodyContent(tabBody);
    for (const [key, value] of Object.entries(obj)) {
        tabBody.appendChild(createKeyValueRow(modifyKey(key), value));
    }
}

